package com.spider.taobao.parser;

import com.spider.taobao.dao.UserListDao;
import com.spider.taobao.entity.Discussion;
import com.spider.taobao.entity.DiscussionListConfig;
import com.spider.taobao.util.DateUtil;
import com.spider.taobao.util.DriverFactory;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class DiscussionListParser extends Thread{
    private WebDriver driver;
    private UserListDao uld;

    public DiscussionListParser() {
        uld=new UserListDao();
    }
    
    @Override
    public void run() {
        parse();
    }
    
    public void parse() {
        driver=DriverFactory.getFirefoxDriverTextOnly();
        //driver=DriverFactory.getFirefoxDriver();
        
        DiscussionListConfig dlc = uld.getDiscussionListConfig();
        Date today=new Date(DateUtil.getStringByDate(new Date()));
        System.out.println(dlc.getFinishPage());
        if(dlc.getDate()==null||DateUtil.compare(dlc.getDate(), today)<0||dlc.getFinishPage()<dlc.getMaxPage()){
            for(int page=dlc.getFinishPage()+1;page<=dlc.getMaxPage();page++){
                String url=getUrl(14541188L,page);          //对未处理的页面逐页进行处理
                driver.get(url);                            //跳转到讨论区
                List<Discussion> dlist=getDiscussionList();
                for(Discussion d:dlist){
                    uld.save(d);
                    uld.evict(d);
                }
                dlc.setFinishPage(page);
                dlc.setDiscussionNum(dlc.getDiscussionNum()+dlist.size());
                dlc.setDate(today);
                uld.save(dlc);
                uld.evict(dlc);
            }
            driver.quit();
        }
        
    }
    
    private String getUrl(Long id,int page){
        String url=String.format("http://bangpai.taobao.com/group/discussion/%d--%d.htm", id,page);
        return url;
    } 
    
    private List<Discussion> getDiscussionList(){
        List<Discussion> dlist=new ArrayList<Discussion>();
        
        List<WebElement> delist=driver.findElements(By.xpath("//tbody/tr"));     //将每张帖子放在列表中
        List<WebElement> ndelist=new ArrayList<WebElement>();
        for(WebElement element:delist){
            try{
                WebElement ae=element.findElement(By.xpath("./td[@class='subject']/div[@class='detail']/a"));
                ndelist.add(element);
            }catch(Exception ex){
                System.out.println("");
            }            
        }
        for(WebElement element:ndelist){
            WebElement ae=element.findElement(By.xpath("./td[@class='subject']/div[@class='detail']/a"));
            String href=ae.getAttribute("href");
            
            String idStr=href.substring(href.indexOf("-")+1);
            idStr=idStr.substring(0,idStr.indexOf("."));
            Long id=Long.parseLong(idStr);                       //获取该帖子的id
            
            String title=ae.getText();                           //获取帖子文本信息（标题）
            
            WebElement nameE=element.findElement(By.xpath("./td[@class='later']/div/div[@class='name']/a"));
            String name=nameE.getAttribute("title");             //获取发帖人的名称
            
            String uidStr=nameE.getAttribute("href");
            uidStr=uidStr.substring(uidStr.indexOf("groups/"));
            uidStr=uidStr.replaceAll("groups/", "");
            uidStr=uidStr.substring(0,uidStr.indexOf("."));
            Long uid=Long.parseLong(uidStr);                     //获取发帖人的id             
            
            WebElement timeE=element.findElement(By.xpath("./td[@class='later']/div/div[@class='time']"));
            String timeStr=timeE.getText();
            timeStr=timeStr.replaceAll("-", "/");
            Date time=null;
            try{
                time=new Date(timeStr);
            }catch(Exception ex){
                continue;
            }                                                      //获取发帖时间
            
            WebElement replyE=element.findElement(By.xpath("./td[@class='score']/em[1]"));
            String replyStr=replyE.getText();
            Integer replyNum=Integer.parseInt(replyStr);           //获取回复的数量
            
            WebElement viewE=element.findElement(By.xpath("./td[@class='score']/em[2]"));
            String viewStr=viewE.getText();
            Integer viewNum=Integer.parseInt(viewStr);             //获取阅读数量
            
            String lastName="";
            Long lastId=-1L;
            Date lastTime=null;
            
            try{ 
                WebElement laste=element.findElement(By.xpath("./td[@class='last']/div/div[@class='name']/a"));
                lastName=laste.getAttribute("title");

                String lastIdStr=laste.getAttribute("href");
                lastIdStr=lastIdStr.substring(lastIdStr.indexOf("groups/"));
                lastIdStr=lastIdStr.replaceAll("groups/", "");
                lastIdStr=lastIdStr.substring(0,lastIdStr.indexOf("."));
                lastId=Long.parseLong(lastIdStr);                     //获取最后回帖人的名称

                WebElement lastTimee=element.findElement(By.xpath("./td[@class='last']/div/div[@class='time']"));
                String lastTimeStr=lastTimee.getText();
                lastTimeStr=lastTimeStr.replaceAll("-", "/");
                lastTime=new Date(lastTimeStr);                      //获取最后回贴的时间
            }catch(Exception ex){
            
            }
            
            Discussion discussion=new Discussion();
            discussion.setId(id);                         //帖子ID
            discussion.setTitle(title);                   //帖子名称
            discussion.setUrl(href);                      //URL
            discussion.setUserName(name);                 //发帖人名称
            discussion.setUserId(uid);                    //发帖人ID
            discussion.setTime(time);                     //发帖时间
         
            discussion.setReplyNum(replyNum);             //回复数量
            discussion.setReadNum(viewNum);               //浏览数量
            discussion.setLastReplyId(lastId);            //最后回复ID
            discussion.setLastReplyName(lastName);        //最后回复姓名
            discussion.setLastReplyTime(lastTime);        //最后回复时间
            
            dlist.add(discussion);
            
            System.out.println(title);
        }
        
        
        return dlist;
    }
}
