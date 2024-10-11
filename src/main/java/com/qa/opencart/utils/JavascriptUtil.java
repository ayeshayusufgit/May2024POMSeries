package com.qa.opencart.utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class JavascriptUtil {
	
	private WebDriver driver;
	private JavascriptExecutor jse;
	
	public JavascriptUtil(WebDriver driver) {
		this.driver=driver;
		jse=(JavascriptExecutor)driver;
	}
	
	public  String getPageTitle() {
		return jse.executeScript("return document.title;").toString();
	}

	public  String getPageURL() {
		return jse.executeScript("return document.URL;").toString();
	}
	
	//This method just displays the Alert on the page with a message if passed to it
	//no return is required from javascript
	public void generateJsAlert(String message) {
		jse.executeScript("alert('"+message+"')");
	}
	
	public String getPageInnerText() {
		return jse.executeScript("return document.documentElement.innerText;").toString();
	}
	
	public void goForwardWithJs() {
		jse.executeScript("history.go(1)");
	}
	
	public void goBackWithJs() {
		jse.executeScript("history.go(1)");
	}
	
	public void pageRefreshWithJs() {
		jse.executeScript("history.go(1)");
	}
	
	public void zoomChromeEdgeSafariFirefox(String zoomPercentage) {
		String zoom = "document.body.style.zoom = '"+zoomPercentage+"%'";
		jse.executeScript(zoom);
	}
	
	public void zoomFirefox(String zoomPercentage) {
		String zoom = "document.body.style.MozTransform = 'scale("+zoomPercentage+")'";
		jse.executeScript(zoom);
	}
	
	public void scrollMiddlePageDown() {
		jse.executeScript("window.scrollTo(0, document.body.scrollHeight/2);");
	}
	
	public void scrollPageDown() {
		jse.executeScript("window.scrollTo(0, document.body.scrollHeight);");
	}
	
	public void scrollPageDown(String height) {
		jse.executeScript("window.scrollTo(0,'"+height+"' );");
	}
	public void scrollPageUp() {
		jse.executeScript("window.scrollTo(document.body.scrollHeight, 0);");
	}
	
	public void scrollIntoView(WebElement element) {
		jse.executeScript("arguments[0].scrollIntoView(true);", element);
	}
	
	public void drawBorder(WebElement element) {
		jse.executeScript("arguments[0].style.border='3px solid red'", element);
	}
	
	public void flash(WebElement element) {
		String bgcolor = element.getCssValue("backgroundColor");//white
		for (int i = 0; i < 100; i++) {
			changeColor("rgb(0,200,0)", element);// Green
			changeColor(bgcolor, element);// While
		}
	}
	
	private void changeColor(String color, WebElement element) {
		jse.executeScript("arguments[0].style.backgroundColor = '" + color + "'", element);//G-->W--G--W---G--W
		try {
			Thread.sleep(20);
		} catch (InterruptedException e) {
		}
	}
}