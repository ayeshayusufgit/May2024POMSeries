package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.safari.SafariDriver;

import com.qa.opencart.errors.AppError;
import com.qa.opencart.exceptions.BrowserException;
import com.qa.opencart.exceptions.FrameworkException;

import io.qameta.allure.Step;

//This class is responsible for the creation of the Webdriver
public class DriverFactory {

	WebDriver driver;
	Properties prop;
	public static String isHighlight;
	public static ThreadLocal<WebDriver> tlDriver=new ThreadLocal<WebDriver>();

	/**
	 * This method is used to initialize the browser on the basis of the given browserName
	 * @param browserName
	 * @return driver
	 */
	@Step("Initializing the driver with properties:{0}")
	public WebDriver initDriver(Properties prop) {
		//The advantage of passing the "prop" is that the initDriver decides the keys it needs
		//to be used, the browser and the url being used are thus being fetched from config.properties
		//ow so many , separated keys would have to be supplied to the initDriver method
		//initDriver(browserName,url......)
		
		String browserName=prop.getProperty("browser");
		System.out.println("Browser name is:" + browserName);
		
		isHighlight = prop.getProperty("highlight");
		System.out.println("Highlight is:" + isHighlight);
		
		OptionsManager optionsManager=new OptionsManager(prop);
		

		switch (browserName.toLowerCase().trim()) {
		case "chrome":
			//driver = new ChromeDriver(optionsManager.getChromeOptions());
			tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
			break;
		case "firefox":
			//driver = new FirefoxDriver(optionsManager.getFireOptions());
			tlDriver.set(new FirefoxDriver(optionsManager.getFireOptions()));
			break;
		case "edge":
			//driver = new EdgeDriver(optionsManager.getEdgeOptions());
			tlDriver.set(new EdgeDriver(optionsManager.getEdgeOptions()));
			break;
		case "safari":
			//driver = new SafariDriver();
			tlDriver.set(new SafariDriver());
			break;
		default:
			System.out.println(AppError.INVALID_BROWSER_MESSAGE+browserName);
			throw new BrowserException(AppError.INVALID_BROWSER_MESSAGE+browserName);
		}
		//driver.manage().window().maximize();
		//driver.manage().deleteAllCookies();
		//driver.get(prop.getProperty("url"));
		//return driver;
		getDriver().manage().window().maximize();
		getDriver().manage().deleteAllCookies();
		getDriver().get(prop.getProperty("url"));
		return getDriver();
	}
	
	/*
	 * this method is returning the driver with threadlocal
	 */
	public static WebDriver getDriver() {
		return tlDriver.get();
		//returns the local copy of the driver
	}
	
	/**
	 * 
	 * @return
	 */
	public Properties initProp() {
		
		prop=new Properties();
		FileInputStream fis=null;
		
		String envName=System.getProperty("env");
		System.out.println("Running tests on env:"+envName);
		try {
			if(envName==null) {
			//if no "envName" is passed then we assume that the tests have to executed on the QA environment
			System.out.println("if env is null....hence running the tests on the QA environment:"+envName);
			fis=new FileInputStream("./src/test/resources/config/qa.config.properties");
			//This makes connection with the file(config.properties) which is being read
			}else {
				switch(envName.toLowerCase().trim()) {
					case "qa": fis=new FileInputStream("./src/test/resources/config/qa.config.properties");
								break;
					case "dev": fis=new FileInputStream("./src/test/resources/config/dev.config.properties");		
								break;
					case "stage": fis=new FileInputStream("./src/test/resources/config/stage.config.properties");		
								break;
					case "uat": fis=new FileInputStream("./src/test/resources/config/uat.config.properties");		
								break;
					case "prod": fis=new FileInputStream("./src/test/resources/config/config.properties");		
								break;	
					default:System.out.println("Please pass the right environment name:"+envName);
						throw new FrameworkException("INVALID ENVIRONMENT NAME...");
				}
			}	
			prop.load(fis);//54
			}catch(FileNotFoundException ex) {
				ex.printStackTrace();
			}catch(IOException ex) {
				ex.printStackTrace();
			}
			return prop;
	}
	public static String getScreenshot(String methodName) {
		//the getScreenShot() is static as it can be used directly
		
		//getDriver() will get the driver and typecast it into the TakesScreenshot interface
		//In Selenium "TakesScreenshot" interface is responsible to take screenshots
		//The driver has to be converted to "TakesScreenshot"(just like JavascriptExecutor)
		
		File scrFilePath=((TakesScreenshot)getDriver()).getScreenshotAs(OutputType.FILE);
		//This ss will be created in the temp directory
		
		//System.out.println();
		System.out.println(System.getProperty("user.dir"));
		String path=System.getProperty("user.dir")+"/screenshots/"+methodName+"_"+System.currentTimeMillis()+".png";
		//user.dir is an environment variable
		//user.dir will take the path of the current directory
		//a unique ss path is generated ow the ss will get overridden
		//.png is better as it'll work for all platforms
		
		File destFilePath=new File(path);
		try {
			FileHandler.copy(scrFilePath, destFilePath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return path;
		//the path is returned as the ExtentReports use the ss path
	}
	
	//https://mkyong.com/maven/how-to-install-maven-in-windows/
}
