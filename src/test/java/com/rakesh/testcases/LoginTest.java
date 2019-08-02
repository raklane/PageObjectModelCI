package com.rakesh.testcases;

import org.testng.annotations.Test;

import com.rakesh.pages.HomePage;
import com.rakesh.pages.LoginPage;
import com.rakesh.pages.ZohoAppPage;

public class LoginTest extends BaseTest {
	
	@Test
	public void loginTest() {
		
		//Login test
		HomePage homePage = new HomePage();
		LoginPage loginPage = homePage.goToLogin();
		
		ZohoAppPage zohoAppPage = loginPage.doLogin("rakesh.xyzz@gmail.com", "selenium@123");
		
	}

}
