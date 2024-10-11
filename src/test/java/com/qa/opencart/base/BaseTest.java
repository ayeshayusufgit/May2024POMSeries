package com.qa.opencart.base;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.asserts.SoftAssert;

import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.pages.AccountsPage;
import com.qa.opencart.pages.LoginPage;
import com.qa.opencart.pages.ProductInfoPage;
import com.qa.opencart.pages.RegisterPage;
import com.qa.opencart.pages.RegistrationPage;
import com.qa.opencart.pages.ResultsPage;

import io.qameta.allure.Step;

public class BaseTest {
	WebDriver driver;
	DriverFactory df;

	protected Properties prop;//this can be used in the child test classes
	protected LoginPage loginPage;
	protected AccountsPage accountsPage;
	protected RegistrationPage registrationPage;//Assignment Page class
	protected RegisterPage regPage;//Naveen's Page class
	protected ResultsPage resultPage;
	protected ProductInfoPage productInfoPage;

	//protected SoftAssert softAssert;
	//All the methods of the SoftAssert are non static,
	//Thus the methods of the SoftAssert can be called by the reference variable

	//All the methods of the Hard Assert are static thats why called by the class name

	@Step("Setup with browser:{0} and init the properties file")
	@Parameters({"browser"})
	@BeforeTest 
	public void setUp(@Optional("chrome") String browserName) {
		//@Optional : the default value of the browser is chrome
		//if run from the @Test method then the BaseTest won't be able to get the value of browser from the testNG.xml
		//So the @Optional parameter will give a default value of the browser ie chrome
		//Thus @Test can be run directly from the Test Class
		
		df=new DriverFactory();
		prop=df.initProp();

		//check if "browser" parameter is there in the testNG.xml, if the browser parameter is
		//there do the below ow pick the  default "browser" from the config.properties
			
			if(browserName!=null) {
				prop.setProperty("browser", browserName);
				//Dynamically the key "browser" is updated at runtime if there is a "browser" parameter being passed from testNG.xml
			}

		driver=df.initDriver(prop);//call by reference
		//The whole prop ref is passed and whatever is being used can be fetched within initDriver method

		loginPage=new LoginPage(driver);
		//softAssert=new SoftAssert();
	}

	@Step("Close the browser")
	@AfterTest
	public void tearDown() {
		driver.quit();
	}
}
