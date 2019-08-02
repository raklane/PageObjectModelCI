package com.rakesh.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.rakesh.base.Page;

public class LoginPage extends Page {
	
	
	public ZohoAppPage doLogin(String username, String password) {
		
		type("username_CSS", username);
		type("password_CSS", password);
		click("loginButton_CSS");
		return new ZohoAppPage();
	}

}
