/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.spider.taobao.parser;
import com.spider.taobao.dao.UserListDao;
import com.spider.taobao.entity.DynamicMem;
import com.spider.taobao.entity.MemListConfig;
import com.spider.taobao.util.DriverFactory;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
/**
 *
 * @author user
 */
public class MemListParser extends Thread {

    private WebDriver driver;

    private UserListDao uld;
    private MemListConfig mlc;

    public MemListParser() {
        uld = new UserListDao();
        mlc = uld.getMemListConfig();
    }

    public void run() {
        parse();
    }

    public void parse() {

        driver = DriverFactory.getFirefoxDriverTextOnly();

        int startPage = mlc.getFinishPage() + 1;
        int endPage = mlc.getMaxPage();
        //for(int page=startPage;page<=endPage;page++){
        for (int page = startPage; page <= endPage; page++) {
            String url = getUrl(14541188, page);
            driver.get(url);
            System.out.println("打开"+url+"\n");
                    
            getMemList();

            mlc.setFinishPage(page);
            uld.save(mlc);
            uld.evict(mlc);
        }
        driver.quit();
    }

    private String getUrl(long id, int page) {
        String url = String.format("http://bangpai.taobao.com/group/members/%d-%d.htm", id, page);
        return url;
    }

    private void getMemList() {
        List<DynamicMem> MemList = new ArrayList<DynamicMem>();

        List<WebElement> memberList = driver.findElements(By.xpath("//li//div[@class='info comet-m']"));

        for (WebElement memElement : memberList) {
            WebElement idElement = memElement.findElement(By.xpath("./div[@class='avatar s60 J_PigCard']"));
            String idStr = idElement.getAttribute("param:user_id");
            Long ID = Long.parseLong(idStr);

            WebElement nameElement = memElement.findElement(By.xpath("./div[@class='name']/a"));
            String Nickname = nameElement.getText();


            
            String url = String.format("http://bangpai.taobao.com/user/groups/%d.htm?", ID);
            DynamicMem mem = new DynamicMem();
            mem.setID(ID);
            mem.setNickname(Nickname);
            mem.setUrl(url);

            MemList.add(mem);
            System.out.println(ID+" "+Nickname+" "+url);
        }

        for (DynamicMem mems : MemList) {
            uld.save(mems);
            uld.evict(mems);
        }

        mlc.setMemnum(mlc.getMemnum() + MemList.size());
    }

}
