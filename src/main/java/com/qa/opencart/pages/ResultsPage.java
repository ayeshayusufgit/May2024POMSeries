package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class ResultsPage {
	
	private WebDriver driver;
	private ElementUtil eleUtil;
	
	private By searchHeader=By.cssSelector("div#content h1");
	private By searchResults=By.cssSelector("div.product-thumb");
	private By macbookLink=By.linkText("MacBook Pro");//static By locator
	
	public ResultsPage(WebDriver driver) {
		this.driver=driver;
		eleUtil=new ElementUtil(driver);
	}
	
	public String getSearchHeader() {
		String searchHeaderValue=eleUtil.waitForElementVisible(searchHeader, AppConstants.DEFAULT_SHORT_TIME_OUT).getText();
		return searchHeaderValue;
	}
	
	public int getSearchResultsCount() {
		int searchResultCount= eleUtil.waitForElementsVisible(searchResults, AppConstants.DEFAULT_SHORT_TIME_OUT).size();
		System.out.println("SearchResultCount====>"+searchResultCount);
		return searchResultCount;
	}
	
	public ProductInfoPage selectProduct(String productName) {
		System.out.println("Selecting Product:"+productName);
		eleUtil.doClick(By.linkText(productName));//dynamic By locator:	locators created on the fly.
		return new ProductInfoPage(driver);
	}
}
