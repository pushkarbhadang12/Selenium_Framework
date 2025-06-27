package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utils.ConfigReader;

public class LoginPage {
	
	private WebDriver driver;
	
	@FindBy(id="input-email")
	WebElement emailIdTextBox;

	@FindBy(id = "input-password")
	WebElement passwordTextBox;

	@FindBy(xpath = "//input[@type='submit']")
	WebElement loginButton;

	@FindBy(xpath = "//div[contains(text(),'Warning: No match')]")
	WebElement errorMessage;

	public LoginPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public void enterEmailId(String emailId) {
		emailIdTextBox.clear();
		emailIdTextBox.sendKeys(emailId);
	}

	public void enterPassword(String password) {
		passwordTextBox.sendKeys(password);
	}

	public void clickLogin() {
		loginButton.click();
	}

	public String getPageTitle() {
		return driver.getTitle();
	}

	public boolean checkPresenceOfErrorMessage() {
		boolean presenceOfErrorMessage = false;
		try {
			if (errorMessage.isDisplayed() == true) {	
				System.out.println("Error message displayed as: "+ConfigReader.readConfigValue("invalidLoginErrorMsg"));
				presenceOfErrorMessage = true;
			}
			else {
				System.out.println("Not able to find any Error Message.");
				presenceOfErrorMessage = false;				
			}
			
		} catch (Exception e) {
			System.out.println("Unable to check presence of Error Message");
		}
		return presenceOfErrorMessage;
	}
	
	public String getErrorMessage() {		
		if(errorMessage.isDisplayed()==true)
			return errorMessage.getText();
		else
			return null;
	}
}
