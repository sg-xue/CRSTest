package com.dianhua;

import io.appium.java_client.android.AndroidDriver;  
//import io.appium.java_client.android.AndroidKeyCode;  

import java.io.File;
import java.io.IOException;
//import java.io.File;  
import java.net.MalformedURLException;  
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
//import java.util.List;  
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;  
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;  
import org.testng.annotations.AfterMethod;  
import org.testng.annotations.AfterSuite;  
import org.testng.annotations.BeforeMethod;  
import org.testng.annotations.BeforeSuite;  
import org.testng.annotations.Test;

  
public class loginTest {  
    private AndroidDriver<?> driver;  
    long num, pwd = 0;
  
    @BeforeSuite  
    public void beforeSuite() throws MalformedURLException {  
        // set up appium 
//        File classpathRoot = new File(System.getProperty("user.dir"));  
//        File appDir = new File(classpathRoot, "apps");  
//        File app = new File(appDir, "AndroidApp.apk");
        DesiredCapabilities capabilities = new DesiredCapabilities();  
        capabilities.setCapability("platformName", "Android");  
        capabilities.setCapability("deviceName", "79c1e852");  
        capabilities.setCapability("platformVersion", "4.3");
//        capabilities.setCapability("app", app.getAbsolutePath());  
        capabilities.setCapability("appPackage", "com.vivo.browser");
        capabilities.setCapability("appActivity", ".MainActivity");  
//        capabilities.setCapability("unicodeKeyboard", "True"); 
//        capabilities.setCapability("resetKeyboard", "True");  
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
    }  
  
    @Test  
    public void testLogin() throws Exception {  
        
        ExcelUtils excelData;
        
        // ��ҳ�� 
        driver.findElementById("com.vivo.browser:id/search_text").click();  
        WebElement search_box = driver.findElement(By.id("com.vivo.browser:id/edit"));
        search_box.sendKeys("crs-ui.dianhua.dev");
        sleep(3);
        driver.findElementById("com.vivo.browser:id/go").click();
        sleep(2);
        
        //�������
        driver.context("WEBVIEW_com.vivo.browser");
        WebElement tel_num = driver.findElementById("tel");
        int RowNum = 31;
        int ColNum = 4;
        try {
			excelData = new ExcelUtils("simInfo.xlsx","Sheet1");
			num = excelData.getCellDataasnumber(RowNum, ColNum);
			tel_num.sendKeys(String.valueOf(num));
			sleep(2);
	        WebElement pin_pwd = driver.findElementById("input_pin_pwd");
	        sleep(2);
	        pwd = excelData.getCellDataasnumber(RowNum, ColNum+2);
	        pin_pwd.sendKeys(String.valueOf(pwd));
//	        sleep(5);
		} catch (Exception e) {
			e.printStackTrace();
		}
        ExpectedConditions.elementToBeClickable(driver.findElementById("p1_submit"));
        driver.findElementById("p1_submit").click();
        driver.context("NATIVE_APP");
//        sleep(3);
//        WebElement frame=driver.findElementById("com.vivo.browser:id/parentPanel");
//        driver.switchTo().frame(frame);
//        if (driver.findElementById("com.vivo.browser:id/parentPanel") != null) {
//        	driver.findElementById("com.vivo.browser:id/button1").click();
//        }
//        driver.switchTo().defaultContent();
        sleep(20);
        File scrFile = driver.getScreenshotAs(OutputType.FILE);
        try {
        	FileUtils.copyFile(scrFile, new File(num + "-" + getCurrentDateTime()+ ".png")); 
        } catch (IOException e) {
            System.out.println("Can't save screenshot!");
            e.printStackTrace();
        }
        
        driver.context("WEBVIEW_com.vivo.browser");
//        Assert.assertNotNull(driver.findElementsByClassName("td_num"));
        Assert.assertNotNull(driver.findElementByXPath("//*[@id='p_index']/table/thead/tr/td[4])"));
//      *[@id="p_index"]/table/thead/tr/td[4]
        sleep(3);
//        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
//        driver.switch_to.context("WEBVIEW_com.vivo.browser")
//                print self.driver.context
//                sleep(5)
//                self.driver.find_element_by_id('js_tel_more').click()
//                sleep(3)
//                self.driver.switch_to.context("NATIVE_APP")
        
//        Assert.assertEquals("�������:" + i, result);  
//        // �ȴ�  
//        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);  
//        // ͨ���ؼ����ͻ�ȡ�ؼ��б�  
//        List<WebElement> textFieldsList = driver  
//                .findElementsByClassName("android.widget.EditText");  
//        textFieldsList.get(0).sendKeys("123456789");  
//        // driver.findElementById("et").sendKeys("123456789");  
//        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);  
//        driver.findElementByName("��ת").click();  
    }  
  
//    @Test  
//    public void test2() throws Exception {  
//        System.out.println("anotherTest");  
//        String result = driver.findElementById("another_tv").getText();  
//        //Assert.assertEquals("1234567890", result);  
//        driver.findElementByName("��ť2").click();  
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
    

    public static String getCurrentDateTime(){
       SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd_HHmmss");//设置日期格式
       return df.format(new Date());
    }
}  
