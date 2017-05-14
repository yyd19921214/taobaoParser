/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.spider.taobao.util;

import com.spider.taobao.parser.UserInfoParser;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openqa.selenium.WebDriver;

/**
 *
 * @author GW_2
 */
public class WebDriverPool {
    private List<WebDriver> drvList;
    private int get_count;
    private int delay_msec;
    private int pageLoadTimeout;    //单位分钟
    private int implicitlyWait;     //单位秒
    
    public WebDriverPool(){
        drvList = new ArrayList<WebDriver>();
        get_count = 0;
        delay_msec = 0;
        pageLoadTimeout = 1;
        implicitlyWait = 5;
    }

    public void addDriver(WebDriver driver){
        drvList.add(driver);  
        driver.manage().timeouts().pageLoadTimeout(pageLoadTimeout, TimeUnit.MINUTES);
        driver.manage().timeouts().implicitlyWait(implicitlyWait, TimeUnit.SECONDS);  
    }
    
    public void addDriver(){
        WebDriver driver;
        driver = DriverFactory.getFirefoxDriver();
        drvList.add(driver);  
        driver.manage().timeouts().pageLoadTimeout(pageLoadTimeout, TimeUnit.MINUTES);
        driver.manage().timeouts().implicitlyWait(implicitlyWait, TimeUnit.SECONDS);  
    }
    
    public void addDriverTextOnly(){
        WebDriver driver;
        driver = DriverFactory.getFirefoxDriverTextOnly();
        drvList.add(driver);  
        driver.manage().timeouts().pageLoadTimeout(pageLoadTimeout, TimeUnit.MINUTES);
        driver.manage().timeouts().implicitlyWait(implicitlyWait, TimeUnit.SECONDS);     
    }

    public void addDriverWithProxy(String proxyIP, int proxyPort){
        WebDriver driver;
        driver = DriverFactory.getFirefoxDriverWithProxy(proxyIP, proxyPort); 
        drvList.add(driver);    
        driver.manage().timeouts().pageLoadTimeout(pageLoadTimeout, TimeUnit.MINUTES);
        driver.manage().timeouts().implicitlyWait(implicitlyWait, TimeUnit.SECONDS);          
    }    
    
    public void quitAllDriver(){
        for(WebDriver driver:drvList){
            driver.quit();
        }
    }
    
    public WebDriver getDriver(){
        int size;
        size = drvList.size();        
        if(size>0) return drvList.get((get_count++) % size);
        else  return null;
    }
    
    public WebDriver openByGet(String url, boolean change){
        WebDriver driver;
        int size;
        size = drvList.size();
        if(size>0){
            driver = drvList.get((get_count) % size);
            if(change) get_count++;
        }
        else  return null;
        
        if(delay_msec>0){
            try {
                Thread.sleep(delay_msec);
            } catch (InterruptedException ex) {
                Logger.getLogger(UserInfoParser.class.getName()).log(Level.SEVERE, null, ex);
            }              
        }
        driver.get(url);
        return driver;
    }
    
    public WebDriver openByGet(String url, int delay_msec, boolean change){
        WebDriver driver;
        int size;
        size = drvList.size();
        if(size>0){
            driver = drvList.get((get_count) % size);
            if(change) get_count++;
        }
        else  return null;
        
        if(delay_msec>0){
            try {
                Thread.sleep(delay_msec);
            } catch (InterruptedException ex) {
                Logger.getLogger(UserInfoParser.class.getName()).log(Level.SEVERE, null, ex);
            }              
        }
        driver.get(url);
        return driver;
    }    
    
    public void setDelayTime(int delay_msec){
        this.delay_msec = delay_msec;
    }
}