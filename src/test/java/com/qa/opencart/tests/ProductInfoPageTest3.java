package com.qa.opencart.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.qa.opencart.base.BaseTest;

public class ProductInfoPageTest3 extends BaseTest {

	@BeforeClass
	public void productInfoSetup() {
		accountsPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}

	
	
//	@DataProvider
//	public Object[][] getProductInfo(){
//		return new Object[][] {
//			{"macbook","MacBook Pro"      		 ,"Apple","Product 18","800"   ,"In Stock"     ,"$2,000.00" ,"$2,000.00"},
//			{"iMac"   ,"iMac"                    ,"Apple","Product 14",  null  ,"Out Of Stock" ,"$122.00"   ,"$100.00"  },
//			{"samsung","Samsung SyncMaster 941BW", null  ,"Product 6" ,  null  ,"2-3 Days"     ,"$242.00"   ,"$200.00"  },
//			{"samsung","Samsung Galaxy Tab 10.1" , null  ,"SAM1"      ,"1000"  ,"Pre-Order"    ,"$241.99"   ,"$199.99"  },
//			{"canon"  ,"Canon EOS 5D"            ,"Canon","Product 3" ,"200"   ,"2-3 Days"     ,"$98.00"    ,"$80.00"   }
//		};
//	}
	
	
	
	//Will look into this in some time
	@DataProvider
	public Object[][] getProductInfo(){
		return new Object[][] {
			{"canon"  ,"Canon EOS 5D" ,"Canon","Product 3" ,"200"   ,"2-3 Days"     ,"$98.00"    ,"$80.00"   }
		};
	}
	
	//Assignment 1 data provider to be added for this test(8 columns have to be maintained)
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
}
