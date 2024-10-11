package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

import io.qameta.allure.Step;

public class LoginPage {
	private WebDriver driver;
	private ElementUtil eleUtil;
	
	//1.private By locators; page Objects
	private By firstname=By.id("input-email");
	private By pwd=By.id("input-password");
	private By loginBtn=By.xpath("//input[@value='Login']");
	private By forgotPasswordLink=By.linkText("Forgotten Password");
	private By logo=By.cssSelector("img.img-responsive");
	private By registerLink=By.linkText("Register");
	
	//2.public Page Constructors...
	public LoginPage(WebDriver driver) {
		this.driver=driver;
		eleUtil=new ElementUtil(driver);
	}
	
	@Step("Getting the Login Page title")
	public String getLoginPageTitle() {
		String title=eleUtil.waitForTitleContainsAndReturn(AppConstants.LOGIN_PAGE_TITLE,AppConstants.DEFAULT_SHORT_TIME_OUT );
		System.out.println("Login Page Title is:"+title);
		return title;
	}
	
	@Step("Getting the Login Page URL")
	public String getLoginPageURL() {
		String url=eleUtil.waitForURLContainsAndReturn(AppConstants.LOGIN_PAGE_FRACTION_URL, AppConstants.DEFAULT_SHORT_TIME_OUT);
		System.out.println("Login Page Url:"+url);
		return url;
	}
	
	@Step("Checking if the Forgot Password link exists")
	public boolean isForgotPwdLinkExist() {
		return eleUtil.isElementDisplayed(forgotPasswordLink);
	}
	
	@Step("Checking if the Open Cart Logo exists on the Login Page")
	public boolean isLogoExist() {
		return eleUtil.isElementDisplayed(logo);
	}
		
	@Step("Login with username:{0} and password:{1}")
	public AccountsPage doLogin(String username,String password) {
		System.out.println("credentials are:"+username+":"+password);
		eleUtil.waitForElementVisible(firstname, AppConstants.DEFAULT_MEDIUM_TIME_OUT).sendKeys(username);
		eleUtil.doSendKeys(pwd,password );
		eleUtil.doClick(loginBtn);
		return new AccountsPage(driver);
	}
	
	@Step("Navigating to the Registration Page from the Login Page")
	public RegistrationPage navigateToRegistrationPage() {
		eleUtil.doClick(registerLink, AppConstants.DEFAULT_SHORT_TIME_OUT);
		eleUtil.waitForURLContains("route=account/register",AppConstants.DEFAULT_MEDIUM_TIME_OUT);
		return new RegistrationPage(driver);
	}
	
	@Step("Navigating to the Registration Page from the Login Page")
	public RegisterPage navigateToRegisterPage() {
		eleUtil.doClick(registerLink, AppConstants.DEFAULT_SHORT_TIME_OUT);
		eleUtil.waitForURLContains("route=account/register",AppConstants.DEFAULT_MEDIUM_TIME_OUT);
		return new RegisterPage(driver);
	}
}