package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.utils.ElementUtil;

public class CartPage {

	private WebDriver driver;
	private ElementUtil eleUtil;
	
	//1.private By locators; page Objects
	private By firstname=By.id("input-email");

	public CartPage(WebDriver driver) {
		this.driver=driver;
		eleUtil=new ElementUtil(driver);
	}
}
