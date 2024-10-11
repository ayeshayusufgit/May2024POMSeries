package com.qa.opencart.constants;

import java.util.List;

public class AppConstants{
	
	//Advantages for the Constants class
	//1.Common repo for all constants
	//2.Ex if ACCOUNTS_PAGE_TITLE is being used in 5 places it can be picked up using the class n constant
	//if the ACCOUNTS_PAGE_TITLE gets changed then in that case it can be changed only in the Constants class
	//instead of changing in 5 places
	//3.If other devs need to use they can used the Constants class with the constant name instead of creating many constants unnecessarily
	
	public static final int DEFAULT_SHORT_TIME_OUT=5;
	public static final int DEFAULT_MEDIUM_TIME_OUT=10;
	public static final int DEFAULT_LONG_TIME_OUT=20;
	
	public static final String LOGIN_PAGE_TITLE="Account Login";
	public static final String LOGIN_PAGE_FRACTION_URL="route=account/login";
	
	public static final String ACCOUNTS_PAGE_TITLE="My Account";
	public static final String ACCOUNTS_PAGE_FRACTION_URL="route=account/account";
	public static final int ACCOUNTS_PAGE_HEADERS_COUNT=4;
	public static final List<String> EXPECTED_ACCOUNTS_PAGE_HEADERS_LIST= List.of("My Account","My Orders","My Affiliate Account","Newsletter");
	
	public static final String USER_REGISTER_SUCCESS_MESSAGE="Your Account Has Been Created!";

	//****************************************************************************************//
	public static final String REGISTER_SHEET_NAME="register";
	public static final String SEARCH_SHEET_NAME="search";


}
