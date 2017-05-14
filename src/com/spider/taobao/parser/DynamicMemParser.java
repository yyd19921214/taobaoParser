/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.spider.taobao.parser;
import com.spider.taobao.core.ParserHelper;
import com.spider.taobao.dao.UserListDao;
import com.spider.taobao.entity.BPUser;
import com.spider.taobao.entity.DynamicMem;
import com.spider.taobao.entity.Member;
import com.spider.taobao.entity.UserListConfig;
import com.spider.taobao.util.DateUtil;
import com.spider.taobao.util.DriverFactory;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.sql.*;
import java.util.concurrent.TimeUnit;
/**
 *
 * @author hadoop
 */
public class DynamicMemParser extends Thread {

    private WebDriver driver;
    //private UserListConfig ulc;
    private UserListDao uld;
    private ArrayList<DynamicMem> memList;
    private String DBdriver;
    private String url;
    private String user;
    private String password;
    private Connection conn;
    private PreparedStatement st,st2;
    private ResultSet rs,rs2;

	public DynamicMemParser(ArrayList<DynamicMem> memList) {

		uld = new UserListDao();
		DBdriver = "com.mysql.jdbc.Driver";
		url = "jdbc:mysql://127.0.0.1:3306/tbbp";
		user = "root";
		password = "123456";
		conn = null;
		st = null;
		rs = null;
		this.memList = memList;
	}

    @Override
    public void run() {
        parse();            
    }

    public void parse() {
        try{
            driver = DriverFactory.getFirefoxDriverTextOnly();
            driver.manage().timeouts().pageLoadTimeout(1, TimeUnit.MINUTES);
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);         
            ParserHelper.login("WebCrawlerTest", "123456a", driver);

            //此处应将memberList换成memtest
//            memList = uld.getMemberList();//对尚未处理（抓取时间为空）或者尚未在当下处理过（处理时间小于现在时间）的用户记录进行处理

            Date today=new Date(DateUtil.getStringByDate(new Date()));
            String date=(today.toString()).substring(4,7)+"_"+(today.toString().substring(8,11));
            System.out.println(date);


            //登陆数据库
            try {
                Class.forName(DBdriver);
                conn = DriverManager.getConnection(url, user, password);
                if (!conn.isClosed()) {
                    System.out.println("Succeeded connecting to the Database!----------" + new Date());
                }
            }catch(Exception ex){
                System.out.print("数据库连接错误！！");
            }


    //        Date today=new Date(DateUtil.getStringByDate(new Date()));
            int i=1, memSize;
            memSize = memList.size();
            for (DynamicMem mem : memList) {
                getMemInfo(mem,date);

                mem.setParseDate(today);
                uld.save(mem);
                uld.evict(mem);
                System.out.println((i++)+"/"+memSize);
            }
        }finally{
            if(driver != null) driver.quit();
            try{
                if (!conn.isClosed()) {
                    conn.close();
                }
            }catch(Exception ex){
                System.out.print("数据库关闭错误！！");
            }
        }            
    }
    
    public void getMemInfo(DynamicMem mem,String date) {
        try {
            String personurl = mem.getUrl();
            long id=mem.getID();
            driver.get(personurl);//打开帮派成员的页面

            List<WebElement> customerInfo = driver.findElements(By.xpath("//div[@class='bangpai-head']/ul[@class='items']/li/span/em"));//得到关于成员信息的目录

            try {
                int credits = Integer.parseInt(customerInfo.get(1).getText());

                saveCredits(id,date,credits);
                System.out.print(credits + " ");
            } catch (Exception ex) {
                System.out.println("Failure to get credits");
            }

            try {

                int postNum = Integer.parseInt(customerInfo.get(2).getText());
                savePostNum(id,date,postNum);
                System.out.print(postNum + " ");
            } catch (Exception ex) {
                System.out.println("Failure to get PostNum");
            }

            try {

                int essNum = Integer.parseInt(customerInfo.get(3).getText());
                saveEssenceNum(id,date,essNum);
                System.out.println(essNum);
            } catch (Exception ex) {
                System.out.println("Failure to get EssencePostNum");
            }
            
            try {
                //String[] tempBangPaiNum = driver.findElement(By.xpath("//div[@class='sns-box box-detail']/div[@class='bd']/p[@class='status']")).getText().split("了|个");
                List<WebElement> memBangpaiName = driver.findElements(By.xpath("//div[@class='sns-box box-detail']/div[@class='bd']/ul[@id='J_hover']/li[@class='item']/div[@class='main']/h3/a"));
                int t = memBangpaiName.size();
                String[] tbp = new String[t];
                int i = 0;
                for (WebElement memBangpai : memBangpaiName) {
                    tbp[i++] = memBangpai.getText();
                }
                savefocusBangpai(id,date,tbp);
            } catch (Exception ex) {
                System.out.println("Failure to get BangPaiNum");
            }

        } catch (Exception ex) {
            System.out.println("无法打开成员页面");
        }

    }
    public void saveCredits(long id, String date, int credits) {

//        String save1="insert into credits(id,?) values(?,?) on duplicate key update ?=?";
        try {
            String sql = "insert into credits(id,credits,date) values (?,?,?)";
            st = conn.prepareStatement(sql);
            st.setLong(1, id);
            st.setInt(2, credits);
            st.setString(3, date);
            st.executeUpdate();
            //System.out.println("Succeeded inserting to the Database!----------" + new java.util.Date());
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }

    }
    
    public void savePostNum(long id, String date, int postNum) {
        try {
            String sql = "insert into postNum(id,postNum,date) values (?,?,?)";
            st = conn.prepareStatement(sql);
            st.setLong(1, id);
            st.setInt(2, postNum);
            st.setString(3, date);
            st.executeUpdate();
            //System.out.println("Succeeded inserting to the Database!----------" + new java.util.Date());
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        

    }
     
    public void saveEssenceNum(long id, String date, int EssenceNum) {
        try {
            String sql = "insert into essenceNum(id,essenceNum,date) values (?,?,?)";
            st = conn.prepareStatement(sql);
            st.setLong(1, id);
            st.setInt(2, EssenceNum);
            st.setString(3, date);
            st.executeUpdate();
            //System.out.println("Succeeded inserting to the Database!----------" + new java.util.Date());
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }

    }
    public void savefocusBangpai(long id, String date, String[] bangpai) {
        int num = bangpai.length;
        try {
            String sql = "insert into bangpainum(id,bangpainum,date) values (?,?,?)";
            st = conn.prepareStatement(sql);
            st.setLong(1, id);
            st.setInt(2, num);
            st.setString(3, date);
            st.executeUpdate();

            for (int j = 0; j < num; j++) {
                String bangpainame = bangpai[j];
              
                System.out.println(bangpainame);      
                String sql2 = "insert into bangpainame(id,bangpai,date) values(?,?,?) on duplicate key update date=?";
                st = conn.prepareStatement(sql2);
                st.setLong(1, id);
                st.setString(2, bangpainame);
                st.setString(3, date);
                st.setString(4, date);
                st.executeUpdate();
            }
            //System.out.println("Succeeded inserting to the Database!----------" + new java.util.Date());
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }

    }

}
    
 


