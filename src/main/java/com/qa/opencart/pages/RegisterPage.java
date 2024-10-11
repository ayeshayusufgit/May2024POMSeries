package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

//This is Naveen's RegisterPage class
public class RegisterPage {
	private WebDriver driver;
	private ElementUtil eleUtil;

	// Page Objects/Locators
	By firstName = By.id("input-firstname");
	By lastName = By.id("input-lastname");
	By email = By.id("input-email");
	By telephone = By.id("input-telephone");
	By password = By.id("input-password");
	By confirmPassword = By.id("input-confirm");
	By subscribeYes = By.xpath("(//label[@class='radio-inline'])[position()=1]/input[@type='radio']");
	By subscribeNo = By.xpath("(//label[@class='radio-inline'])[position()=2]/input[@type='radio']");
	By privacyPolicy = By.name("agree");
	By regContinueBtn = By.xpath("//input[@value='Continue']");
	By accountCreationElement = By.id("content");
	// By accountCreationMesg = By.xpath("//div[@id='content']/h1");
	By successMesgElement = By.cssSelector("div#content h1");
	By successfulRegistrationContinueBtn = By.cssSelector("a.btn.btn-primary");
	By logOutLink = By.linkText("Logout");
	By registerLink = By.linkText("Register");

	// public constructor
	public RegisterPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}

	// Page Action/Method
	public boolean userRegisteration(String firstName, String lastName, String email, String telephone, String password,
			String subscribe) {

		eleUtil.doSendKeys(this.firstName, firstName);
		eleUtil.doSendKeys(this.lastName, lastName);
		eleUtil.doSendKeys(this.email, email);
		eleUtil.doSendKeys(this.telephone, telephone);
		
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		eleUtil.doSendKeys(this.password, password);
		eleUtil.doSendKeys(this.confirmPassword, password);
		if (subscribe.equalsIgnoreCase("yes")) {
			eleUtil.doClick(subscribeYes);
		} else {
			eleUtil.doClick(subscribeNo);
		}
		eleUtil.doClick(privacyPolicy);
		eleUtil.doClick(regContinueBtn);
		String successMessage = eleUtil.waitForElementVisible(successMesgElement, AppConstants.DEFAULT_SHORT_TIME_OUT)
				.getText();
		System.out.println(successMessage);

		if (successMessage.contains(AppConstants.USER_REGISTER_SUCCESS_MESSAGE)) {
			eleUtil.doClick(logOutLink);
			eleUtil.doClick(registerLink);
			return true;
		} else {
			return false;
		}
	}
}
