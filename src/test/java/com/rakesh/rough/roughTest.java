package com.rakesh.rough;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.rakesh.base.Page;
import com.rakesh.pages.HomePage;
import com.rakesh.pages.LoginPage;
import com.rakesh.pages.ZohoAppPage;
import com.rakesh.pages.accounts.AccountsPage;
import com.rakesh.pages.accounts.CreateAccountPage;

public class roughTest {
	
	public static void main(String args[]) {
		
				
		HomePage homePage = new HomePage();
		LoginPage loginPage = homePage.goToLogin();
		
		ZohoAppPage zohoAppPage = loginPage.doLogin("rakesh.xyzz@gmail.com", "selenium@123");
		
		zohoAppPage.goToCRM();
		
		AccountsPage account = Page.menu.goToAccounts();
		
		CreateAccountPage cap = account.goToCreateAccountPage();
		
		cap.createAccount("Rakesh");
		
	}
	
}
