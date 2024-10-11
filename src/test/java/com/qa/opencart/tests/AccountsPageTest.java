package com.qa.opencart.tests;

import static org.testng.Assert.assertEquals;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;

public class AccountsPageTest extends BaseTest {
	
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
	
	@DataProvider
	public Object[][] getSearchKey(){
		return new Object[][]{
								{"macbook",3}, //if the searchCount is very dynamic then count>0 can be added
								{"imac",1},	  //Since in this the searchCount doesn't change so the searchCount is passed	
								{"samsung",2}
							};
	}
	
	@Test(dataProvider = "getSearchKey")//Do not mix your data providers, always create separate data providers
	public void searchCount(String searchKey,int searchCount) {
		//resultPage=accountsPage.doSearch("macbook");
		resultPage=accountsPage.doSearch(searchKey);
		
		//Assert.assertEquals(resultPage.getSearchResultsCount(), 3);
		//Earlier the searchTerm and searchCount were hardcoded ie macbook n 3, now coming from the DataProvider
		
		Assert.assertEquals(resultPage.getSearchResultsCount(), searchCount);
	}
	
	@DataProvider
	public Object[][] getSearchData(){
		return new Object[][]{
								{"macbook","MacBook Pro"},
								{"macbook","MacBook Air"},
								{"imac","iMac"},
								{"samsung","Samsung SyncMaster 941BW"},
								{"samsung","Samsung Galaxy Tab 10.1"} 
								//In an e-commerce app if its working for 5 searches then it will work for all the searches
								//The test data has to be prepared accordingly, it will be fixed, so this need not be changed
								//The test data can be supplied based on 10 different categories, so if this is working then it'll work for all categories
								
							}; //If test data is alot(100 rows etc) then it should be supplied from an excel file
	}
	
	//TDD:developing the code based on the testcases
	//This is approach the testcases are written first then the code is refactored/adjusted after the testcases 
	//4 pages are involved
	@Test(dataProvider = "getSearchData")
	public void searchTest(String searchKey,String productName) {
		//resultPage=accountsPage.doSearch("macbook");//Hardcoded Data
		resultPage=accountsPage.doSearch(searchKey);//replaced with Dynamic Data
		
		//productInfoPage=resultPage.selectProduct("MacBook Pro");//Hardcoded Data
		productInfoPage=resultPage.selectProduct(productName);//replaced with Dynamic Data
		
		//Assert.assertEquals(productInfoPage.getProductHeader(), "MacBook Pro");//Hardcoded Data
		Assert.assertEquals(productInfoPage.getProductHeader(), productName);//replaced with Dynamic Data
	}
	
	//The Test Data is supplied from the PageTestClass
	//The PageClass shouldn't have a single hardcoded data
}
