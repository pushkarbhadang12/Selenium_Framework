package utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportManager {

	private static ExtentReports extent;
	private static ExtentTest test;
	public static String reportPath;

	public static ExtentReports getReportInstance() {

		if (extent == null) {
			String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
			reportPath = "reports/ExtentReport_" + timestamp + ".html";
			ExtentSparkReporter reporter = new ExtentSparkReporter(reportPath);
			reporter.config().setDocumentTitle("Automation Test Report");
			reporter.config().setReportName("Test Execution Report");

			extent = new ExtentReports();
			extent.attachReporter(reporter);
		}
		return extent;
	}

	public static ExtentTest createTest(String testName) {

		test = getReportInstance().createTest(testName);

		return test;
	}
	
	public static String captureScreenShot(WebDriver driver, String screenShotName) {
				
		try {
			File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
			String path = Paths.get("").toAbsolutePath().toString() + "/screenshots/"+screenShotName+"_"+timestamp+".png";
			System.out.println("Path for screenshot: "+path);
			FileUtils.copyFile(src, new File(path));
			return path;
		} catch (IOException e) {			
			e.printStackTrace();
			return null;
		}
		
	}

}
