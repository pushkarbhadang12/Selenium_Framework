package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
	
	private WebDriver driver;
	
	@FindBy(id="input-email")	
	WebElement emailIdTextBox;
	
	@FindBy(id="input-password")
	WebElement passwordTextBox;	
	
	@FindBy(xpath="//input[@type='submit']")
	WebElement loginButton;	
	
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
}
