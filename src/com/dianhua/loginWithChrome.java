package com.dianhua;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;  
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;  
import org.testng.annotations.AfterSuite;  
import org.testng.annotations.BeforeMethod;  
import org.testng.annotations.BeforeSuite;  
import org.testng.annotations.Test;

  
public class loginWithChrome {  
    AppiumDriver<WebElement> driver;  
    long num, pwd = 0;
    ExcelUtils excelData;
  
    @BeforeSuite  
    public void beforeSuite() throws MalformedURLException {  
        // set up appium 
        DesiredCapabilities capabilities = new DesiredCapabilities();  
        capabilities.setCapability("BROWSER_NAME", "Chrome");
        capabilities.setCapability("platformName", "Android");  
        capabilities.setCapability("deviceName", "vivo Android Phone");  
        capabilities.setCapability("platformVersion", "4.3");
        capabilities.setCapability("appPackage", "com.android.chrome");
        capabilities.setCapability("appActivity", "com.android.chrome/org.chromium.chrome.browser.ChromeTabbedActivity"); 
//        capabilities.setCapability("appActivity", "com.google.android.apps.chrome.Main"); 
//        capabilities.setCapability("unicodeKeyboard", "True"); 
//        capabilities.setCapability("resetKeyboard", "True");  
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
    	sleep(2);
    	driver.context("WEBVIEW_1");
    	WebElement url_add = driver.findElementById("com.android.chrome:id/url_bar");
//        WebElement url_add = driver.findElementById("url_bar_translating_edge");
        url_add.clear();
        url_add.sendKeys("crs-ui.dianhua.dev" + "\n");
        sleep(3); 
        
        // input tel number and pwd
        
        
        new WebDriverWait(driver, 5).until(
        	    ExpectedConditions.presenceOfElementLocated(By.id("tel"))
        	);
        
        
        int RowNum = 25;
        int ColNum = 4;
        try {
    		excelData = new ExcelUtils("/home/yulore/workspace/CRSTest/simInfo.xlsx","Sheet1");
    		num = excelData.getCellDataasnumber(RowNum, ColNum);
    		WebElement tel_num = driver.findElement(By.id("tel"));
    		tel_num.click();
    		tel_num.sendKeys(String.valueOf(num));
    		Thread.sleep(3000);
            WebElement pin_pwd = driver.findElement(By.id("input_pin_pwd"));
            pwd = excelData.getCellDataasnumber(RowNum, ColNum+2);
            pin_pwd.click();
            pin_pwd.sendKeys(String.valueOf(pwd));
            driver.findElement(By.id("p1_submit")).click();
            
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
        
        ExpectedConditions.presenceOfElementLocated(By.className("td_num"));
        WebElement result = (new WebDriverWait( driver, 60)).until(
        	    new ExpectedCondition< WebElement>(){
        	        public WebElement apply( WebDriver d) {
        	            return d.findElement(By.className("td_num"));
        	        }
        	    }
        	);
        if(result != null) {
        	File scrFile = driver.getScreenshotAs(OutputType.FILE);
            try {
            	FileUtils.copyFile(scrFile, new File(num + "-" + getCurrentDateTime()+ ".png")); 
            } catch (IOException e) {
                System.out.println("Can't save screenshot!");
                e.printStackTrace();
            }
        }
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
