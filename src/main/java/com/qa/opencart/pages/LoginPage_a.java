package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class LoginPage_a {
	private WebDriver driver;
	
	
	//When LoginPage class is getting loaded,
	
	//===============================================================================//
	//	@FindBy(How.ID = "naveen")
	//	WebElement fName;
	//	
	//	@FindBy(How.ID = "naveen")
	//	WebElement password;
	//	
	//	@FindBy(How.ID = "naveen")
	//	WebElement ema;
	
	//This is called PageFactory Pattern, the movement the Class is loaded all the 
	//locators are created even though all the locators are not completely used(Page Factory)
	//Internally driver.findBy is used internally, so the server is being hit
	//whether the locator is being used or not.
	//Drawback:
	//1.Performance issue 
	//2.These WEs are already created and again you come back to the LoginPage after sometime
	//These elements become stale(page refresh), logout and login all the elements become stale
	//You will end up getting StaleElementReferenceException
	//In C# this is deprecated,soon will be deprecated in Java too 
	
	//If you login and logout when u come to the loginPage again you will get StaleElementException
	//as the
	
	//Incase of By locator the server is being invoked only when the locator is used
	//using the By locator we will call dynamically the fe or fes as an when its used
	//==================================================================================//
	
	//1.private By locators; page Objects
	private By firstname=By.id("input-email");
	
	//private static By firstname=By.id("input-email");
	//If public static By locators are used then what is the drawback?
	//private final By firstname=By.id("input-email");
	//using static is a wrong practice but final can be used(No one can change it within the class)
	//outside the class the locator cant be accessed
	
	private By pwd=By.id("input-password");
	private By loginBtn=By.xpath("//input[@value='Login']");
	private By forgotPasswordLink=By.linkText("Forgotten Password");
	private By logo=By.cssSelector("img.img-responsive");
	
	//2.public Page Constructors...
	public LoginPage_a(WebDriver driver) {
		this.driver=driver;
	}
	
	//3.public Page Actions/Methods(every page method returns something)  
	
	//Important doc for the POM by the creator
	//https://martinfowler.com/bliki/PageObject.html?ref=backstage.payfit.com
	//https://x.com/shs96c/status/880809924607057920?lang=en
	
	//Internally used methods can be make private
	
	public String getLoginPageTitle() {
		String title=driver.getTitle();
		System.out.println("Login Page Title:"+title);
		return title;
	}
	
	public String getLoginPageURL() {
		String url=driver.getCurrentUrl();
		System.out.println("Login Page Url:"+url);
		return url;
	}
	
	public boolean isForgotPwdLinkExist() {
		return driver.findElement(forgotPasswordLink).isDisplayed();
	}
	
	
	public boolean isLogoExist() {
		return driver.findElement(logo).isDisplayed();
	}
	
	public String doLogin(String username,String password) {
		driver.findElement(firstname).sendKeys(username);
		driver.findElement(pwd).sendKeys(password);
		driver.findElement(loginBtn).click();
		String acctPageTitle=driver.getTitle();
		System.out.println("Acc Page Title:"+acctPageTitle);
		return driver.getTitle();//This returns the next landing page class ie Accounts Page
	}
	
	
	//This is for role based applications
	//No separate page for this
	/*public String doLogin(String username,String password,String role) {
		switch(role) {
		
		case "admin":
				break;
				
		case "customer":
				break;
				
		case "seller":
				break;				
		default:
			break;
		}
	}*/
}