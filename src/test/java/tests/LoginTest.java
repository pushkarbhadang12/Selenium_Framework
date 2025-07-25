package tests;

import java.io.IOException;
import java.lang.reflect.Method;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
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
	public void loginTest(String username, String password, Method method) {

		test = ExtentReportManager.createTest(method.getName());
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

		test.info("Verifying presence of HomeLink");
		Log.info("Verifying presence of HomeLink");		
		
		SoftAssert softAssert = null;
		
		if(loginPage.checkPresenceOfEditAccountLink()==true) {
			test.pass("Login Successful.");
			Log.info("Login Successful.");
			softAssert = new SoftAssert();
			softAssert.assertEquals(loginPage.checkPresenceOfEditAccountLink(), true);
		}
		else {
			test.fail("Login Failed. Error Message received");
			Log.error("Login Failed. Error Message received");
			Assert.fail("Login Failed. Error Message received");
		}		
		
		softAssert.assertAll();
	}

}
