package com.qa.opencart.pages;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class ProductInfoPage {

	private WebDriver driver;
	private ElementUtil eleUtil;
	private Map<String,String> productMap;
	
	//1.private By locators; page Objects
	private By productHeader=By.tagName("h1");
	private By quantity=By.id("input-quantity");
	private By addToCartBtn=By.id("button-cart");
	private By successProductAddedMesg=By.cssSelector("div.alert.alert-success.alert-dismissible");
	private By shoppingCartLink=By.xpath("//div[@class='alert alert-success alert-dismissible']/a[text()='shopping cart']");
	private By productMetaData=By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[1]//li");
	private By productPriceData=By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[2]//li");
	private By productImages=By.cssSelector("ul.thumbnails img");
	
	//2.public Page Constructors...
	public ProductInfoPage(WebDriver driver) {
		this.driver=driver;
		eleUtil=new ElementUtil(driver);
	}
	
	public String getProductHeader() {
		String productHeaderValue=eleUtil.waitForElementVisible(productHeader, AppConstants.DEFAULT_SHORT_TIME_OUT).getText();
		System.out.println("Product Header===>"+productHeaderValue);
		return productHeaderValue;
	}
	
	public boolean addProductToCart(int quantity) {
		eleUtil.waitForElementVisible(this.quantity, AppConstants.DEFAULT_SHORT_TIME_OUT);
		eleUtil.doSendKeys(this.quantity, String.valueOf(quantity));
		eleUtil.doClick(this.addToCartBtn);
		String successMesg=eleUtil.waitForElementVisible(this.successProductAddedMesg, AppConstants.DEFAULT_SHORT_TIME_OUT).getText();
		System.out.println(successMesg);
		
		boolean flag=false;
		if(successMesg.contains("Success: You have added")) {
			flag=true;
		}
		return flag;
	}
	
	public CartPage navigateToCartPage() {
		eleUtil.doClick(shoppingCartLink);
		return new CartPage(driver);
	}
	
	//Product Meta Data
	//	Brand: Apple
	//	Product Code: Product 18
	//	Reward Points: 800
	//	Availability: In Stock
	//These methods are private is its internally used in getProductData()
	private void getProductMetaData() {
		List<WebElement> productMetaList=eleUtil.getElements(productMetaData);
		//productMap=new HashMap<String, String>(); resulting in NPE moving in getProductData() 
		
		for(WebElement productMeta:productMetaList) {
			String productMetaText=productMeta.getText();
			String[] productMetaData=productMetaText.split(":");
			String productMetaKey=productMetaData[0].trim();
			String productMetaValue=productMetaData[1].trim();
			productMap.put(productMetaKey, productMetaValue);	
		}
	}
	
	//Product Price Data
	//	$2,000.00
	//	Ex Tax: $2,000.00 => In this case we are bothered only about the price
	//These methods are private is its internally used in getProductData()
	private void getProductPriceData() {
		List<WebElement> productPriceList=eleUtil.getElements(productPriceData);
		String productPrice=productPriceList.get(0).getText().trim();//	$2,000.00
		String exTaxPrice=productPriceList.get(1).getText().split(":")[1].trim();
		productMap.put("Product Price", productPrice);
		productMap.put("Ex Tax Price", exTaxPrice);
	}
	
	public Map<String,String> getProductData() {
		productMap=new HashMap<String, String>();//does not maintain the order of insertion of the keys
		//The way in which the key=value is added in the HashMap, it is not displayed
		//For that the LinkedHashMap can be used
		//The order of insertion of the key=value is maintained
		
		//2.productMap=new LinkedHashMap<String, String>(); //maintains the insertion order
		//3.productMap=new TreeMap<String, String>(); //maintains the alphabetical order of the keys 
		
		//Interview Question:Which is the map collection to be used?
		
		
		productMap.put("Product Header",getProductHeader());
		getProductMetaData();//Encapsulation is used in here
		getProductPriceData();//Encapsulation is used here
		System.out.println("Product Data:"+productMap);
		return productMap;
	}
	
	
	public int getProductImageCount() {
		int imagesCount=eleUtil.waitForElementsVisible(productImages, AppConstants.DEFAULT_SHORT_TIME_OUT).size();
		System.out.println("Images Count===>"+imagesCount);
		return imagesCount;
	}
}
