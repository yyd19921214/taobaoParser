package com.spider.taobao.parser;
// 284:45 450 1  183 start 278 10;30----打开本人主页时睡2秒，在打开好友页面时不睡 187个 30分 睡1秒 180++ 3s 600+
//import com.spider.taobao.core.Login;
import com.spider.taobao.core.ParserHelper;
import com.spider.taobao.dao.UserListDao;
import com.spider.taobao.entity.BPUser;
import com.spider.taobao.entity.Focus;
import com.spider.taobao.util.DateUtil;
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

public class UserInfoParser extends Thread{
    private WebDriver driver;
    //private Login login;
    private UserListDao uld;
    private List<BPUser> bpuList;
    int num;
    WebDriverPool drvPool;

    public UserInfoParser() {
        uld=new UserListDao();
        num=0;
        drvPool = new WebDriverPool();
        drvPool.setDelayTime(1000);
    }
    
    @Override
    public void run() {
        parse();
    }
    
    public void parse() {
        //drvPool.addDriver();
        drvPool.addDriverTextOnly();
        //drvPool.addDriverWithProxy("191.168.0.23", 808);
        drvPool.addDriverWithProxy("191.168.1.23", 808);
        
        bpuList=uld.getBPUList();//对尚未处理（抓取时间为空）或者尚未在当下处理过（处理时间小于现在时间）的用户记录进行处理
        
        for(BPUser bpu:bpuList){
             System.out.println(num);
            if(bpu.getGender()==null){              
                while(!getUserInfo(bpu));
            }
            
            while(!getFocusList(bpu));
            
            num++;
            uld.save(bpu);
        }
        
        drvPool.quitAllDriver();
    }
    
    public boolean getUserInfo(BPUser bpu) {
        String Tag = bpu.getTag();
        System.out.println(Tag);
        boolean suss_flag = true;

        if (Tag != null) {
            try {
                driver = drvPool.openByGet(Tag, true);

            } catch (Exception ex) {
                System.out.println("跳转异常！！");
            }

            try {
                 WebElement customerInfo = driver.findElement(By.cssSelector(".hp-user-info"));

                try{
                    WebElement gender = customerInfo.findElement(By.xpath("./div[@class='bd']/div/div[@class='line']"));
                    String customer_gender = gender.getText();
                    System.out.println(customer_gender);//customer gender
                    if (customer_gender.contains("女")) {
                        customer_gender = "女";
                    } else {
                        customer_gender = "男";
                    }
                    bpu.setGender(customer_gender);
                } catch (Exception ex) {
                    System.out.println("无法获取性别基本信息");
                    suss_flag = false;
                }
            }catch (Exception ex) {
                System.out.println("无法打开主页");
                suss_flag = false;
            }

            try {
                WebElement eaddress = driver.findElement(By.id("J_HomePageAddress"));
                String address = eaddress.getText().trim();
                bpu.setArea(address);
            } catch (Exception ex) {
                System.out.println("无法获取地址基本信息");
                suss_flag = false;
            }
        }
        
        if(!suss_flag){
            if(ParserHelper.isCheckCodePage(driver)){
                System.out.println("触发验证码");
                ParserHelper.waitForCheckcode();
            }
            else{
                suss_flag = true;
            }
        }
        
        return suss_flag;
    }
    
    public boolean getFocusList(BPUser bpu){
        boolean suss_flag = true;
        String url = bpu.getFocusUrl();
        if (url != null) {
            try {
                driver = drvPool.openByGet(url, true);
                WebElement cus_fans = driver.findElement(By.cssSelector(".J_FansCount"));
                String strcus_fans = cus_fans.getText().trim();
                int customer_fans = Integer.parseInt(strcus_fans);	//获得粉丝数量
                bpu.setFansNum(customer_fans);

                WebElement cus_following = driver.findElement(By.cssSelector(".J_FollowCount"));
                String strcus_following = cus_following.getText().trim();
                int customer_following = Integer.parseInt(strcus_following);//following
                int customer_old;
                Date today = new Date(DateUtil.getStringByDate(new Date()));

                if (bpu.getFocusNum() == null) {                    //如果之前尚未对关注列表进行处理（即该项为null）
                    customer_old = 0;
                    int pages = customer_following / 20;
                    if (customer_following%20 != 0) {
                        pages += 1;
                    }
                    List<Focus> flist = new ArrayList<Focus>();

                    for (int i = 1; i <= pages; i++) {
                        String pageurl = String.format(url + "?page=%d", i);
                        driver = drvPool.openByGet(pageurl, true);
                        System.out.println("Page \t" + i);
                        List<Focus> pflist = getPageFList(bpu.getId(), today, driver);
                        flist.addAll(pflist);
                    }
                    System.out.println(customer_following + "\t" + flist.size());
                    if (customer_following == flist.size()) {
                        bpu.setParseDate(today);
                        for (Focus f : flist) {
                            uld.save(f);
                        }
                    } else {
                        System.out.println("Error!Result don't equals!");
                    }
                } else {
                    customer_old=bpu.getFocusNum();
                    if (customer_old != customer_following) {
                        if (customer_following>customer_old && customer_following-customer_old > 20) {
                            System.out.println("Not update yet!");//说明新加入了关注对象需为此进行更新且只有在关注的数目出现显著变化时才更新（>20个）

                            int pagesend = (customer_following-customer_old)/20;
                            if ((customer_following-customer_old)%20 != 0) {
                                pagesend++;
                            }
                            List<Focus> flist = new ArrayList<Focus>();

                            for (int i =1; i <= pagesend; i++) {
                                String pageurl = String.format(url + "?page=%d", i);
                                driver = drvPool.openByGet(pageurl, true);
                                System.out.println("Page \t" + i);
                                List<Focus> pflist = getPageFList(bpu.getId(), today,driver);
                                flist.addAll(pflist);
                            }
                            System.out.println(customer_following + "\t" + flist.size());
                            if (customer_following == flist.size()) {
                                bpu.setParseDate(today);
                                List<Focus> focuslist = uld.getFList(bpu.getId());

                                for (Focus f : flist) {
                                    boolean found = false;
                                    for (Focus oldf : focuslist) {
                                        if (f.getFid() == oldf.getFid()) {
                                            found = true;
                                        }
                                    }
                                    if (!found) {
                                        uld.save(f);
                                    }
                                }                                         //对之前尚未加入的关注对象进行插入
                            } else {
                                System.out.println("Error!Result don't equals!");
                            }
                        } else {
                            //System.out.println("Error!");//此时关注人数比原来人数减少，认为这是错误的
                        }
                    } else {
                        bpu.setParseDate(today);
                        System.out.println("Equal!");          //如果相等则说明不需要更新
                    }
                }//不相等

                if (customer_following - customer_old > 20) {
                    bpu.setFocusNum(customer_following);
                    System.out.println(customer_following - customer_old);
                }
                //如果人数的增量大于20则更新
            } catch (Exception ex) {
                System.out.println("无法打开好友页面 " + url);
                suss_flag = false;
            }
        }
        
        if(!suss_flag){
            if(ParserHelper.isCheckCodePage(driver)){
                System.out.println("触发验证码");
                ParserHelper.waitForCheckcode();
            }
            else{
                suss_flag = true;
            }
        }
        
        return suss_flag;
    }
    
    private List<Focus> getPageFList(Long uid,Date date,WebDriver wb){
        
        List<Focus> flist=new ArrayList<Focus>();
        try{
            List<WebElement> flistElement=wb.findElements(By.xpath("//div[@id='J_FansWrap']/div[@class='fans-bd']/div/div[@class='fans-item']"));
            for(WebElement element:flistElement){
                WebElement idElement=element.findElement(By.xpath("./div[@class='bd']/a[@class='pic']/img"));
                String idStr=idElement.getAttribute("src");
                idStr=idStr.substring(idStr.indexOf("userId="));
                idStr=idStr.substring(0,idStr.indexOf("&"));
                idStr=idStr.replaceAll("userId=", "");
                Long id=Long.parseLong(idStr);

                WebElement nameElement=element.findElement(By.xpath("./div[@class='bd']/div[@class='cont']/p[@class='list']/a[@class='name']"));
                String nameStr=nameElement.getText();

                Focus focus=new Focus();
                focus.setUid(uid);//关注该对象的帮派成员id
                focus.setFid(id);//该对象本身的Id
                focus.setFname(nameStr);//对象名称
                focus.setDate(date);//抓取时间

                flist.add(focus);

                System.out.println(id+"\t"+nameStr);
            }            
        }catch(NoSuchElementException ex) {
            System.out.println("抓取关注信息异常 " + wb.getCurrentUrl());
        }

        return flist;
    }

}
