package com.expedia.ExpediaMavenFramework.testclasses.basetestclass;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class WebDriverFactory {
	private static final WebDriverFactory instance = new WebDriverFactory();
	
	private WebDriverFactory() {	
	}
	
	public static WebDriverFactory getInstance() {
		return instance;
	}
	
	private static ThreadLocal<WebDriver> threadedDriver = new ThreadLocal<WebDriver>();
	
	public WebDriver getDriver(String browser) {
		WebDriver driver = null;
		setDriver(browser);
		if(threadedDriver.get() == null) {
			if(browser.equalsIgnoreCase("chrome")) {
				driver = new ChromeDriver();
			}
			else if(browser.equalsIgnoreCase("firefox")) {
				driver = new FirefoxDriver();
			}
		}
		threadedDriver.set(driver);
		threadedDriver.get().manage().window().maximize();
		threadedDriver.get().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return threadedDriver.get();
	}
	
	public void setDriver(String browser) {
		String os = System.getProperty("os.name");
		String driverPath = System.getProperty("user.dir")+"//Drivers//";
		String driverKey = "";
		if(browser.equalsIgnoreCase("chrome")) {
			driverPath = driverPath+"chromedriver";
			driverKey = "webdriver.chrome.driver";
		}
		else if(browser.equalsIgnoreCase("Firefox")) {
			driverPath = driverPath+"geckodriver";
			driverKey = "webdriver.gecko.driver";
		}
		driverPath = (os.substring(0, 3).equalsIgnoreCase("win"))?(driverPath + ".exe"):driverPath;
		System.setProperty(driverKey, driverPath);
	}
	
	public void quitDriver() {
		threadedDriver.get().quit();
		threadedDriver.set(null);
	}
}
