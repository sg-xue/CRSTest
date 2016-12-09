package com.dianhua;

import static org.testng.Assert.fail;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class loginTestWebdriver {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();
  long num, pwd = 0;
  ExcelUtils excelData;

  @BeforeClass(alwaysRun = true)
  public void setUp() throws Exception {
	System.setProperty("webdriver.gecko.driver", "/usr/local/share/geckodriver");
    driver = new FirefoxDriver();
    baseUrl = "http://crs-ui.dianhua.dev";
    
  }

  @Test
  public void testWebdriver() throws Exception {
    driver.get(baseUrl + "/");
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
    	takeScreenShot(driver, String.valueOf(num));
    	Thread.sleep(3000);
    }
    
//    WebElement td_num = driver.findElements(By.className("td_num"));
    
  }

  @AfterClass(alwaysRun = true)
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
  
  public void takeScreenShot(WebDriver driver, String name){
	  File output = null;
	  File file;
	  output = ((TakesScreenshot) driver)
	                  .getScreenshotAs(OutputType.FILE);
	  file = new File(name + ".png");
	  try {
	      FileUtils.copyFile(output, file);
	  } catch (IOException e) {
	      e.printStackTrace();
	  }
	}

}
