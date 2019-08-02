package com.rakesh.base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

import com.rakesh.pages.accounts.AccountsPage;

public class TopMenu {
	
	WebDriver driver;
	
	public TopMenu(WebDriver driver) {
		this.driver = driver;
	}
	
	public void goToHome() {
		driver.findElement(By.cssSelector(".lyteMenuActive.lyteItem")).click();
	}
	
	public void goToLeads() {
		driver.findElement(By.cssSelector(".undefined.lyteItem:nth-child(2)")).click();
	}
	
	public void goToContacts() {
		driver.findElement(By.cssSelector(".undefined.lyteItem:nth-child(3)")).click();
	}
	
	public AccountsPage goToAccounts() {
		driver.findElement(By.cssSelector(".undefined.lyteItem:nth-child(4)")).click();
		return new AccountsPage();
	}
	
	public void goToDeals() {
		driver.findElement(By.cssSelector(".undefined.lyteItem:nth-child(5)")).click();
	}
	
	public void goToActivities() {
		driver.findElement(By.cssSelector(".undefined.lyteItem:nth-child(6)")).click();
	}
	
	public void goToReports() {
		driver.findElement(By.cssSelector(".undefined.lyteItem:nth-child(7)")).click();
	}
	
	public void goToAnalytics() {
		driver.findElement(By.cssSelector(".undefined.lyteItem:nth-child(8)")).click();
	}
	
	public void goToProjects() {
		driver.findElement(By.cssSelector(".undefined.lyteItem:nth-child(9)")).click();
	}
	
	public void signout() {
		driver.findElement(By.cssSelector("#dropclk")).click();
		Actions action = new Actions(driver);
		action.moveToElement(driver.findElement(By.cssSelector("a[data-zcqa='signOut']"))).build().perform();
		driver.findElement(By.cssSelector("a[data-zcqa='signOut']")).click();
	}
}
