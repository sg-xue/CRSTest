package com.dianhua;

import static org.testng.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.List;

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
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions; 

public class getRecordsCMCC {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();
  long num, pwd = 0;
  ExcelUtils excelData;
  MysqlUtility sqlUtility = new MysqlUtility();
  String recordPhone = "";
  String startTime = "";
  String callType = "";
  String talkTime = "";
  String cityName = "";
  protected WebDriverWait wait;
//  String toastMsg = "服务器繁忙，请稍后再试";
  int RowNum = 1;
  int ColNum = 4;
  int totalNumber = 18;

  @BeforeClass(alwaysRun = true)
  public void setUp() throws Exception {
	System.setProperty("webdriver.gecko.driver", "/usr/local/share/geckodriver");
    driver = new FirefoxDriver();
    baseUrl = "http://crs-ui.dianhua.dev";
    wait=new WebDriverWait(driver, 60);
  }

  @Test
  public void testWebdriver() throws Exception {
    
	  excelData = new ExcelUtils("/home/yulore/workspace/CRSTest/simInfo.xlsx","Sheet1");
	  
	  
    //get toast message
//    WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated((By.partialLinkText(toastMsg))));
    while (RowNum < totalNumber) {
    	driver.get(baseUrl + "/");
        new WebDriverWait(driver, 5).until(
        	    ExpectedConditions.presenceOfElementLocated(By.id("tel"))
        	);
        
        
        WebElement tel_num = driver.findElement(By.id("tel"));
    	tel_num.click();
    	try {
    		num = excelData.getCellDataasnumber(RowNum, ColNum);
    		tel_num.sendKeys(String.valueOf(num));
    		Thread.sleep(3000);
            WebElement pin_pwd = driver.findElement(By.id("input_pin_pwd"));
            pwd = excelData.getCellDataasnumber(RowNum, ColNum+2);
            pin_pwd.click();
            pin_pwd.sendKeys(String.valueOf(pwd));
            try {
            	wait.until(ExpectedConditions.elementToBeClickable(By.id("p1_submit")));
            	driver.findElement(By.id("p1_submit")).click();
            }catch(Exception e){
            	System.out.println("can't login \n");
            	RowNum++;
            	continue;
            }
            
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	try {
    		WebElement result = (new WebDriverWait( driver, 60)).until(
        	  	    new ExpectedCondition< WebElement>(){
        	  	        public WebElement apply( WebDriver d) {
        	  	            return d.findElement(By.className("td_num"));
        	  	        }
        	  	    }
        	  	);
    	}catch(Exception e){
    		System.out.println("No records found \n");
        	RowNum++;
        	continue;
    	}
    	
    	
    	WebElement table = driver.findElement(By.tagName("table"));  
        List<WebElement> rows = table.findElements(By.tagName("tr"));  
        //check if there is a phone record
        System.out.printf("Total number of records %d\n", rows.size());
        if (rows.size() < 2) {
        	System.out.println("No records returned");
        } else {
        	String[] resultArray = {"","","",""};
            boolean firstRow = true;
            for(WebElement row:rows){
                List<WebElement> cols= row.findElements(By.tagName("td"));  
                if(firstRow == true) {
                	firstRow = false;
                	continue;
                }
                int i = 0;
                for(WebElement col:cols){  
                    System.out.print(col.getText()+"\t");    
                    resultArray[i] = col.getText();
                    i++;
                }
                recordPhone = resultArray[0];
                startTime = resultArray[1];
                callType = resultArray[2];
                talkTime = resultArray[3];
                cityName = excelData.getCellDataasstring(RowNum, 0);
                System.out.println("city is: " + cityName + "\n");
                if (recordPhone != "") {
                	sqlUtility.updateSQL(String.valueOf(num), recordPhone, startTime, callType, talkTime, cityName);
                	Thread.sleep(3000);
                }
                
            }
        }
        RowNum++;
    }
    
//    if(result != null) {
//    	takeScreenShot(driver, String.valueOf(num));
//    	Thread.sleep(3000);
//    }
    
  }

  @AfterClass(alwaysRun = true)
  public void tearDown() throws Exception {
    driver.close();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }
/***
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
  ***/
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

