package pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.FindBy;

public class HomePage {

	WebDriver driver;

	public HomePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);

	}

	@FindBy(xpath = "(//div[@tabindex='-1'][contains(.,'Sign up free')])[1]")
	private WebElement signUpButton;

	public void clickOnSignUpButton() {
		signUpButton.click();

	}

}
