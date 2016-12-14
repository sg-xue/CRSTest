package com.dianhua;

import static org.testng.Assert.fail;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class getRecordsCUC {
  private WebDriver driver;
  private String baseUrl;
  private StringBuffer verificationErrors = new StringBuffer();
  ExcelUtils excelData;
  MysqlUtility sqlUtility = new MysqlUtility();
  String recordPhone = "";
  String startTime = "";
  String callType = "";
  String talkTime = "";
  boolean morePages = false;
  protected WebDriverWait wait;

  @BeforeClass(alwaysRun = true)
  public void setUp() throws Exception {
	System.setProperty("webdriver.gecko.driver", "/usr/local/share/geckodriver");
    driver = new FirefoxDriver();
    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);  
    baseUrl = "http://crs-ui.dianhua.dev";
    wait=new WebDriverWait(driver, 60);
  }
  
  @DataProvider(name="simCard")
  public static Object[][] data() throws IOException  
  {  
      return getSearchData("CUC-SIM-Info.csv");
  } 

//  @Parameters({"city","num","pwd"})
//  public void testWebdriver(@Optional("北京") String city, @Optional("18612012053")String num, @Optional("900319")String pwd) throws Exception {
  @Test (dataProvider="simCard") 
  public void testWebdriver(String city, String num, String pwd) throws Exception {
    	driver.get(baseUrl + "/");
        new WebDriverWait(driver, 5).until(
        	    ExpectedConditions.presenceOfElementLocated(By.id("tel"))
        	);
        
        WebElement tel_num = driver.findElement(By.id("tel"));
    	tel_num.click();
    	try {
    		System.out.printf("城市：　%s，电话: %s \n", city, num);
    		tel_num.sendKeys(num);
    		Thread.sleep(3000);
            WebElement pin_pwd = driver.findElement(By.id("input_pin_pwd"));
            pin_pwd.click();
            pin_pwd.sendKeys(pwd);
            wait.until(ExpectedConditions.elementToBeClickable(By.id("p1_submit")));
            driver.findElement(By.id("p1_submit")).click();
            
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	System.out.println("start deal with result table");
//    	wait.until(ExpectedConditions.elementToBeSelected(By.tagName("tbody")));
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
    	}
    	//get records and save to DB
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
                System.out.println();
                recordPhone = resultArray[0];
                startTime = resultArray[1];
                callType = resultArray[2];
                talkTime = resultArray[3];
                if (recordPhone != "") {
                	sqlUtility.updateSQL(String.valueOf(num), recordPhone, startTime, callType, talkTime, city);
                	Thread.sleep(3000);
                }
                
            }
        }
        
        //check if there are more records on other page
        WebElement secPage = driver.findElement(By.partialLinkText("2"));
        System.out.println("partialLinkText pages: "+secPage.getText());
        List<WebElement> hrefs = driver.findElement(By.xpath("//a[contains(@href, 'page_no')]"));
        System.out.println("Total page is: " + hrefs.size());
        int nextPage = 1;
        for(WebElement h:hrefs) {
        	System.out.println("xpath pages: "+h.getText());
        	System.out.println("get href value: "+h.getAttribute("href"));
        	Pattern pattern =  Pattern.compile("/calls.+page_no=(.+)");
    		Matcher match = pattern.matcher(h.getAttribute("href"));
    		if(match.find()){
    			System.out.println(match.group(1));
    		}else{
    			System.out.println("pattern not find");
    		}
    		if (nextPage < hrefs.size()) {
    			driver.findElement(By.linkText(match.group(1))).click();;
    		}
        }
//        secPage.click();
        Thread.sleep(3000);
    
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
  
  public static Object[][] getSearchData(String FileNameroot) throws IOException{  
      List<Object[]> records=new ArrayList<Object[]>();  
      String record;  
      BufferedReader file=new BufferedReader(new InputStreamReader(new FileInputStream(FileNameroot),"UTF-8"));  
      //忽略读取CSV文件的标题行（第一行）  
      file.readLine();  
      //遍历读取文件中除第一行外的其他所有内容并存储在名为records的ArrayList中，每一行records中存储的对象为一个String数组  
      while((record=file.readLine())!=null){  
          String fields[]=record.split(",");  
          records.add(fields);  
      }  
      //关闭文件对象  
      file.close();  
      //将存储测试数据的List转换为一个Object的二维数组  
      Object[][] results=new Object[records.size()][];  
      //设置二位数组每行的值，每行是一个Object对象  
      for(int i=0;i<records.size();i++){  
          results[i]=records.get(i);  
      }  
      return results;       
  }


}

