package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

//@Listeners({ExtentReportListener.class,AnnotationTransformer.class})
@Epic("Epic 101: Open Cart Automation Testcases")
@Feature("Feature 105: Login")
@Story("US 120: All the features related to Login")
@Owner("Naveen Automation Labs")
@Link(name="LoginPage",url="https://naveenautomationlabs.com/opencart/index.php?route=account/login")
public class LoginPageTest extends BaseTest {
	
	//Here the AAA pattern is followed
	//There is no depends or priority
	//The functionality is not mixed
	
	
	@Severity(SeverityLevel.MINOR)
	@Description("Login Page Title Test")
	@Feature("Feature 400: Login Title Test")
	@Test
	public void loginPageTitleTest() {
		String actualTitle=loginPage.getLoginPageTitle();
		Assert.assertEquals(actualTitle, AppConstants.LOGIN_PAGE_TITLE);
	}
	
	@Severity(SeverityLevel.NORMAL)
	@Description("Login Page URL Test")
	@Feature("Feature 401:Login URL Test")
	@Test
	public void loginPageURLTest() {
		String actualURL=loginPage.getLoginPageURL();
		Assert.assertTrue(actualURL.contains(AppConstants.LOGIN_PAGE_FRACTION_URL));
	}
	
	@Severity(SeverityLevel.CRITICAL)
	@Description("The Forgot Password link should exist in the Login Page")
	@Issue("Bug-123")
	@Test
	public void forgotPasswordLinkExistsTest() {
		Assert.assertTrue(loginPage.isForgotPwdLinkExist());
	}
	
	
	@Severity(SeverityLevel.CRITICAL)
	@Description("Logo Test")
	@Test
	public void logoExistsTest() {
		Assert.assertTrue(loginPage.isLogoExist());
	}

	
	@Severity(SeverityLevel.MINOR)
	@Description("User is able to login")
	@Test(priority = Integer.MAX_VALUE)
	public void loginTest() {
		accountsPage=loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
		Assert.assertEquals(accountsPage.getAccountsPageTitle(), AppConstants.ACCOUNTS_PAGE_TITLE);
	}
	
	
	@Test(enabled=false)
	//This is a feature of TestNG, if enabled=false this test wont participate in the execution
	//By default enabled=true
	public void naveenTest() {
		System.out.println("test");
	}
}
