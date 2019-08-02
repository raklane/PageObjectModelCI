package com.rakesh.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.rakesh.base.Page;

public class ZohoAppPage extends Page {
	
	
	public void goToBooks() {
		driver.findElement(By.cssSelector(".zicon-apps-books.zicon-apps-96")).click();
	}
	
	public void goToCalender() {
		driver.findElement(By.cssSelector(".zicon-apps-calendar.zicon-apps-96")).click();
	}
	
	public void goToCampaigns() {
		driver.findElement(By.cssSelector("zicon-apps-campaigns zicon-apps-96")).click();
	}
	
	public void goToCliq() {
		driver.findElement(By.cssSelector(".zicon-apps-chat.zicon-apps-96")).click();
	}
	
	public void goToConnect() {
		driver.findElement(By.cssSelector(".zicon-apps-connect.zicon-apps-96")).click();
	}
	
	public void goToCRM() {
		driver.findElement(By.cssSelector(".zicon-apps-crm.zicon-apps-96")).click();
	}
	
	public void goToDesk() {
		driver.findElement(By.cssSelector(".zicon-apps-support.zicon-apps-96")).click();
	}
	
	public void goToInvoice() {
		driver.findElement(By.cssSelector(".zicon-apps-invoice.zicon-apps-96")).click();
	}
	
	public void goToMail() {
		driver.findElement(By.cssSelector(".zicon-apps-mail.zicon-apps-96")).click();
	}

}
