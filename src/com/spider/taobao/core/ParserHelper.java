package com.spider.taobao.core;

import com.spider.taobao.parser.UserInfoParser;
import com.spider.taobao.util.DriverFactory;
import com.spider.taobao.util.WebDriverPool;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class ParserHelper {

    public static boolean login(String name,String psd, WebDriver driver){
       
        String url = "https://login.taobao.com/";
        driver.get(url);
        try {
            WebElement logincheck=driver.findElement(By.xpath("//input[@id='J_SafeLoginCheck']"));
            if(logincheck.isEnabled()) logincheck.click();
            WebElement nameInput=driver.findElement(By.xpath("//input[@name='TPL_username']"));

//           JavascriptExecutor j= (JavascriptExecutor)driver;
//            j.executeScript("document.getElementByName('TPL_password').style.display=''; ");
            WebElement psdInput=driver.findElement(By.xpath("//input[@name='TPL_password']"));
//            System.out.println(psdInput.toString());
            WebElement submitButton=driver.findElement(By.xpath("//button[@id='J_SubmitStatic']"));
            nameInput.clear();
            nameInput.sendKeys(name);
            ////*[@id="J_StandardPwd"]
 //           ((JavascriptExecutor)driver).executeScript("arguments[0].checked = true;", psdinput);
            psdInput.clear();
            psdInput.sendKeys(psd);
            submitButton.click();
            return true;
        } catch (NoSuchElementException ex) {
//            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("打开登陆页面异常，登录失败！");
            return false;
        }
    }

    public static boolean isCheckCodePage(WebDriver driver){
        try{
            driver.findElement(By.xpath("//img[@id='J_CheckCode']"));
            return true;
        }catch(NoSuchElementException elementException){
            return false;
        }
    } 
    
    public static void waitForCheckcode(int msec){
        try {
            Thread.sleep(msec); 
        } catch (InterruptedException ex) {
            Logger.getLogger(UserInfoParser.class.getName()).log(Level.SEVERE, null, ex);
        }         
    }
    
    public static void waitForCheckcode(){
        try {
            Thread.sleep(300000); 
        } catch (InterruptedException ex) {
            Logger.getLogger(UserInfoParser.class.getName()).log(Level.SEVERE, null, ex);
        }         
    }
}
