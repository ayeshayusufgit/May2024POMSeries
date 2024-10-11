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
public class ProductInfoPageTest4 extends BaseTest {

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
	
	//Assignment 2 data provider to be added for this test(2 columns have to be maintained)
	@Test(dataProvider = "getSearchNProductNameData")
	public void productHeaderTest(String searchTerm,String productName) {
		resultPage = accountsPage.doSearch(searchTerm);
		productInfoPage = resultPage.selectProduct(productName);
		
		Assert.assertEquals(productInfoPage.getProductHeader(), productName);
	}
	
	@DataProvider
	public Object[][] getProductInfo(){
		return new Object[][] {
			{"macbook","MacBook Pro"      		 ,"Apple","Product 18","800"   ,"In Stock"     ,"$2,000.00" ,"$2,000.00"},
			{"iMac"   ,"iMac"                    ,"Apple","Product 14",  null  ,"Out Of Stock" ,"$122.00"   ,"$100.00"  },
			{"samsung","Samsung SyncMaster 941BW", null  ,"Product 6" ,  null  ,"2-3 Days"     ,"$242.00"   ,"$200.00"  },
			{"samsung","Samsung Galaxy Tab 10.1" , null  ,"SAM1"      ,"1000"  ,"Pre-Order"    ,"$241.99"   ,"$199.99"  },
		};
	}
	
	
	
//	@DataProvider
//	public Object[][] getProductInfo(){
//		return new Object[][] {
//			{"canon"  ,"Canon EOS 5D" ,"Canon","Product 3" ,"200"   ,"2-3 Days"     ,"$98.00"    ,"$80.00"   }
//		};
//	}

	
	
	
	//Assignment 2: needs to be picked up from Excel
	@Test(dataProvider = "getProductInfo")
	public void productInfoTest(String searchTerm,String productName,String productBrand,String productCode,String productRewardPoints,String productAvailability, String productPrice,String exTaxProductPrice) {
		SoftAssert softAssert=new SoftAssert();
		resultPage = accountsPage.doSearch(searchTerm);
		productInfoPage = resultPage.selectProduct(productName);

		Map<String,String> actProductDataMap=productInfoPage.getProductData();
		
		softAssert.assertEquals(actProductDataMap.get("Brand"),productBrand);
		
		softAssert.assertEquals(actProductDataMap.get("Product Code"),productCode);
		softAssert.assertEquals(actProductDataMap.get("Reward Points"),productRewardPoints);
		softAssert.assertEquals(actProductDataMap.get("Availability"),productAvailability);
		softAssert.assertEquals(actProductDataMap.get("Product Price"),productPrice);
		softAssert.assertEquals(actProductDataMap.get("Ex Tax Price"),exTaxProductPrice);
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
