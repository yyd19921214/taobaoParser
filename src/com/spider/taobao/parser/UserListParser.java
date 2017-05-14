package com.spider.taobao.parser;

import com.spider.taobao.core.ParserHelper;
import com.spider.taobao.dao.UserListDao;
import com.spider.taobao.entity.BPUser;
import com.spider.taobao.entity.UserListConfig;
import com.spider.taobao.util.DriverFactory;
import com.spider.taobao.util.WebDriverPool;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class UserListParser extends Thread{
    private WebDriver driver,driver_log;
    private WebDriverPool drvPool_log;
    
    private UserListDao uld;
    private UserListConfig ulc;
    int temp;
    boolean isCheckCode;
    
    public UserListParser() {
        uld=new UserListDao();
        ulc=uld.getUserListConfig();
        temp=1;
        drvPool_log = new WebDriverPool();
        isCheckCode = false;
    }
    
    @Override
    public void run() {
        parse();
    }
    
    public void parse() {
        //login.login("xiaowenqweasd", "09jianai@");

        //drvPool_log.addDriver();
        drvPool_log.addDriverTextOnly();
        //drvPool_log.addDriverWithProxy("191.168.0.23", 808);
        drvPool_log.addDriverWithProxy("191.168.1.23", 808);   
        
        if(!ParserHelper.login("WebCrawlerTest", "123456a",drvPool_log.getDriver())) return;
        //if(!ParserHelper.login("WhuWebTest003", "123456a",drvPool_log.getDriver())) return;
        if(!ParserHelper.login("WhuWebTest004", "123456a",drvPool_log.getDriver())) return;
        
        drvPool_log.setDelayTime(1000);
        
        //String url="http://bangpai.taobao.com/group/members/14541188.htm";
        driver=DriverFactory.getFirefoxDriverTextOnly();
        //driver=new ChromeDriver();
        //driver=DriverFactory.getFirefoxDriver();
        
        int startPage=ulc.getFinishPage()+1;
        int endPage=ulc.getMaxPage();
        for(int page=startPage;page<=endPage;page++){
        //for(int page=1;page<=endPage;page++){
            String url=getUrl(14541188,page);
            driver.get(url);
            
            getUserList();
            
            ulc.setFinishPage(page);
            uld.save(ulc);
        }
        
        //login.logout();
        driver.quit();
        drvPool_log.quitAllDriver();
    }
    
    private String getUrl(long id,int page){
        String url=String.format("http://bangpai.taobao.com/group/members/%d-%d.htm", id,page);
        return url;
    }
    
    private void getUserList(){
       
        List<BPUser> bpuList=new ArrayList<BPUser>();
        
        try{
            List<WebElement> userList=driver.findElements(By.xpath("//li//div[@class='info comet-m']"));

            for(WebElement userElement:userList){
                WebElement idElement=userElement.findElement(By.xpath("./div[@class='avatar s60 J_PigCard']"));
                String idStr=idElement.getAttribute("param:user_id");
                System.out.println(idStr);
                Long id=Long.parseLong(idStr);

                WebElement nameElement=userElement.findElement(By.xpath("./div[@class='name']/a"));
                String name=nameElement.getText();

                WebElement dateElement=userElement.findElement(By.xpath("./p[@class='time']"));
                String timeStr=dateElement.getText();
                timeStr=timeStr.replaceAll("加入", "");
                timeStr=timeStr.replaceAll("-", "/");
                timeStr.trim();
                Date date=new Date(timeStr);

                String url=String.format("http://my.taobao.com/%d", id);
                String Tag = null;
                do{
                    isCheckCode = false;
                    Tag = getTag(id);
                }while(isCheckCode);
                
                String focusurl=null;
                if (Tag != null) {
                    do{
                        isCheckCode = false;
                        focusurl = getFocusurl(Tag);
                    }while(isCheckCode);
                    System.out.println(focusurl+"  "+(temp++));
                }
                
                BPUser bpu=new BPUser();
                bpu.setId(id);
                bpu.setName(name);
                bpu.setUrl(url);
                bpu.setJoinDate(date);
                bpu.setTag(Tag);
                bpu.setFocusUrl(focusurl);
    //            System.out.print(bpu.getName());
    //            uld.save(bpu);
    //            uld.evict(bpu);
                bpuList.add(bpu);
            }
    //        
            for(BPUser bpu:bpuList){
                uld.save(bpu);
                uld.evict(bpu);
            }

            ulc.setUsernum(ulc.getUsernum()+bpuList.size());
        }catch(NoSuchElementException ex) {
            System.out.println("抓取成员基本信息异常");
        }
    }
    
    private String getTag(long id){
        String turl=String.format("http://bangpai.taobao.com/user/groups/%d.htm?", id);
        String tag = null;
        
        try{
            driver_log = drvPool_log.openByGet(turl, false);
            WebElement tagElement = driver_log.findElement(By.xpath("//div[@class='sns-tab tab-nav']/ul/li/a"));
            tag = tagElement.getAttribute("href");      
            tag = tag.trim();            
        }
        catch(NoSuchElementException ex){
            System.out.println("异常用户：淘帮派页面打开异常，抓取个人主页链接失败 " + turl);
            if(ParserHelper.isCheckCodePage(driver_log)){
                System.out.println("触发验证码");
                ParserHelper.waitForCheckcode();
                isCheckCode = true;
            }
        }
        
        return tag;
    }
    
    private String getFocusurl(String Tag){
        String focusurl = null;
        try{       
            driver_log = drvPool_log.openByGet(Tag, true);
            WebElement focus = driver_log.findElement(By.xpath("//div[@class='sns']/ul[@class='user-atten']/li[@class='atten-item follow']/a"));

            focusurl = focus.getAttribute("href");   
        }
        catch(NoSuchElementException ex){
            System.out.println("异常用户：个人主页打开异常，抓取focusurl失败 "+ Tag);
            if(ParserHelper.isCheckCodePage(driver_log)){
                System.out.println("触发验证码");
                ParserHelper.waitForCheckcode();
                isCheckCode = true;
            }
        }
        return focusurl;
    }
    
}
 