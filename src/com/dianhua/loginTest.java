package com.dianhua;

import io.appium.java_client.android.AndroidDriver;  
//import io.appium.java_client.android.AndroidKeyCode;  
  
//import java.io.File;  
import java.net.MalformedURLException;  
import java.net.URL;  
//import java.util.List;  
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;  
import org.openqa.selenium.remote.DesiredCapabilities;  
import org.testng.Assert;  
import org.testng.annotations.AfterMethod;  
import org.testng.annotations.AfterSuite;  
import org.testng.annotations.BeforeMethod;  
import org.testng.annotations.BeforeSuite;  
import org.testng.annotations.Test;  
  
public class loginTest {  
    private AndroidDriver<?> driver;  
  
    @BeforeSuite  
    public void beforeSuite() throws MalformedURLException {  
        // set up appium 
//        File classpathRoot = new File(System.getProperty("user.dir"));  
//        File appDir = new File(classpathRoot, "apps");  
//        File app = new File(appDir, "AndroidApp.apk");// 要测试的App  
        DesiredCapabilities capabilities = new DesiredCapabilities();  
        capabilities.setCapability("platformName", "Android");  
        capabilities.setCapability("deviceName", "79c1e852");  
        capabilities.setCapability("platformVersion", "4.3");
//        capabilities.setCapability("app", app.getAbsolutePath());  
        capabilities.setCapability("appPackage", "com.vivo.browser");// 包名  
        capabilities.setCapability("appActivity", ".MainActivity");  
        capabilities.setCapability("unicodeKeyboard", "True"); // 输入中文  
        capabilities.setCapability("resetKeyboard", "True");  
        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"),  
                capabilities);  
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
        driver.quit();
    }  
  
    @Test  
    public void testLogin() throws Exception {  
        System.out.println("mainTest");  
        // 打开页面 
        driver.findElementById("com.vivo.browser:id/search_text").click();  
        WebElement search_box = driver.findElement(By.id("com.vivo.browser:id/edit"));
        search_box.sendKeys("crs-ui.dianhua.dev");
        sleep(3);
        driver.findElementById("com.vivo.browser:id/go").click();
        sleep(5);
        
        //输入号码
        driver.context("WEBVIEW_com.vivo.browser");
        WebElement tel_num = driver.findElementById("tel");
        tel_num.sendKeys("13294396470");
//        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS); 
        sleep(5);
        WebElement pin_pwd = driver.findElementById("input_pin_pwd");
        sleep(5);
        pin_pwd.sendKeys("074693");
        sleep(5);
//        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.findElementById("p1_submit").click();
        sleep(5);
//        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
//        driver.switch_to.context("WEBVIEW_com.vivo.browser")
//                print self.driver.context
//                sleep(5)
//                self.driver.find_element_by_id('js_tel_more').click()
//                sleep(3)
//                self.driver.switch_to.context("NATIVE_APP")
        
//        Assert.assertEquals("点击次数:" + i, result);  
//        // 等待  
//        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);  
//        // 通过控件类型获取控件列表  
//        List<WebElement> textFieldsList = driver  
//                .findElementsByClassName("android.widget.EditText");  
//        textFieldsList.get(0).sendKeys("123456789");  
//        // driver.findElementById("et").sendKeys("123456789");  
//        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);  
//        driver.findElementByName("跳转").click();  
    }  
  
//    @Test  
//    public void test2() throws Exception {  
//        System.out.println("anotherTest");  
//        String result = driver.findElementById("another_tv").getText();  
//        //Assert.assertEquals("1234567890", result);  
//        driver.findElementByName("按钮2").click();  
//          
//        //driver.sendKeyEvent(AndroidKeyCode.BACK);  
//    }  
    
    public static void sleep(int seconds) {
    	try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}  
