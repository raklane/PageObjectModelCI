package com.rakesh.utilities;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Hashtable;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.annotations.DataProvider;

import com.rakesh.base.Page;
import com.relevantcodes.extentreports.LogStatus;

public class Utilities extends Page {
	
	public static String screenshotPath;
	public static String screenshotName;
	
	
	public static void captureScreenshot() throws IOException {
		
		Date d = new Date();
		screenshotName = d.toString().replaceAll(" ", "_").replaceAll(":", "_");
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir") + "\\target\\surefire-reports\\html\\" + screenshotName));
		
	}
	
	
	@DataProvider(name="dp")
	public Object[][] getData(Method m){
		
		String sheetName = m.getName();
		
		int rowCount = excel.getRowCount(sheetName);
		int colCount = excel.getColumnCount(sheetName);
		
		
		Object[][] data = new Object[rowCount-1][1];
		
		Hashtable<String, String> table = null;
		
		
		for(int rowNum = 2; rowNum<=rowCount; rowNum++) {
			
			table = new Hashtable<String, String>();
			for(int colNum = 0; colNum<colCount; colNum++) {
				
				table.put(excel.getCellData(sheetName, colNum, 1), excel.getCellData(sheetName, colNum, rowNum));
				data[rowNum-2][0] = table;
			}
		}
		
		return data;
	}
	
	public static boolean isTestRunnable(String testName, ExcelReader excel) {
		
		String sheetName = "test_suite";
		int rowCount = excel.getRowCount(sheetName);
		String testCaseName;
		
		for(int i=2; i<=rowCount; i++) {
			testCaseName = excel.getCellData(sheetName, "TCID", i);
			if(testCaseName.equalsIgnoreCase(testName)) {
				if(excel.getCellData(sheetName, "runMode", i).equalsIgnoreCase("Y"))
					return true;
				else
					return false;
			}
		}
		
		return false;
	}
	
}
