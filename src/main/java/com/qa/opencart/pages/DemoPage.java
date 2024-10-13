package com.qa.opencart.pages;

import org.openqa.selenium.By;

public class DemoPage {

	By loc=By.cssSelector(".demo");
	
	public int getPage() {
		System.out.println("Click on Demo");
		return 0;
	}
}
