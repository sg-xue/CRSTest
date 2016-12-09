package com.dianhua;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;  

import java.net.MalformedURLException;  
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.AfterMethod;  
import org.testng.annotations.AfterSuite;  
import org.testng.annotations.BeforeMethod;  
import org.testng.annotations.BeforeSuite;  
import org.testng.annotations.Test;

  
public class loginOnFirefox {  
    AppiumDriver<WebElement> driver;  
    long num, pwd = 0;
  
    @BeforeSuite  
    public void beforeSuite() throws MalformedURLException {  
        // set up appium 
        DesiredCapabilities capabilities = new DesiredCapabilities();  
        capabilities.setCapability("BROWSER_NAME", "Chrome");
        capabilities.setCapability("platformName", "Android");  
        capabilities.setCapability("deviceName", "vivo Android Phone");  
        capabilities.setCapability("platformVersion", "4.3");
//        capabilities.setCapability("appPackage", "org.mozilla.firefox");
//        capabilities.setCapability("appActivity", "org.mozilla.gecko.BrowserApp"); 
        capabilities.setCapability("appPackage", "com.android.chrome");
        capabilities.setCapability("appActivity", "com.android.chrome/org.chromium.chrome.browser.ChromeTabbedActivity"); 
//        capabilities.setCapability("appActivity", "com.google.android.apps.chrome.Main"); 
//        capabilities.setCapability("unicodeKeyboard", "True"); 
//        capabilities.setCapability("resetKeyboard", "True");  
//        System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
        driver = new AndroidDriver<WebElement>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);  
        
//        logger.info("Context:   "+((AndroidDriver<?>)driver).getContext());
    }  
  
    @AfterSuite  
    public void afterSuite() {  
        driver.quit();  
    }  
  
    @BeforeMethod  
    public void beforeMethod() throws Exception {  
        System.out.println("beforeMethod");  
    }  
  
    @AfterMethod  
    public void afterMethod() throws Exception {  
        System.out.println("afterMethod"); 
    }  
  
    @Test  
    public void testLogin() throws Exception {  
    	
    	// open login page
//    	driver.findElementById("url_bar_title").click();
//        WebElement url_add = driver.findElementById("url_bar_translating_edge");
//        url_add.clear();
//        url_add.sendKeys("crs-ui.dianhua.dev" + "\n");
//        sleep(3); 
        
        // input tel number and pwd
        Thread.sleep(2500);
        Set<String> contextSet=driver.getContextHandles();
        System.out.println(contextSet);
        for(String context : contextSet){
        	System.out.println(context);
        }
        Thread.sleep(2500);
//        driver.context("webview");
//        ExpectedConditions.presenceOfElementLocated(By.id("tel"));
//        WebElement tel_num = driver.findElementById("tel");
//        tel_num.sendKeys("123");
//        sleep(5);
    }  
  
    public static void sleep(int seconds) {
    	try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    

    public static String getCurrentDateTime(){
       SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd_HHmmss");
       return df.format(new Date());
    }
}  
