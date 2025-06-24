package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import base.BaseTest;
import pages.LoginPage;
import utils.ConfigReader;

public class LoginTest extends BaseTest {

	@Test
	public void validLoginTest() {

		LoginPage loginPage = new LoginPage(driver);

		loginPage.enterEmailId(ConfigReader.readConfigValue("emailId"));
		loginPage.enterPassword(ConfigReader.readConfigValue("password"));
		loginPage.clickLogin();

		String pageTitle = loginPage.getPageTitle();
		System.out.println("Page Title is: " + pageTitle);

		SoftAssert softAssert = new SoftAssert();
		softAssert.assertEquals(pageTitle, "My Account");
	}

	
	public void invalidLoginTest() {

		LoginPage loginPage = new LoginPage(driver);

		loginPage.enterEmailId(ConfigReader.readConfigValue("emailId"));
		loginPage.enterPassword(ConfigReader.readConfigValue("emailId"));
		loginPage.clickLogin();

		Assert.assertEquals(loginPage.checkPresenceOfErrorMessage(), true);
	}

}
