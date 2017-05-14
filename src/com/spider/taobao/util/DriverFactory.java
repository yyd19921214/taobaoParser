package com.spider.taobao.util;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class DriverFactory {
    public static WebDriver getHtmlUnitDriver(){
        return (new HtmlUnitDriver());
    }
    
    public static WebDriver getInternetExplorerDriver(){
        return (new InternetExplorerDriver());
    }
    
    public static WebDriver getChromeDriver(){
        ChromeDriverService service=new ChromeDriverService.Builder().usingDriverExecutable(
                new File("C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe")).usingAnyFreePort().build(); 
        try {
            service.start();
        } catch (IOException ex) {
            Logger.getLogger(DriverFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
        WebDriver driver = new RemoteWebDriver(service.getUrl(),DesiredCapabilities.chrome());        
        return driver;
    }
    
    public static WebDriver getFirefoxDriverTextOnlyWithProxy(String proxyIP, int proxyPort){
        //System.setProperty ( "webdriver.firefox.bin" , "e:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe" );
        FirefoxProfile fp=new FirefoxProfile();       


        // http协议代理配置
        fp.setPreference("network.proxy.http", proxyIP);
        fp.setPreference("network.proxy.http_port", proxyPort);
        // 所有协议公用一种代理配置，如果单独配置，这项设置为false，再类似于http的配置
        fp.setPreference("network.proxy.share_proxy_settings", true);
        // 对于localhost的不用代理，这里必须要配置，否则无法和webdriver通讯
        fp.setPreference("network.proxy.no_proxies_on", "localhost");       
        fp.setPreference("network.proxy.type", 1);      // 使用代理  
        
        //fp.setPreference("permissions.default.image", 2);//禁止下载图像
        fp.setPreference("plugins.click_to_play", true);//点击后才运行插件
        fp.setPreference("browser.cache.disk.parent_directory", "E:\\Temp\\Firefox\\Debug");//缓存文件保存机制

        fp.setPreference("webdriver.load.strategy", "unstable"); //快速模式        
        //fp.setPreference("permissions.default.script", 2);  //无脚本
        //fp.setPreference("permissions.default.stylesheet", 2);  //无CSS
        fp.setPreference("dom.ipc.plugins.enabled.libflashplayer.so",false);    //无flash
        
        //fp.setPreference("network.proxy.type", 0);
        fp.setPreference("dom.disable_open_during_load", false);
        fp.setPreference("privacy.popups.disable_from_plugins", 3);//禁止弹出窗口
        fp.setPreference("privacy.popups.firstTime", false);//禁止插件弹窗
        fp.setPreference("dom.popup_maximum", 0);
        
        FirefoxBinary fb=new FirefoxBinary();
        fb.setTimeout(60000);
        return (new FirefoxDriver(fb,fp));
        
    }
    
    public static WebDriver getFirefoxDriverWithProxy(String proxyIP, int proxyPort){
        //System.setProperty ( "webdriver.firefox.bin" , "e:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe" );
        FirefoxProfile fp=new FirefoxProfile();       


        // http协议代理配置
        fp.setPreference("network.proxy.http", proxyIP);
        fp.setPreference("network.proxy.http_port", proxyPort);
        fp.setPreference("network.proxy.share_proxy_settings", true);       // 所有协议公用一种代理配置，如果单独配置，这项设置为false，再类似于http的配置
        fp.setPreference("network.proxy.no_proxies_on", "localhost");       // 对于localhost的不用代理，这里必须要配置，否则无法和webdriver通讯
        fp.setPreference("network.proxy.type", 1);      // 使用代理  

        //fp.setPreference("browser.cache.disk.parent_directory", "E:\\Temp\\Firefox\\Debug");//缓存文件保存机制
        
        fp.setPreference("plugins.click_to_play", true);//点击后才运行插件        
        fp.setPreference("permissions.default.image", 2);//禁止下载图像
        //fp.setPreference("webdriver.load.strategy", "unstable"); //快速模式        
        //fp.setPreference("permissions.default.script", 2);  //无脚本
        fp.setPreference("permissions.default.stylesheet", 2);  //无CSS
        fp.setPreference("dom.ipc.plugins.enabled.libflashplayer.so",false);    //无flash
        

        fp.setPreference("dom.disable_open_during_load", false);
        fp.setPreference("privacy.popups.disable_from_plugins", 3);
        fp.setPreference("privacy.popups.firstTime", false);
        fp.setPreference("dom.popup_maximum", 0);
        
        FirefoxBinary fb=new FirefoxBinary();
        fb.setTimeout(60000);
        return (new FirefoxDriver(fb,fp));
        
    }
        
    public static WebDriver getFirefoxDriverTextOnly(){
        //System.setProperty ( "webdriver.firefox.bin" , "e:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe" );
        FirefoxProfile fp=new FirefoxProfile();       
        //fp.setPreference("browser.cache.disk.parent_directory", "E:\\Temp\\Firefox\\Debug");//缓存文件保存机制
        
        fp.setPreference("permissions.default.image", 2);//禁止下载图像
        fp.setPreference("plugins.click_to_play", true);//点击后才运行插件
        //fp.setPreference("webdriver.load.strategy", "unstable"); //快速模式        
        //fp.setPreference("permissions.default.script", 2);  //无脚本
        fp.setPreference("permissions.default.stylesheet", 2);  //无CSS
        fp.setPreference("dom.ipc.plugins.enabled.libflashplayer.so",false);    //无flash
        
        fp.setPreference("network.proxy.type", 0);
        fp.setPreference("dom.disable_open_during_load", false);
        fp.setPreference("privacy.popups.disable_from_plugins", 3);//禁止弹出窗口
        fp.setPreference("privacy.popups.firstTime", false);//禁止插件弹窗
        fp.setPreference("dom.popup_maximum", 0);
        
        FirefoxBinary fb=new FirefoxBinary();
        fb.setTimeout(60000);
        return (new FirefoxDriver(fb,fp));
        
    }
    
    public static WebDriver getFirefoxDriverWithoutImage(){
        //System.setProperty ( "webdriver.firefox.bin" , "e:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe" );
        FirefoxProfile fp=new FirefoxProfile();
        fp.setPreference("permissions.default.image", 2);
        //fp.setPreference("plugins.click_to_play", true);
        fp.setPreference("browser.cache.disk.parent_directory", "E:\\Temp\\Firefox\\Debug");    
        
        fp.setPreference("network.proxy.type", 0);
        fp.setPreference("dom.disable_open_during_load", false);
        fp.setPreference("privacy.popups.disable_from_plugins", 3);
        fp.setPreference("privacy.popups.firstTime", false);
        fp.setPreference("dom.popup_maximum", 0);
        
        FirefoxBinary fb=new FirefoxBinary();
        fb.setTimeout(60000);
        return new FirefoxDriver(fb,fp);   
    }
    
    
    public static WebDriver getFirefoxDriverWithImage(){
        //System.setProperty ( "webdriver.firefox.bin" , "e:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe" );
        FirefoxProfile fp=new FirefoxProfile();
        //fp.setPreference("permissions.default.image", 2);
        fp.setPreference("plugins.click_to_play", true);
        //fp.setPreference("browser.cache.disk.parent_directory", "E:\\Temp\\Firefox\\Debug");

        //fp.setPreference("permissions.default.script", 2);  //无脚本
        //fp.setPreference("permissions.default.stylesheet", 2);  //无CSS
        //fp.setPreference("dom.ipc.plugins.enabled.libflashplayer.so",false);    //无flash
        
        fp.setPreference("network.proxy.type", 0);
        fp.setPreference("dom.disable_open_during_load", false);
        fp.setPreference("privacy.popups.disable_from_plugins", 3);
        fp.setPreference("privacy.popups.firstTime", false);
        fp.setPreference("dom.popup_maximum", 0);
        
        FirefoxBinary fb=new FirefoxBinary();
        fb.setTimeout(60000);
        return new FirefoxDriver(fb,fp);         
    }
    
    public static WebDriver getFirefoxDriver(){
        //System.setProperty ( "webdriver.firefox.bin" , "e:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe" );
        //System.setProperty ();
        FirefoxProfile fp=new FirefoxProfile();
        //fp.setPreference("permissions.default.image", 2);
        //fp.setPreference("plugins.click_to_play", true);
        //fp.setPreference("browser.cache.disk.parent_directory", "E:\\Temp\\Firefox\\Debug");
        
        fp.setPreference("network.proxy.type", 0);
        //fp.setPreference("permissions.default.script", 1);
        fp.setPreference("dom.disable_open_during_load", false);
        fp.setPreference("privacy.popups.disable_from_plugins", 3);
        fp.setPreference("privacy.popups.firstTime", false);
        fp.setPreference("dom.popup_maximum", 0);
        
        FirefoxBinary fb=new FirefoxBinary();
        fb.setTimeout(60000);
        return new FirefoxDriver(fb,fp); 
        //return new FirefoxDriver();
    }
    
    public static WebDriver getdefaultFirefoxDriver(){
        //System.setProperty ( "webdriver.firefox.bin" , "e:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe" ); 

        FirefoxProfile profile = new ProfilesIni().getProfile("default"); 
        DesiredCapabilities dCap = DesiredCapabilities.firefox(); 
        dCap.setCapability(FirefoxDriver.PROFILE, profile); 
        return new FirefoxDriver(dCap);
        
//        FirefoxProfile fp=new FirefoxProfile();
//        //fp.setPreference("permissions.default.image", 2);
//        //fp.setPreference("plugins.click_to_play", true);
//        fp.setPreference("browser.cache.disk.parent_directory", "E:\\Temp\\Firefox\\Debug");
//       
//        fp.setPreference("network.proxy.type", 0);
//        fp.setPreference("dom.disable_open_during_load", false);
//        fp.setPreference("privacy.popups.disable_from_plugins", 3);
//        fp.setPreference("privacy.popups.firstTime", false);
//        fp.setPreference("dom.popup_maximum", 0);
//        
//        FirefoxBinary fb=new FirefoxBinary();
//        fb.setTimeout(60000);
//        return new FirefoxDriver(fb,fp);          
    }
}
