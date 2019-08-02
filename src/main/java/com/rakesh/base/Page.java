package com.rakesh.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import com.rakesh.utilities.ExcelReader;
import com.rakesh.utilities.ExtentManager;
import com.rakesh.utilities.Utilities;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class Page {
	
	public static TopMenu menu;
	public static WebDriver driver;
	
	public static Properties config = new Properties();
	public static Properties OR = new Properties();
	public static FileInputStream fis;
	public static Logger log = Logger.getLogger("devpinoyLogger");
	public static ExcelReader excel = new ExcelReader(System.getProperty("user.dir") + "\\src\\test\\resources\\com\\rakesh\\excel\\testData.xlsx");
	public static WebDriverWait wait;
	
	public static ExtentReports rep = ExtentManager.getInstance();
	public static ExtentTest test;
	public static String browser;
	
	public Page() {
		
		if(driver == null) {
			
			try {
				fis = new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\resources\\com\\rakesh\\properties\\Config.properties");
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				config.load(fis);
				log.debug("Properties file loaded.");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				fis = new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\resources\\com\\rakesh\\properties\\OR.properties");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				OR.load(fis);
				log.debug("OR file loaded.");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			//Jenkins browser parameter
			if(System.getenv("browser") != null && !System.getenv("browser").isEmpty()) {
				browser = System.getenv("browser");
				config.setProperty("browser", browser);
			}else {
				browser = System.getenv("browser");
			}
			
			if(config.getProperty("browser").equalsIgnoreCase("chrome")) {
				System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\src\\test\\resources\\com\\rakesh\\executables\\chromedriver.exe");
				Map<String, Object> prefs = new HashMap<String, Object>();
				prefs.put("profile.default_content_setting_values.notifications", 2);
				prefs.put("credentials_enable_service", false);
				prefs.put("profile.password_manager_enabled", false);
				ChromeOptions options = new ChromeOptions();
				options.setExperimentalOption("prefs", prefs);
				options.addArguments("--disable-extensions");
				options.addArguments("--disable-infobars");
				driver = new ChromeDriver(options);
			}else if(config.getProperty("browser").equalsIgnoreCase("firefox")) {
				System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "\\src\\test\\resources\\com\\rakesh\\executables\\geckodriver.exe");
				driver = new FirefoxDriver();
			}else if(config.getProperty("browser").equalsIgnoreCase("ie")) {
				System.setProperty("webdriver.ie.driver", System.getProperty("user.dir") + "\\src\\test\\resources\\com\\rakesh\\executables\\IEDriverServer.exe");
				driver = new FirefoxDriver();
			}

			
			driver.get(config.getProperty("testsiteurl"));
			log.debug(config.getProperty("browser") + " launched with URL: " + config.getProperty("testsiteurl"));
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Integer.parseInt(config.getProperty("implicit.wait")), TimeUnit.SECONDS);
			
			wait = new WebDriverWait(driver, 10);
			
			menu = new TopMenu(driver);
			
		}
			
	}
	
	public void click(String locator) {
		if(locator.endsWith("_CSS")) {
			driver.findElement(By.cssSelector(OR.getProperty(locator))).click();
		}else if(locator.endsWith("_XPATH")) {
			driver.findElement(By.xpath(OR.getProperty(locator))).click();
		}else if(locator.endsWith("_ID")) {
			driver.findElement(By.id(OR.getProperty(locator))).click();
		}
		log.debug("Clicking on element: " + locator);
		test.log(LogStatus.INFO, "Clicking on: " + locator);
	}
	
	
	
	public void type(String locator, String value) {
		if(locator.endsWith("_CSS")) {
			driver.findElement(By.cssSelector(OR.getProperty(locator))).sendKeys(value);
		}else if(locator.endsWith("_XPATH")) {
			driver.findElement(By.xpath(OR.getProperty(locator))).sendKeys(value);
		}else if(locator.endsWith("_ID")) {
			driver.findElement(By.id(OR.getProperty(locator))).sendKeys(value);
		}
		log.debug("Typing on element: " + locator + ", Entered value: " + value);
		test.log(LogStatus.INFO, "Entering in: " + locator + ", Entered value: " + value);
	}
	
	public boolean isElementPresent(By by) {
		
		try {
			
			driver.findElement(by);
			return true;
			
		}catch(NoSuchElementException e) {
			
			return false;
			
		}
		
	}
	
	static Select dropdown;
	
	public static void select(String locator, String value) {
		
		if(locator.endsWith("_CSS")) {
			dropdown = new Select(driver.findElement(By.cssSelector(OR.getProperty(locator))));
		}else if(locator.endsWith("_XPATH")) {
			dropdown = new Select(driver.findElement(By.xpath(OR.getProperty(locator))));
		}else if(locator.endsWith("_ID")) {
			dropdown = new Select(driver.findElement(By.id(OR.getProperty(locator))));
		}
		log.debug("Selecting from dropdown: " + locator + ", Selected value: " + value);
		dropdown.selectByVisibleText(value);
		test.log(LogStatus.INFO, "Selecting from dropdown: " + locator + ", Selected value: " + value);
		
	}
	
	
	
	public static void verifyEquals(String expected, String actual) throws IOException {
		
		try {
			Assert.assertEquals(actual, expected);
		}catch(Throwable t) {
			Utilities.captureScreenshot();
			//ReportNG
			Reporter.log("<br>" + "Verification failed: " + t.getMessage() + "<br>");
			Reporter.log("<a target=\"_blank\" href=" + Utilities.screenshotName + "><img src=" + Utilities.screenshotName + " height=200 width=200></img></a>");
			Reporter.log("<br>");
			Reporter.log("<br>");
			//Extent report
			test.log(LogStatus.FAIL, "Verification failed___: " +  t.getMessage());
			test.log(LogStatus.FAIL, test.addScreenCapture(Utilities.screenshotName));
			
		}
		log.info("verification failure methoh end");
	}
	
	public static void quit() {
		driver.quit();
	}

}
