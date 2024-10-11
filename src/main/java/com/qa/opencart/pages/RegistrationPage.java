package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;


//THis is the Registration Assignment Page Class

//SRP:means single responsibility principle
//A specific TestClass is responsible for a certain task only.

//LoginPageTest is responsible only for LoginPage related testcases.
//AccountPageTest is responsible only for AccountPage related testcases.
//RegistrationPageTest is responsible for RegistrationPage related testcases.

public class RegistrationPage {

	//Assignment
	
	//Do the POM start from the LoginPage, do not login,click on the register link to goto the
	//Registration Page
	//Fill up the form, click on continue and verify in the RegistrationTest
	
	private WebDriver driver;
	private ElementUtil eleUtil;

	//Page Objects/Locators
	By firstName=By.id("input-firstname");
	By lastName=By.id("input-lastname");
	By email=By.id("input-email");
	By telephone=By.id("input-telephone");
	By password=By.id("input-password");
	By confirmPassword=By.id("input-confirm");
	By privacyPolicy=By.name("agree");
	By continueBtn=By.xpath("//input[@value='Continue']");
	By accountCreationElement=By.id("content");
	By accountCreationMesg=By.xpath("//div[@id='content']/h1");
	
	//public constructor
	public RegistrationPage(WebDriver driver) {
		this.driver=driver;
		eleUtil=new ElementUtil(driver);
	}
	
	//Page Action/Method
	public boolean doRegistration1(String firstName,String lastName,String email,String telephone,String password) {
		eleUtil.waitForElementVisible(this.firstName, AppConstants.DEFAULT_SHORT_TIME_OUT).sendKeys(firstName);
		eleUtil.doSendKeys(this.lastName,lastName);
		eleUtil.doSendKeys(this.email,email);
		eleUtil.doSendKeys(this.telephone,telephone);
		eleUtil.doSendKeys(this.password,password);
		eleUtil.doSendKeys(this.confirmPassword,password);
		eleUtil.doClick(this.privacyPolicy);
		eleUtil.doClick(this.continueBtn);
		boolean accountRegistrationStatus=eleUtil.waitForURLContains("route=account/success", AppConstants.DEFAULT_SHORT_TIME_OUT);
		if(accountRegistrationStatus) {
			return true;
		}
		return false;
	}
	
	public boolean doRegistration2(String firstName,String lastName,String email,String telephone,String password) {
		eleUtil.waitForElementVisible(this.firstName, AppConstants.DEFAULT_SHORT_TIME_OUT).sendKeys(firstName);;
		eleUtil.doSendKeys(this.lastName,lastName);
		eleUtil.doSendKeys(this.email,email);
		eleUtil.doSendKeys(this.telephone,telephone);
		eleUtil.doSendKeys(this.password,password);
		eleUtil.doSendKeys(this.confirmPassword,password);
		eleUtil.doClick(this.privacyPolicy);
		eleUtil.doClick(this.continueBtn);
		String successMesg=eleUtil.waitForElementVisible(accountCreationMesg,AppConstants.DEFAULT_SHORT_TIME_OUT).getText();
		if(successMesg.equals("Your Account Has Been Created!")) {
			return true;
		}
		return false;
	}
}
