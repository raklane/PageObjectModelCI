package com.rakesh.listeners;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.SkipException;

import com.rakesh.base.Page;
import com.rakesh.utilities.MonitoringMail;
import com.rakesh.utilities.TestConfig;
import com.rakesh.utilities.Utilities;
import com.relevantcodes.extentreports.LogStatus;

public class CustomListeners extends Page implements ITestListener, ISuiteListener {
	
	static String message;

	public void onFinish(ITestContext arg0) {
		// TODO Auto-generated method stub
		
	}

	public void onStart(ITestContext arg0) {
		// TODO Auto-generated method stub
		
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {
		// TODO Auto-generated method stub
		
	}

	public void onTestFailure(ITestResult arg0) {
		// TODO Auto-generated method stub
		
		try {
			Utilities.captureScreenshot();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Click to see screenshot");
		System.setProperty("org.uncommons.reportng.escape-output", "false");
		Reporter.log("<a target=\"_blank\" href=" + Utilities.screenshotName + ">Screenshot</a>");
		Reporter.log("<br");
		Reporter.log("<a target=\"_blank\" href=" + Utilities.screenshotName + "><img src=" + Utilities.screenshotName + " height=200 width=200></img></a>");
		
		test.log(LogStatus.FAIL, arg0.getName().toUpperCase() + " FAIL " + arg0.getThrowable().toString());
		test.log(LogStatus.FAIL, test.addScreenCapture(Utilities.screenshotName));
		rep.endTest(test);
		rep.flush();
		
	}

	public void onTestSkipped(ITestResult arg0) {
		// TODO Auto-generated method stub
		test.log(LogStatus.SKIP, "Skipping the test " + arg0.getName().toUpperCase() + "as the run mode is No");
		rep.endTest(test);
		rep.flush();
		
	}

	public void onTestStart(ITestResult arg0) {
		// TODO Auto-generated method stub
		test = rep.startTest(arg0.getName().toUpperCase());
				
	}

	public void onTestSuccess(ITestResult arg0) {
		// TODO Auto-generated method stub
		test.log(LogStatus.PASS , arg0.getName().toUpperCase() + " PASS");
		rep.endTest(test);
		rep.flush();
		
	}

	
	public void onFinish(ISuite arg0) {
		// TODO Auto-generated method stub
		MonitoringMail mail = new MonitoringMail();
		try {
			message = InetAddress.getLocalHost().getHostAddress() + ":8080/job/PageObjectModel/Extent_20report_20-_20Rakesh/extent.html";
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			mail.sendMail(TestConfig.server, TestConfig.from, TestConfig.to, TestConfig.subject, message);
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public void onStart(ISuite arg0) {
		// TODO Auto-generated method stub
		
	}
	
	

}
