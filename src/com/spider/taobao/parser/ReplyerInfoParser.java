/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spider.taobao.parser;

import com.spider.taobao.dao.UserListDao;
import com.spider.taobao.entity.Discussion;
import com.spider.taobao.entity.ReplyerInfo;
import com.spider.taobao.util.DriverFactory;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;

/**
 *
 * @author Administrator
 */
public class ReplyerInfoParser extends Thread{
    private WebDriver driver;
    private UserListDao uld;
    ArrayList<ReplyerInfo> riList;
    
    public ReplyerInfoParser(ArrayList<ReplyerInfo> riList) {
        uld=new UserListDao();
        this.riList = riList;
    }
    
    @Override
    public void run() {
        parse();
    }
    
    public void parse(){
        driver=DriverFactory.getFirefoxDriverTextOnly();
        driver.manage().timeouts().pageLoadTimeout(1, TimeUnit.MINUTES);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);  
        //driver=DriverFactory.getFirefoxDriver();
        
        //List<Discussion> dList = uld.getDiscussionList();
        //System.out.println(dList.size());
        int i=1;
        for(ReplyerInfo ri:riList){

            String url = ri.getShopUrl();
            System.out.println((i++) + "/"+riList.size() + " " + url);
            int count=0;
            while(true){
                try{
                    driver.get(url);                  //进入该帖子的页面    

                    String realUrl = driver.getCurrentUrl();
                    boolean isShopOwner;
                    if(realUrl.indexOf("/shop/")>=0){
                        isShopOwner = true;
                    }else{
                        isShopOwner = false;
                    }

                    ri.setRealUrl(realUrl);
                    ri.setIsShopOwner(isShopOwner);
                    uld.save(ri);
                    uld.evict(ri);
                    
                    break;
                }catch(TimeoutException ex){
                    if(++count>3) break;
                }                
            }
        }
        
        driver.quit();
    }    
}
