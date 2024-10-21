package com.qa.opencart.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.qa.opencart.base.BaseTest;

import io.qameta.allure.Epic;

//@Listeners({ExtentReportListener.class,AnnotationTransformer.class})
@Epic("Epic 103:Open Cart Product Info Page Test")
public class ProductInfoPageTest5 extends BaseTest {

	@BeforeClass
	public void productInfoSetup() {
		accountsPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}

	@DataProvider
	public Object[][] getSearchNProductNameData(){
		return new Object[][] {
			{"macbook","MacBook Pro"},
			{"iMac","iMac"},
			{"samsung","Samsung SyncMaster 941BW"},
			{"samsung","Samsung Galaxy Tab 10.1"},
			{"canon","Canon EOS 5D"}
		};
	}

	@Test(dataProvider = "getSearchNProductNameData")
	public void productHeaderTest(String searchTerm,String productName) {
		resultPage = accountsPage.doSearch(searchTerm);
		productInfoPage = resultPage.selectProduct(productName);
		
		Assert.assertEquals(productInfoPage.getProductHeader(), productName);
	}
	
	//Removing the dataProvider
	@Test//(dataProvider = "getProductInfo")
	public void productInfoTest() {
		SoftAssert softAssert=new SoftAssert();
		resultPage = accountsPage.doSearch("macbook");
		productInfoPage = resultPage.selectProduct("MacBook Pro");

		Map<String,String> actProductDataMap=productInfoPage.getProductData();
		
		softAssert.assertEquals(actProductDataMap.get("Brand"),"Apple");
		
		softAssert.assertEquals(actProductDataMap.get("Product Code"),"Product 18");
		softAssert.assertEquals(actProductDataMap.get("Reward Points"),"800");
		softAssert.assertEquals(actProductDataMap.get("Availability"),"In Stock");
		softAssert.assertEquals(actProductDataMap.get("Product Price"),"$2,000.00");
		softAssert.assertEquals(actProductDataMap.get("Ex Tax Price"),"$2,000.00");
		softAssert.assertAll();
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
		resultPage = accountsPage.doSearch(searchTerm);
		productInfoPage = resultPage.selectProduct(productName);
		Assert.assertEquals(productInfoPage.getProductImageCount(),expectedImageCount);
	}
}
