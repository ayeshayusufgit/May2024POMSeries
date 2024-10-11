package com.qa.opencart.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.qa.opencart.base.BaseTest;

public class ProductInfoPageTest extends BaseTest {

	@BeforeClass
	public void productInfoSetup() {
		accountsPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
		//resultPage = accountsPage.doSearch("macbook"); wont be in the @BeforeClass as then DataProvider can't be used
		//productInfoPage = resultPage.selectProduct("MacBook Pro"); wont be in the @BeforeClass as then DataProvider can't be used
		//the data also 
	}

	

	//Assignment 2 data provider to be added for this test(2 columns have to be maintained)
	@Test
	public void productHeaderTest() {
		resultPage = accountsPage.doSearch("macbook");
		productInfoPage = resultPage.selectProduct("MacBook Pro");
		
		Assert.assertEquals(productInfoPage.getProductHeader(), "MacBook Pro");
	}
	
	//Assignment 1 data provider to be added for this test(8 columns have to be maintained)
	@Test
	public void productInfoTest() {
		SoftAssert softAssert=new SoftAssert();
		resultPage = accountsPage.doSearch("macbook");
		productInfoPage = resultPage.selectProduct("MacBook Pro");

		Map<String,String> actProductDataMap=productInfoPage.getProductData();
		
		//The Asserts for the keys:Brand,Product Code etc etc.
		//The Hard Asserts have to be replaced by the Soft Asserts
		
		//Assert.assertEquals(actProductDataMap.get("Brand"),"Apple111");
		
		//if any assertions fail the test fails, these are Hard Assertions
		//The other assertions wont get executed, this is the problem with Hard Assertions
		//So in that case we need to use Soft Assertions
		//The SoftAssert comes from TestNG
		
		softAssert.assertEquals(actProductDataMap.get("Brand"),"Apple111");//This fails
		//If this fails then the testcase wont fail but it will continue with the below assertions
	
		softAssert.assertEquals(actProductDataMap.get("Product Code"),"Product 18");
		softAssert.assertEquals(actProductDataMap.get("Reward Points"),"801");//This fails
		softAssert.assertEquals(actProductDataMap.get("Availability"),"In Stock");
		softAssert.assertEquals(actProductDataMap.get("Product Price"),"$2,000.00");
		softAssert.assertEquals(actProductDataMap.get("Ex Tax Price"),"$2,000.00");
		softAssert.assertAll();//=> this tells out of 6 assertions, 1 got failed 
		//After all the soft asserts, assertAll() is mandatory(this line will tell us how many assertions got failed)
		//After all the assertions, SA will tell which one got failed
		
		//Since this is a single test/functionality ie to test the product information with multiple checks, 
		//thus all the Soft Asserts can be written in the productInfoTest and doesn't have to be written in different tests
		
		//When to use Soft Asserts?
		
		//This is still a AAA pattern an there is no violation of the pattern
	}
	@DataProvider
	public Object[][] getProductImagesCountData(){
		return new Object[][] {
			{"macbook","MacBook Pro",4},
			{"iMac","iMac",3},
			{"samsung","Samsung SyncMaster 941BW",1},
			{"samsung","Samsung Galaxy Tab 10.1",7},
			{"canon","Canon EOS 5D",3}
		};
	}
	
	@Test(dataProvider = "getProductImagesCountData")
	public void productImagesCountTest(String searchTerm,String productName,int expectedImageCount) {
		//The images are always static
		//The Seller gives the images which needs to be uploaded from the admin into the 
		//system and displayed in the E-commerce Application

// 		Having hardcoded data 		
//		resultPage = accountsPage.doSearch("macbook");
//		productInfoPage = resultPage.selectProduct("MacBook Pro");
//		Assert.assertEquals(productInfoPage.getProductImageCount(),4);
		
		resultPage = accountsPage.doSearch(searchTerm);
		productInfoPage = resultPage.selectProduct(productName);
		Assert.assertEquals(productInfoPage.getProductImageCount(),expectedImageCount);
	}
}
