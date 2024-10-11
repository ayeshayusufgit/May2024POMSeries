package com.qa.opencart.utils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.opencart.exceptions.FrameworkException;
import com.qa.opencart.factory.DriverFactory;

import io.qameta.allure.Step;

//Every class wud have its own WebDriver reference
//Only 1 session id will be used 
public class ElementUtil {
	
	//parallel execution cannot be done with static 
	private WebDriver driver;
	private Actions act;
	private JavascriptUtil jsUtil;
	
	//This class is not required to create the driver
	//When an object of this class will be created the constructor will be called
	//the driver that is passed from the constructor will be assigned to the local driver variable within the class ElementUtil
	public ElementUtil(WebDriver driver) {
		this.driver=driver;
		this.act=new Actions(driver);
		jsUtil=new JavascriptUtil(driver);
	}
	
	private void checkElementHighlight(WebElement element) {
		if(Boolean.parseBoolean(DriverFactory.isHighlight)) {
			jsUtil.flash(element);
		}
	}
	
	public WebElement getElement(By locator) {
	//the local driver will be used here(which is assigned in the constructor)
		WebElement element=driver.findElement(locator);
		checkElementHighlight(element);
		return element;
	}
	
	@Step("Clicking on element using locator:{0}")
	public void doClick(By locator) {
		getElement(locator).click();
	}
	
	@Step("Clicking on element using locator:{0} with timeout:{1}")
	public void doClick(By locator,int timeOut) {
		waitForElementVisible(locator, timeOut).click();
	}
	
	//This method is overloaded, depending on the parameters passed the appropriate method will be invoked
	@Step("Entering the value:{1} into locator:{0}")
	public void doSendKeys(By locator,String value) {
		getElement(locator).sendKeys(value);
	}
	
	@Step("Entering the value:{1} into WebElement:{0}")
	public void doSendKeys(WebElement element,String value) {
		element.clear();
		element.sendKeys(value);
	}
	
	@Step("Entering the value:{1} into locator:{0} with timeout {2}")
	public void doSendKeys(By locator,String value,int timeOut) {
		waitForElementVisible(locator, timeOut).sendKeys(value);
	}
	
	@Step("Entering the value:{1} into locator:{0}")
	public void doSendKeys(By locator,CharSequence...value) {
		//A null check is not required as internally its already done
		getElement(locator).sendKeys(value);
	}
	
	public boolean isElementDisplayed(By locator) {
		try {
			return getElement(locator).isDisplayed();
		} catch (NoSuchElementException ex) {
			System.out.println("Element is not displayed:"+locator);
			return false;
		}
	}
	
	public String doGetElementText(By locator) {
		String elementText=getElement(locator).getText();
		if(elementText!=null) {
			return elementText;
		}else {
			System.out.println("Element text is null:"+elementText);
			return null;	
		}
	}
	
	public String doElementGetAttribute(By locator,String attributeName) {
		return getElement(locator).getAttribute(attributeName);
	}
	
	public int getElementsCount(By locator) {
		return getElements(locator).size();
	}
	
	public List<WebElement> getElements(By locator){
		return driver.findElements(locator);
	}
	
	public List<String> getElementsTextList(By locator){
		List<WebElement> elementsList=getElements(locator);
		List<String> elementsTextList=new ArrayList<String>();
		for(WebElement element:elementsList) {
			String elementText=element.getText();
			if(elementText.length()!=0) {
			elementsTextList.add(elementText);
			}
		}
		return elementsTextList;
	}
	
	public void printElementsList(By locator) {
		List<String> elementsTextList=getElementsTextList(locator);
		for(String elementText:elementsTextList) {
			System.out.print(elementText+" ");
		}
		System.out.println();
	}
	
	public void doSearch(By searchField,By suggestions,String searchTerm,String suggestionMatch ) {
		doSendKeys(searchField, searchTerm);
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<WebElement>  suggestionElementList=getElements(suggestions);
		int totalSuggestionsCount=suggestionElementList.size();
		
		System.out.println("Total number of suggestions:"+totalSuggestionsCount);
		
		if(totalSuggestionsCount==0) {
			System.out.println("There are no suggestions");
			throw new FrameworkException("No Suggestions Found");
		}
		
		for(WebElement element:suggestionElementList) {
			String searchText=element.getText();
			
			if(searchText.contains(suggestionMatch)) {
				element.click();
				break;
			}
		}
		
	}

	//These methods are overloaded
	
	//This method is written for checking the presence of an element once
	public  boolean isElementPresent(By locator) {
		if (getElementsCount(locator) == 1) {
			return true;
		}
		return false;
	}
	
	//This method is written for checking the presence of an element "n" times 
	public  boolean isElementPresent(By locator,int expectedElementCount) {
		if (getElementsCount(locator) == expectedElementCount) {
			return true;
		}
		return false;
	}
	
	//This method is to check if an element is not present on the page
	public  boolean isElementNotPresent(By locator) {
		if (getElementsCount(locator) == 0) {
			return true;
		}
		return false;
	}
	
	//This method is used to check if an element is found 1 more times in the page
	public  boolean isElementPresentMultipleTimes(By locator) {
		if (getElementsCount(locator) >= 1) { //?
			return true;
		}
		return false;
	}
	
	//****************Select Dropdown Utils ******************//
	private Select getSelect(By locator) {
		Select select=new Select(getElement(locator));
		return select;
	}
	
	public int getDropdownOptionsCount(By locator) {
		return getSelect(locator).getOptions().size();
	}

	public void selectDropdownValueByValue(By locator, String value) {
		getSelect(locator).selectByValue(value);
	}

	public void selectDropdownValueByVisibleText(By locator, String visibleText) {
		getSelect(locator).selectByVisibleText(visibleText);
	}

	public void selectDropdownValueByIndex(By locator, int index) {
		getSelect(locator).selectByIndex(index);
	}
	
	public List<String> getDropdownOptionsTextList(By locator) {
		WebElement dropdownElement = getElement(locator);
		List<String> dropdownOptionsTextList = new ArrayList<String>();

		Select select = new Select(dropdownElement);

		List<WebElement> optionsList = select.getOptions();

		System.out.println("Total Number of Options:"+optionsList.size());

		for (WebElement element : optionsList) {
			String text = element.getText();
			dropdownOptionsTextList.add(text);
		}
		
		return dropdownOptionsTextList;
	}
	
	/*
	 * selects the dropdown value using the Select class
	 */
	public void selectDropdownValueUsingSelectClass(By locator,String value) {
		Select select=new Select(getElement(locator));
		List<WebElement> dropdownOptionsList=select.getOptions();
		selectDropdown(dropdownOptionsList,value);
	}
	
	//Coding should be done keeping the following in mind:
	//optimized code
	//reusable
	//no hardcoded values
	
	
	
	/*
	 * selects the dropdown value without using the Select class
	 */
	public void selectDropdownValue(By locator,String value) {
		List<WebElement> dropdownOptionsList=getElements(locator);
		//System.out.println(ddOptionsList.size());
		
		selectDropdown(dropdownOptionsList,value);
	}
	
	private void selectDropdown(List<WebElement> dropdownOptionsList,String value) {
		System.out.println("Total number of options:"+dropdownOptionsList.size());
		for(WebElement option:dropdownOptionsList) {
			String text=option.getText();
			if(text.equals(value)){
				option.click();
				break;
			}
		}
	}
	
	//**************************Actions methods***********************
	
	public  void doActionsClick(By locator) {
		act.click(getElement(locator)).perform();
	}
	
	public  void doActionsSendKeys(By locator,String value) {
		act.sendKeys(getElement(locator),value).perform();
	}
	
	public void doActionsSendkeysWithPause(By locator,String value,long pauseTime) {	
		char chArray[]=value.toCharArray();
		
		for(char ch:chArray ) {
			act.sendKeys(getElement(locator), String.valueOf(ch)).pause(pauseTime).perform();		
		}
	}
	
	
	/*
	 * This method is two level of parent and child menu
	 * @param parentMenu
	 * @param childMenu
	 * @throws InterruptedException
	 */
	public  void parentChildMenu(By parentMenu,By childMenu ) throws InterruptedException  {
		Actions act=new Actions(driver);
		act.moveToElement(getElement(parentMenu)).perform();
		Thread.sleep(1500);
		//getElement(childMenu).click();
		doClick(childMenu);
	}
	
	public void parentChildMenu(String parentMenu,String childMenu ) throws InterruptedException {
		Actions act=new Actions(driver);
		By parent=By.xpath("//*[text()='"+parentMenu+"']");
		act.moveToElement(getElement(parent)).perform();
		Thread.sleep(1500);
		By child=By.xpath("//*[text()='"+childMenu+"']");
		doClick(child);
	}
	
	/*
	 * This method is used for handling four levels of parent and child menus on the basis of By locators
	 * 
	 */
	public void handleParentChildMenu(By level1Menu, By level2Menu, By level3Menu, By level4Menu) throws InterruptedException {
		doClick(level1Menu);
		Thread.sleep(1500);
		Actions act=new Actions(driver);
		act.moveToElement(getElement(level2Menu)).perform();
		Thread.sleep(1500);
		act.moveToElement(getElement(level3Menu)).perform();
		Thread.sleep(1500);
		doClick(level4Menu);
	}
	//************************Wait Utils*****************************//
	
	/**
	 * An expectation for checking that an element is present on the DOM of a page.
	 *  This does not necessarily mean that the element is visible on the page.
	 * @param locator
	 * @param timeOut
	 * @return
	 */
	public  WebElement waitForElementPresence(By locator,int timeOut) {
		WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(timeOut));
		WebElement element=wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		checkElementHighlight(element);
		return element; 
		
		//presenceOfElementLocated->To check the presence of the element on the DOM of the page
		//This does not necessarily mean that the element is visible on the page
	}
	
	/**
	 * An expectation for checking that an element is present on the DOM of a page and visible on the page as well. 
	 * Visibility means that the element is not only displayed but also has a height and width that is greater than 0.
	 * default polling time/interval time=500ms 	
	 * @param locator
	 * @param timeOut
	 * @return
	 */
	@Step("Waiting for WebElement using locator:{0} with timeout value:{1}")
	public WebElement waitForElementVisible(By locator,int timeOut) {
		WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(timeOut));
		WebElement element=wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		checkElementHighlight(element);
		return element;
		
		//visibilityOfElementLocated->To check that an element is present on the DOM of a page and visible.
		//Visibility means that the element is not only displayed but also has a height and width that is greater than 0.
	}
	
	public WebElement waitForElementVisible(By locator,int timeOut,int pollingTime) {
		WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(timeOut),Duration.ofSeconds(pollingTime));
		WebElement element= wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		checkElementHighlight(element);
		return element;
	}
	
	
	/**
	 * wait for element visible on the page with FluentWait features
	 * @param locator
	 * @param timeOut
	 * @param pollingTime
	 * @return
	 */
	
	public WebElement waitForElementVisibleWithFluentWaitFeatures(By locator,int timeOut,int pollingTime) {
		Wait<WebDriver> wait=new FluentWait<WebDriver>(driver)
				.withTimeout(Duration.ofSeconds(timeOut))
				.pollingEvery(Duration.ofSeconds(pollingTime))
				.ignoring(NoSuchElementException.class)
				.ignoring(StaleElementReferenceException.class)
				.ignoring(ElementNotInteractableException.class)
				.withMessage("======Element is not found======:"+locator);
		WebElement element=wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		checkElementHighlight(element);
		return element;
	}
	
	/**
	 * An expectation for checking an element is visible and enabled such that you can click it.
	 * @param locator
	 * @param timeOut
	 * 
	 */
	public void waitForElementAndClick(By locator,int timeOut) {
		WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
	}
	
	/*
	 * An expectation for checking that all elements present on the web page that match 
	 * the locator are visible. Visibility means that the elements are not only displayed
	 * but also have a height and width that is greater than 0.
	 * 
	 * @param locator
	 * @param timeOut
	 * @return 
	 */
	public List<WebElement> waitForElementsVisible(By locator,int timeOut){
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
	}
	
	/*
	 * An expectation for checking that there is at least one element present on a web page
	 * 
	 * @param locator
	 * @param timeOut
	 * @return 
	 */
	public List<WebElement> waitForElementsPresence(By locator,int timeOut){
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
	}
	
	public String getPageTitleIs(String expectedTitle, int timeOut) {
		if (waitForTitleIs(expectedTitle, timeOut)) {
			return driver.getTitle();
		} else {
			return "-1";
		}
	}

	public String getPageTitleContains(String partTitle, int timeOut) {
		if (waitForTitleContains(partTitle, timeOut)) {
			return driver.getTitle();
		} else {
			return "-1";
		}
	}

	public boolean waitForTitleIs(String expectedTitle, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));

		try {
			return wait.until(ExpectedConditions.titleIs(expectedTitle));// return true if title is matched
		} catch (TimeoutException ex) {
			System.out.println("Title is not matched");
			return false;// returns false if the title is not matched
		}
	}

	public boolean waitForTitleContains(String partTitle, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		try {
			return wait.until(ExpectedConditions.titleContains(partTitle));// return true if the part title is contained in the title
		} catch (TimeoutException ex) {
			System.out.println("Part Title is not matched");
			return false;// returns false if the part title is not present in the complete title
		}
	}
	
	public String waitForTitleContainsAndReturn(String partTitle, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));

		try {
			wait.until(ExpectedConditions.titleContains(partTitle));
			return driver.getTitle();
		} catch (TimeoutException ex) {
			System.out.println("Part Title is not matched");
			return "-1";
		}
	}
	
	public String getPageURLContains(String partURL, int timeOut) {
		if (waitForURLContains(partURL, timeOut)) {
			return driver.getCurrentUrl();
		} else {
			return "-1";
		}
	}
	
	public boolean waitForURLContains(String partURL, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));

		try {
			return wait.until(ExpectedConditions.urlContains(partURL));// return true if the part URL is contained in the complete URL
		} catch (TimeoutException ex) {
			System.out.println("Part URL is not matched");
			return false;// returns false if the part URL is not present in the complete URL
		}
	}
	
	
	public String waitForURLContainsAndReturn(String partURL, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));

		try {
			wait.until(ExpectedConditions.urlContains(partURL));
			return driver.getCurrentUrl();
		} catch (TimeoutException ex) {
			System.out.println("Part URL is not matched");
			return "-1";
		}
	}
	
	//Wait for Alert
	public Alert waitForAlertAndSwitch(int timeOut) {
		WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.alertIsPresent());
		//this line automatically switches to alert		
	}
	
	public Alert waitForAlertAndSwitchWithFluentFeatures(int timeOut) {
		Wait<WebDriver> wait=new FluentWait<WebDriver>(driver)
				.withTimeout(Duration.ofSeconds(timeOut))
				.ignoring(NoAlertPresentException.class)
				.withMessage("======JS Alert is not Present======:");
		
		return wait.until(ExpectedConditions.alertIsPresent());
		//this line automatically switches to alert		
	}
	
	public String getAlertText(int timeOut) {
		return waitForAlertAndSwitch(timeOut).getText();
	}
	
	public void acceptAlert(int timeOut) {
		 waitForAlertAndSwitch(timeOut).accept();
	}
	
	public void dismissAlert(int timeOut) {
		 waitForAlertAndSwitch(timeOut).dismiss();
	}
	
	public void enterValueOnAlert(int timeOut,String value) {
		 waitForAlertAndSwitch(timeOut).sendKeys(value);
	}
	
	//Wait for Frame using "By locator" and switch to it
	public void waitForFrameUsingLocatorAndSwitchToIt(By locator,int timeOut) {
		WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(timeOut));	
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(locator));
	}
	
	//wait for Frame using index and switch to it
	public void waitForFrameUsingLocatorAndSwitchToIt(int index,int timeOut) {
		WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(timeOut));	
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(index));
	}
	
	//wait for Frame using frame name or frame id and switch to it
		public void waitForFrameUsingLocatortAndSwitchToIt(String idOrName,int timeOut) {
			WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(timeOut));	
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(idOrName));
		}
	
	//wait for Frame using WebElement and switch to it
	public void waitForFrameUsingLocatortAndSwitchToIt(WebElement element,int timeOut) {
		WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(timeOut));	
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(element));
	}
	
	//wait for Window/Tab:
	public boolean waitForWindowOrTab(int exceptedWindowCount,int timeOut ) {
		WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		try {
		wait.until(ExpectedConditions.numberOfWindowsToBe(exceptedWindowCount));
			return true;
		}catch(TimeoutException ex) {
			System.out.println("Number of Windows do not match...");
			//catch block is to display the message
		}
		return false;
	}
	
	//to get text:getText()
	//to get attribute:getAttribute()
	//to fetch single Web Element:fe
	//to fetch multiple Web Elements:fes
	//to type something:sendKeys()
	//to click a Web Element: click()
	//to check if a Web Element is displayed: isDisplayed()
	//to check if a Web Element is enabled: isEnabled()
	//to check if an Web Element is selected: isSelected()
	
	//Chrome Dev Protocols this kind of Errors are seen in Chrome n Edge browsers
	//As CDP is applicable for chrome and edge browsers
	//Warning:Unable to find the excact match for CDP version 127 ......
	//CDP 125->supporting Selenium version 4.22(125) -> There wont be any warnings
	//CDP 126->supporting upto Selenium version 4.22(125) 
	//CDP 127(browser has upgraded)->supporting upto Selenium version 4.22(125/126)
	//To prevent the warning upgrade selenium to the version supporting CDP
	//or the warning can be avoided as its not an error
}
