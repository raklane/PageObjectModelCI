package com.rakesh.pages.accounts;

import org.openqa.selenium.By;

import com.rakesh.base.Page;

public class CreateAccountPage extends Page {
	
	public void createAccount(String name) {
		
		driver.findElement(By.cssSelector("#Crm_Accounts_ACCOUNTNAME")).sendKeys(name);
		
	}
	
}
