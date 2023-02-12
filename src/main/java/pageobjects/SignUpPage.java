package pageobjects;

import org.openqa.selenium.interactions.Actions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignUpPage {

	WebDriver driver;

	public SignUpPage(WebDriver driver) {

		this.driver = driver;

		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//input[@type='email']")
	private WebElement emailAddress;

	@FindBy(xpath = "//button[@data-testid='mr-form-signup-btn-start-1']")
	private WebElement continueButtonAfterEmail;

	@FindBy(xpath = "//input[@name='signup[name]']")
	private WebElement nameText;

	@FindBy(xpath = "//button[@data-testid='mr-form-signup-btn-start-2']")
	private WebElement continueButtonAfterName;

	@FindBy(xpath = "//input[@type='password']")
	private WebElement passwordText;

	@FindBy(xpath="//input[@id='signup-terms']")
	private WebElement signUpTerms;

	@FindBy(xpath = "//button[@data-testid='mr-form-signup-btn-start-3']")
	private WebElement continueButtonAfterTerms;

	@FindBy(xpath = "//h1[contains(@class,'signup__title-form')]")
	private WebElement checkYourEmailConfirmationTitle;

	@FindBy(xpath = "//div[@class='signup__subtitle-form']")
	private WebElement confirmationCodeSentMessage;

	

	public void enterEmailAddress(String emailText) {
		emailAddress.sendKeys(emailText);
	}

	public void clickContinueButtonAfterEmail() {
		continueButtonAfterEmail.click();
	}

	public void enterName(String Name) {
		
		nameText.sendKeys(Name);
	}
	
	public WebElement getUserField() {
		return nameText;
	}

	public void clickContinueButtonAfterName() {
		continueButtonAfterName.click();
	}
	
	public WebElement getPasswordElement() {
		return passwordText;
	}

	public void enterPassword(String pwd) {
		passwordText.sendKeys(pwd);
	}

	public void acceptSignUpTerms(Actions actions) {
		actions.moveToElement(signUpTerms).click().build().perform();
	}

	public void clickContinueButtonAfterAcceptingTerms() {
		continueButtonAfterTerms.click();
	}

	public String getConfirmationTitle() {
		return checkYourEmailConfirmationTitle.getText();
	}

	public String getConfirmationCodeSentMessage() {
		return confirmationCodeSentMessage.getText();
	}
}
