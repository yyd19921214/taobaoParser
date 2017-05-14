/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//package com.spider.taobao.parser;
//import com.spider.taobao.core.Login;
//import com.spider.taobao.dao.UserListDao;
//import com.spider.taobao.entity.Member;
//import com.spider.taobao.entity.DynamicMem;
//import com.spider.taobao.entity.Focus;
//import com.spider.taobao.util.DateUtil;
//import com.spider.taobao.util.DriverFactory;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
///**
// *
// * @author user
// */
//public class MemInfoParser extends Thread {
//    private WebDriver driver;
//    private Login login;
//
//    private UserListDao uld;
//
//    private List<DynamicMem> memList;
//
//    public MemInfoParser() {
//        login = new Login();
//        uld = new UserListDao();
//    }
//    public void run() {
//        parse();
//    }
//    public void parse() {
//        login.login("WhuWebTest004", "123456a");
//        driver=login.getDriver();
//        memList=uld.getDynamicMem();//对尚未处理（抓取时间为空）或者尚未在当下处理过（处理时间小于现在时间）的用户记录进行处理
//        
//            for (DynamicMem mem : memList) {
//            getMemInfo(mem);
//            Date today=new Date(DateUtil.getStringByDate(new Date()));
//            mem.setParseDate(today);
//            uld.save(mem);
//        }
//
//        driver.quit();
//    }
//    private void getMemInfo(Member mem) {
//        //String="http://bangpai.taobao.com/user/groups/"+mem.getID()+
//        try{
//        driver.get(String.format("http://bangpai.taobao.com/user/groups/%d.htm?", mem.getID()));//打开帮派成员的页面
//        
//        List<WebElement> customerInfo = driver.findElements(By.xpath("//div[@class='bangpai-head']/ul[@class='items']/li/span/em"));//得到关于成员信息的目录
//
//        try {
//            int credits =Integer.parseInt(customerInfo.get(1).getText());
//           // WebElement credits = customerInfo.findElement(By.xpath("相应路径B得到积分./li/span/em"));
//            //int customer_credits = Integer.parseInt(credits.getText());		//credits
//            mem.setCredits(credits);
//            System.out.print(credits+" ");
//        } catch (Exception ex) {
//            System.out.println("Failure to get credits");
//        }
//
//        try {
//            //WebElement post = driver.findElement(By.id("相应路径c得到发帖数量"));
//            //String tpost = post.getText().trim();
//            int postNum=Integer.parseInt(customerInfo.get(2).getText());
//            mem.setPostNum(postNum);
//            System.out.print(postNum+" ");
//        } catch (Exception ex) {
//            System.out.println("Failure to get PostNum");
//        }
//
//        try {
//            //WebElement ess = driver.findElement(By.id("相应路径C得到精华帖数量"));
//            //String tess = ess.getText().trim();
//            int essNum=Integer.parseInt(customerInfo.get(3).getText());
//            mem.setEssenceNum(essNum);
//             System.out.println(essNum);
//        } catch (Exception ex) {
//            System.out.println("Failure to get EssencePostNum");
//        }
//        
//        }
//        catch(Exception ex){
//            System.out.println("无法打开成员页面");
//        }
//        
//    }
//    
//         
//}
