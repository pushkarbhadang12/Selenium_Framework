package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import io.restassured.RestAssured;
import utils.ConfigReader;
import utils.EmailUtils;
import utils.ExtentReportManager;
import utils.Log;

public class BaseTest {

	protected WebDriver driver;
	protected static ExtentReports extent;
	protected static ExtentTest test;
	private String className;

	@BeforeSuite
	public void setUpReport() {
		extent = ExtentReportManager.getReportInstance();
	}

	@AfterSuite
	public void tearDownReport() {
		extent.flush();
		String reportPath = ExtentReportManager.reportPath;
		EmailUtils.sendTestReport(reportPath);
	}

	@BeforeMethod
	public void setup(ITestContext context) {
		className = this.getClass().getSimpleName();
		if (!className.contains("API")) {
			Log.info("Starting Webdriver...");
			ChromeOptions options = new ChromeOptions();
			// options.addArguments("--incognito");
			driver = new ChromeDriver(options);
			driver.manage().window().maximize();
			Log.info("Navigting to URL..." + ConfigReader.readConfigValue("url"));
			driver.get(ConfigReader.readConfigValue("url"));
		} else {
			String baseURI = ConfigReader.readConfigValue("api_URL");
			RestAssured.baseURI = baseURI;
		}

	}

	@AfterMethod
	public void tearDown(ITestResult result) {
		if (className.contains("API")) {
			if (result.getStatus() == ITestResult.FAILURE) {
				test.fail("Test Failed...");
			} else if (result.getStatus() == ITestResult.SUCCESS) {
				test.pass("Test Passed....");
			}
		} else {
			String screenShotPath = ExtentReportManager.captureScreenShot(driver, result.getName());

			if (result.getStatus() == ITestResult.FAILURE) {
				System.out.println("Screenshot path: " + screenShotPath);
				test.fail("Test Failed. Check Screenshot",
						MediaEntityBuilder.createScreenCaptureFromPath(screenShotPath).build());

			} else if (result.getStatus() == ITestResult.SUCCESS) {
				System.out.println("Screenshot path: " + screenShotPath);
				test.pass("Test Passed. Check Screenshot",
						MediaEntityBuilder.createScreenCaptureFromPath(screenShotPath).build());
			}

			if (driver != null) {
				Log.info("Closing Browser...");
				driver.quit();
			}
		}
	}

}
