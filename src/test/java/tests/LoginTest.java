package tests;

import java.io.IOException;
import java.nio.file.Paths;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import base.BaseTest;
import pages.LoginPage;
import utils.CommonFunctions;
import utils.ConfigReader;
import utils.ExcelUtils;
import utils.ExtentReportManager;
import utils.Log;

public class LoginTest extends BaseTest {
	
	@DataProvider(name="LoginData")
	public Object[][] getLoginData() throws IOException{
		String filePath = CommonFunctions.getProjectPath() + "/testdata/TestData.xlsx";
		ExcelUtils.loadExcel(filePath, "Login");
		int rowCount = ExcelUtils.getRowCount();
		Object [][] data = new Object[rowCount-1][2];
		
		for(int i=1; i<rowCount; i++) {
			data[i-1][0] = ExcelUtils.getCellData(i, 0); //Username
			data[i-1][1] = ExcelUtils.getCellData(i, 1); //Password
		}
		ExcelUtils.closeWorkbook();;
		return data;
	}

	@Test(dataProvider = "LoginData")
	public void validLoginTest(String username, String password) {

		test = ExtentReportManager.createTest("ValidLoginTest");
		LoginPage loginPage = new LoginPage(driver);
		
		loginPage.enterEmailId(username);
		test.info("Entered Email Id: "+username);
		Log.info("Entered Email Id: "+username);
		
		loginPage.enterPassword(password);
		test.info("Entered Password: "+password);
		Log.info("Entered Password: "+password);
		
		test.info("Logging in to the application");
		Log.info("Logging in to the application");
		loginPage.clickLogin();		

		test.info("Verifying Page Title");
		Log.info("Verifying Page Title");
		String pageTitle = loginPage.getPageTitle();
		System.out.println("Page Title is: " + pageTitle);
		test.info("Page Title is: " + pageTitle);
		Log.info("Page Title is: " + pageTitle);
		
		if(pageTitle.equalsIgnoreCase("My Account")) {
			test.pass("Login Successful");
			Log.info("Login Successful");
		}
		else if(loginPage.checkPresenceOfErrorMessage()==true) {
			test.fail("Login Failed. Error Message received: "+loginPage.getErrorMessage());
			Log.error("Login Failed. Error Message received: "+loginPage.getErrorMessage());
		}		
		
		SoftAssert softAssert = new SoftAssert();
		softAssert.assertEquals(pageTitle, "My Account");
		softAssert.assertAll();
	}

	
	public void invalidLoginTest() {
		test = ExtentReportManager.createTest("InvalidLoginTest");
		LoginPage loginPage = new LoginPage(driver);

		loginPage.enterEmailId(ConfigReader.readConfigValue("emailId"));
		test.info("Entered Email Id: "+ConfigReader.readConfigValue("emailId"));
		Log.info("Entered Email Id: "+ConfigReader.readConfigValue("emailId"));
		
		loginPage.enterPassword(ConfigReader.readConfigValue("emailId"));
		test.info("Entered Invalid Password: "+ConfigReader.readConfigValue("emailId"));
		Log.info("Entered Invalid Password: "+ConfigReader.readConfigValue("emailId"));
		
		loginPage.clickLogin();
		
		test.info("Verifying Error Message");
		Log.info("Verifying Error Message");
		
		if(loginPage.checkPresenceOfErrorMessage()==true) {
			test.pass("Verified Error Message Successfully. Received Error Message as "+loginPage.getErrorMessage());
			Log.info("Verified Error Message Successfully. Received Error Message as "+loginPage.getErrorMessage());
		}
		else {
			test.fail("Unable to verify Error Message");
			Log.error("Unable to verify Error Message");
		}
		
		Assert.assertEquals(loginPage.checkPresenceOfErrorMessage(), true);
	}

}
