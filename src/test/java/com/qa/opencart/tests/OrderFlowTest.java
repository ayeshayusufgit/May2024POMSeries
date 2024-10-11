package com.qa.opencart.tests;

import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;

import io.qameta.allure.Epic;

//@Listeners({ExtentReportListener.class,AnnotationTransformer.class})
@Epic("Epic 102:Open Cart Order Flow Test")
public class OrderFlowTest extends BaseTest {
	
	
	//assignment 2
	//login from the login page,search for macbook,Click on the product,add the quantity(ie 2)
	//click on landing on the shopping cart, click on the checkout 
	//fill the form in the checkout page,click on the checkout button
	//fill all the tabs:Billing Details till the Confirm Order
	//write testcases for this flow
	
	
	
	
	
	@Test
	public void orderFlowTest() {
		accountsPage=loginPage.doLogin("test786@gmail.com","test123");
		resultPage=accountsPage.doSearch("macbook");
		productInfoPage=resultPage.selectProduct("MacBook Air");
		
	}

}
