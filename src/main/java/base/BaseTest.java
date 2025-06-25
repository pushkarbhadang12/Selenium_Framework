package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;

import utils.ConfigReader;
import utils.ExtentReportManager;

public class BaseTest {
	
protected WebDriver driver;
protected static ExtentReports extent;
protected static ExtentTest test; 
	
	@BeforeSuite
	public void setUpReport() {
		extent = ExtentReportManager.getReportInstance();
	}
	
	@AfterSuite
	public void tearDownReport() {
		extent.flush();
	}

	@BeforeMethod
	public void setup() {
		ChromeOptions options = new ChromeOptions();
		//options.addArguments("--incognito");
		driver = new ChromeDriver(options);
		driver.manage().window().maximize();		
		driver.get(ConfigReader.readConfigValue("url"));
	}
	
	@AfterMethod
	public void tearDown(ITestResult result) {
		String screenShotPath = ExtentReportManager.captureScreenShot(driver, result.getName());
		
		if(result.getStatus() == ITestResult.FAILURE) {			
			System.out.println("Screenshot path: "+screenShotPath);
			test.fail("Test Failed. Check Screenshot", 
					MediaEntityBuilder.createScreenCaptureFromPath(screenShotPath).build());
			
		} else if(result.getStatus() == ITestResult.SUCCESS) {			
			System.out.println("Screenshot path: "+screenShotPath);
			test.pass("Test Passed. Check Screenshot", 
					MediaEntityBuilder.createScreenCaptureFromPath(screenShotPath).build());
		}
		
		if(driver!=null)
			driver.quit();
	}

}
