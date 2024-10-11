package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ExcelUtil;

import io.qameta.allure.Epic;

//This is the Naveen's RegisterPageTest class wrt to FW video 6
//@Listeners({ExtentReportListener.class,AnnotationTransformer.class})
@Epic("Epic 104:Open Cart Register Page Test")
public class RegisterPageTest2 extends BaseTest {
	
	@BeforeMethod
	public void registerPageSetUp() {
		regPage=loginPage.navigateToRegisterPage();
	}
	
	public String getRandomEmail() {
		return "uiautomation"+System.currentTimeMillis()+"@gmail.com";
	}
	
	//getUserRegistrationData() will be getting the data from the opencarttestdata.xlsx tab register
	//by using the getData() of the ExcelUtil internally using the POI Apis
	//The getUserRegistrationData() is also returning Object[][] data which is supplied to the userRegisterTest()
	@DataProvider
	public Object[][] getUserRegistrationData(){
		return ExcelUtil.geTesttData(AppConstants.REGISTER_SHEET_NAME);
	}
	
	//The userRegisterTest is supplied with the Object[][] data
	@Test(dataProvider = "getUserRegistrationData")
	public void userRegisterTest(String firstName,String lastName,String mobile,String password,String subscribe) {
		 Assert.assertTrue(regPage.userRegisteration(firstName,lastName,getRandomEmail(), mobile,password,subscribe));
	}
	
	//If this functionality is working for 3 users then it will work for 100 users aswell
	//Ideally its recommended to create less no of users as these get dumped in the db
	//But still better to add the testdata in the DataProvider itself for smaller data
	//For large data like 10 columns etc excels can be used
	
	//The constraints just ot read data
	//Should be ms excel
	//should be the licensed
	//should be the latest version
	//we need to be dependent on the apache poi api(if the apache poi is gone then code wont work)
	//When the excel file is transferred from 1 location to another the excel file gets corrupted
	//If the data is such that it is dynamic then better to generate it dynamically
	
}
