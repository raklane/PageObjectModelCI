package com.rakesh.testcases;

import org.testng.annotations.AfterSuite;

import com.rakesh.base.Page;

public class BaseTest {
	
	@AfterSuite
	public void tearDown() {
		
		Page.quit();
		
	}

}
