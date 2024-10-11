package com.qa.opencart.tests;

import static org.testng.Assert.assertEquals;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ExcelUtil;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

//Using this test with dataProvider using Excel Utils
//FW Video 6
//@Listeners({ExtentReportListener.class,AnnotationTransformer.class})
//The above annotations have to be added in all the PageTest classes
@Epic("Epic 100:Open Cart Account Page Test")
@Feature("Feature 101: Account feature")
@Story("US 120:All the features related to Account Page")
public class AccountsPageTest3 extends BaseTest {
	
	@BeforeClass
	public void accSetUp() {
		accountsPage=loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}
	
	@Test
	public void accountPageTitleTest() {
		String accTitle=accountsPage.getAccountsPageTitle();
		Assert.assertEquals(accTitle, AppConstants.ACCOUNTS_PAGE_TITLE);
	}
	
	@Test
	public void isLogoutLinkExistTest() {
		Assert.assertTrue(accountsPage.isLogoutLinkExist());
	}
	
	@Test
	public void accPageHeadersCountTest() {	
		Assert.assertEquals(accountsPage.getTotalAccountsPageHeader(), AppConstants.ACCOUNTS_PAGE_HEADERS_COUNT);
	}
	
	@Test
	public void accPageHeadersTest() {
		List<String> actualHeadersList=accountsPage.getAccPageHeaders();
		Assert.assertEquals(actualHeadersList, AppConstants.EXPECTED_ACCOUNTS_PAGE_HEADERS_LIST);
	}
	
	//Assignment 1:needs to be picked up from Excel
	@DataProvider
	public Object[][] getSearchKey(){
		return new Object[][]{
								{"macbook",3}, //if the searchCount is very dynamic then count>0 can be added
								{"imac",1},	  //Since in this the searchCount doesn't change so the searchCount is passed	
								{"samsung",2}
							};
	}
	
	
	
	@Description("Search functionality Test")
	@Severity(SeverityLevel.CRITICAL)
	@Test(dataProvider = "getSearchKey")
	//Do not mix your data providers, always create separate data providers
	public void searchCount(String searchKey,int searchCount) {
		//resultPage=accountsPage.doSearch("macbook");
		resultPage=accountsPage.doSearch(searchKey);
		
		//Assert.assertEquals(resultPage.getSearchResultsCount(), 3);
		//Earlier the searchTerm and searchCount were hardcoded ie macbook n 3, now coming from the DataProvider
		
		Assert.assertEquals(resultPage.getSearchResultsCount(), searchCount);
	}
	
	@DataProvider
	public Object[][] getSearchExcelData(){
		return ExcelUtil.geTesttData(AppConstants.SEARCH_SHEET_NAME);
	}
	
	@Test(dataProvider = "getSearchExcelData")
	public void searchTest(String searchKey,String productName) {
		//resultPage=accountsPage.doSearch("macbook");//Hardcoded Data
		resultPage=accountsPage.doSearch(searchKey);//replaced with Dynamic Data
		
		//productInfoPage=resultPage.selectProduct("MacBook Pro");//Hardcoded Data
		productInfoPage=resultPage.selectProduct(productName);//replaced with Dynamic Data
		
		//Assert.assertEquals(productInfoPage.getProductHeader(), "MacBook Pro");//Hardcoded Data
		Assert.assertEquals(productInfoPage.getProductHeader(), productName);//replaced with Dynamic Data
	}
	
}
