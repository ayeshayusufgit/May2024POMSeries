package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

//This is the RegistrationPageTest Assignment class
public class RegistrationPageTest extends BaseTest {
	
	//Here the AAA pattern is followed
	//There is no depends or priority
	//The functionality is not mixed

	@BeforeClass
	public void registrationPageSetUp() {
		registrationPage=loginPage.navigateToRegistrationPage();
	}
	
	
	//@Test
	public void testRegisteration1() {
		 boolean actualRegistrationStatus=registrationPage.doRegistration1("First","Last","may2024@open5.com", "9999999999","Selenium@123");
		Assert.assertTrue(actualRegistrationStatus);
	}
	
	//@Test
	public void testRegisteration2() {
		 boolean actualRegistrationStatus=registrationPage.doRegistration2("First","Last","may2024@open6.com", "9999999999","Selenium@123");
		Assert.assertTrue(actualRegistrationStatus);
	}
	
	
	
}
