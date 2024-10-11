package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

//This is the Naveen's RegisterPageTest class
public class RegisterPageTest extends BaseTest {
	
	@BeforeMethod
	public void registerPageSetUp() {
		regPage=loginPage.navigateToRegisterPage();
	}
	
	
	public String getRandomEmail() {
		return "uiautomation"+System.currentTimeMillis()+"@gmail.com";
	}
	
	@DataProvider
	public Object[][] getUserRegistrationData(){
		return new Object[][] {
			{"firstname1","lastname1","999999999","test123","yes"},
			{"firstname2","lastname2","999999999","test123","yes"},
			{"firstname3","lastname3","999999999","test123","yes"},
			{"firstname4","lastname4","999999999","test123","yes"},
			{"firstname5","lastname5","999999999","test123","yes"}
		};
	}
	
	
	//assignment 1
	//excel with 5 columns and 5 rows for user registration
	@Test(dataProvider = "getUserRegistrationData")
	public void userRegisterTest(String firstName,String lastName,String mobile,String password,String subscribe) {
		 Assert.assertTrue(regPage.userRegisteration(firstName,lastName,getRandomEmail(), mobile,password,subscribe));
	}

	//Since the search is a functionality common across all the pages so there can be a CommonPage class which can have 
	//the search functionality
	
}
