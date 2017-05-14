package com.spider.taobao.parser;

import com.spider.taobao.dao.UserListDao;
import com.spider.taobao.entity.Discussion;
import com.spider.taobao.entity.DiscussionReply;
import com.spider.taobao.entity.ReplyerInfo;
import com.spider.taobao.util.DriverFactory;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class DiscussionParser extends Thread{
    private WebDriver driver;
    private UserListDao uld;
    ArrayList<Discussion> dList;
    
    public DiscussionParser(ArrayList<Discussion> dList) {
        uld=new UserListDao();
        this.dList = dList;
    }
    
    @Override
    public void run() {
        parse();
    }
    
    
    public void parse() {
        driver=DriverFactory.getFirefoxDriverTextOnly();
        driver.manage().timeouts().pageLoadTimeout(1, TimeUnit.MINUTES);
        //driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);  
        //driver=DriverFactory.getFirefoxDriver();
        
        //List<Discussion> dList = uld.getDiscussionList();
        //System.out.println(dList.size());
        int i=1;
        for(Discussion d:dList){
            String url = d.getUrl();
            System.out.println((i++) + "/"+dList.size() + " " + url);
            driver.get(url);                  //进入该帖子的页面
            
            List<DiscussionReply> drList=getDiscussionReply(d);
            for(DiscussionReply dr:drList){
                uld.save(dr);
                uld.evict(dr);
            }
            d.setParsed(true);               
            uld.save(d);
            uld.evict(d);
        }
        
        driver.quit();
    }
    
    private List<DiscussionReply> getDiscussionReply(Discussion discussion){
        List<DiscussionReply> drList=new ArrayList<DiscussionReply>();
        
        WebElement lastE;
        try{
            lastE=driver.findElement(By.xpath("//div[@id='pagination_div']/div[@class='page-top']/form/input[@value='末页']"));
        }catch(NoSuchElementException e){
            return drList;
        }
        String totalPageStr=lastE.getAttribute("onclick");
        totalPageStr=totalPageStr.substring(totalPageStr.lastIndexOf("-")+1, totalPageStr.indexOf(".htm"));
        int totalPage=Integer.parseInt(totalPageStr);                         //获取回复的页数
        
        for(int page=1;page<=totalPage;page++){
            String nurl=String.format("http://bangpai.taobao.com/group/thread/14541188-%d-%d.htm",discussion.getId(),page);
            driver.get(nurl);                                                 //翻页
            System.out.println(page+"/"+totalPage);
            List<DiscussionReply> pdrList=getPageDiscussionReply(discussion); //对每一页的回复进行处理
            drList.addAll(pdrList);                                           //drList得到该帖子所有回复的情况
        }
                
        return drList;
    }
        
    private List<DiscussionReply> getPageDiscussionReply(Discussion discussion){
        List<DiscussionReply> drList=new ArrayList<DiscussionReply>();
        Set<ReplyerInfo> riList = new HashSet<ReplyerInfo>(); 
        
        List<WebElement> replyEList;
        try{
            replyEList=driver.findElements(By.xpath("//div[@class='bbd']/div[@class='detail-post']"));//每页的楼层数统计
        }catch(NoSuchElementException e){
            return drList;
        }
            
        for(WebElement element:replyEList){
            WebElement nameE=null;
            
            Long uid=-1L;
            try{
                nameE=element.findElement(By.xpath(".//a[@class='name name-link']"));//获得各个回复者
                
                String uidStr=nameE.getAttribute("href");
                uidStr=uidStr.substring(uidStr.indexOf("groups/"));
                uidStr=uidStr.replaceAll("groups/", "");                            //获得回复者的Id
                uidStr=uidStr.substring(0,uidStr.indexOf("."));
                uid=Long.parseLong(uidStr);
            }catch(Exception ex){
                nameE=element.findElement(By.xpath(".//span[@class='name name-link']"));
            }
            
            String name=nameE.getText();                                            //获取回复者的名称
            
            String shopUrl;
            try{                                                                    //获取回复者的店铺链接
                shopUrl = element.findElement(By.xpath("./div/div[1]/ul[1]/li[1]/a[2]")).getAttribute("href");  
            }catch(NoSuchElementException e){
                shopUrl = null;
            } 
            
            String refName;
            try{                                                                    //获取引用帖子的发帖者名称
                WebElement cite = element.findElement(By.xpath(".//cite")); 
                //WebElement cite = element.findElement(By.cssSelector("div.bd > div > div > p:nth-child(1) > cite"));
                String citeStr = cite.getText();
                String[] citeArray = citeStr.split(" ");
                refName = citeArray[1];
                refName = refName.trim();
            }catch(NoSuchElementException e){
                refName = null;
            }
            
            WebElement timeE=element.findElement(By.xpath(".//li[@class='time']"));
            String timeStr=timeE.getText();
            timeStr=timeStr.replaceAll("发表于", "");
            timeStr=timeStr.replaceAll("回复于", "");
            timeStr=timeStr.replaceAll("-", "/");
            timeStr=timeStr.trim();
            Date time=null;
            try{
                time=new Date(timeStr);
            }catch(Exception ex){}                                                    //获取回复的发表时间
            
            DiscussionReply dr=new DiscussionReply();
            dr.setdId(discussion.getId());                                            //帖子的ID
            dr.setRefName(refName);                                                   //被引用者的名称
            dr.setReplyId(uid);                                                       //回复者的ID
            dr.setReplyName(name);                                                    //回复者的名称
            dr.setReplyDate(time);                                                    //回复时间
            
            drList.add(dr);
            
            ReplyerInfo ri = new ReplyerInfo();
            ri.setReplyId(uid);
            ri.setReplyName(name);
            ri.setShopUrl(shopUrl);
            
            riList.add(ri);
        }

        for(ReplyerInfo rInfo:riList){
            uld.save(rInfo);
            uld.evict(rInfo);
        }
            
        return drList;
    }
}
