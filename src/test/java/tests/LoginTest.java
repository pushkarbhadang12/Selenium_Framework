package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import base.BaseTest;
import pages.LoginPage;
import utils.ConfigReader;
import utils.ExtentReportManager;

public class LoginTest extends BaseTest {

	@Test
	public void validLoginTest() {

		test = ExtentReportManager.createTest("ValidLoginTest");
		LoginPage loginPage = new LoginPage(driver);

		loginPage.enterEmailId(ConfigReader.readConfigValue("emailId"));
		test.info("Entered Email Id: "+ConfigReader.readConfigValue("emailId"));
		loginPage.enterPassword(ConfigReader.readConfigValue("password"));
		test.info("Entered Password: "+ConfigReader.readConfigValue("password"));
		
		test.info("Logging in to the application");
		loginPage.clickLogin();		

		test.info("Verifying Page Title");
		String pageTitle = loginPage.getPageTitle();
		System.out.println("Page Title is: " + pageTitle);
		test.info("Page Title is: " + pageTitle);
		
		if(pageTitle.equalsIgnoreCase("My Account"))
			test.pass("Login Successful");
		else
			test.fail("Login Failed");
		
		SoftAssert softAssert = new SoftAssert();
		softAssert.assertEquals(pageTitle, "My Account");
		softAssert.assertAll();
	}

	@Test
	public void invalidLoginTest() {
		test = ExtentReportManager.createTest("InvalidLoginTest");
		LoginPage loginPage = new LoginPage(driver);

		loginPage.enterEmailId(ConfigReader.readConfigValue("emailId"));
		test.info("Entered Email Id: "+ConfigReader.readConfigValue("emailId"));
		loginPage.enterPassword(ConfigReader.readConfigValue("emailId"));
		test.info("Entered Invalid Password: "+ConfigReader.readConfigValue("emailId"));
		
		loginPage.clickLogin();
		
		test.info("Verifying Error Message");
		if(loginPage.checkPresenceOfErrorMessage()==true)
			test.pass("Verified Error Message Successfully. Received Error Message as "+loginPage.getErrorMessage());
		else
			test.fail("Unable to verify Error Message");
		
		Assert.assertEquals(loginPage.checkPresenceOfErrorMessage(), true);
	}

}
