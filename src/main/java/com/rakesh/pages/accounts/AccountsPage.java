package com.rakesh.pages.accounts;

import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;

import com.rakesh.base.Page;

public class AccountsPage extends Page {
	
	public void goToImportPage() {
		
	}
	
	
	public CreateAccountPage goToCreateAccountPage(){
		driver.findElement(By.cssSelector("#createIcon")).click();
		driver.findElement(By.cssSelector("#submenu_Accounts")).click();
		return new CreateAccountPage();
	}
}
