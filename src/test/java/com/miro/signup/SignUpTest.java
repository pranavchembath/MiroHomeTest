package com.miro.signup;

import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.Base;
import pageobjects.HomePage;
import pageobjects.SignUpPage;
import util.DataUtil;
import util.XLXSReader;

public class SignUpTest extends Base {

	WebDriver driver;
	Object[][] data = null;
	String CheckMessage = "Check your email";
	XLXSReader excelReader;
	Actions actions;
	HomePage homePage;
	SignUpPage signupPage;

	@DataProvider
	public Object[][] dataSupplier() {

		try {
			excelReader = new XLXSReader("src/test/resources/data.xlsx");
			data = DataUtil.getTestData(excelReader, "SignUp", "Data");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return data;

	}

	@BeforeTest
	public void setUp() {

	}

	@AfterTest
	public void tearDown() {
		driver.quit();
	}

	@Test(dataProvider = "dataSupplier")
	public void validateSuccessfulSignUpByCustomer(HashMap<String, String> hMap) throws InterruptedException {

		if (!DataUtil.isRunnable(excelReader, "SignUp", "Testcases") || hMap.get("Runmode").equals("N")) {
			throw new SkipException("Run mode is set to N, hence not executed");
		}
		driver = openBrowserAndApplicationBaseURI(hMap.get("Browser"));
		signupPage = new SignUpPage(driver);
		homePage = new HomePage(driver);
		homePage.clickOnSignUpButton();

		signupPage.enterEmailAddress(hMap.get("Email"));
		signupPage.clickContinueButtonAfterEmail();

		waitForElementToLoad(driver, signupPage.getUserField());
		signupPage.enterName(hMap.get("User"));
		signupPage.clickContinueButtonAfterName();

		waitForElementToLoad(driver, signupPage.getPasswordElement());
		signupPage.enterPassword(hMap.get("Password"));

		actions = new Actions(driver);
		signupPage.acceptSignUpTerms(actions);

		signupPage.clickContinueButtonAfterAcceptingTerms();

		// Validating whether user gets Check your email message
		Assert.assertTrue(signupPage.getConfirmationTitle().equalsIgnoreCase(CheckMessage),
				"Failed to display confirmation message");

		// Validating whether confirmation code message includes the user email address
		Assert.assertTrue(signupPage.getConfirmationCodeSentMessage().contains(hMap.get("Email")));
	}

}
