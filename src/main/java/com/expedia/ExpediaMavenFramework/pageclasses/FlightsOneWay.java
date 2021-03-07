package com.expedia.ExpediaMavenFramework.pageclasses;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.expedia.ExpediaMavenFramework.pageclasses.basePageClass.CustomDriver;
import com.expedia.ExpediaMavenFramework.utilities.GeneralUtility;

/**
 * @author Nitish Panda
 * Automatin of some components of expedia website using Page Factory
 *
 */

public class FlightsOneWay extends CustomDriver {
	WebDriver driver;
	public static Logger log = LogManager.getLogger(FlightsOneWay.class.getName());
	
	@FindBy(xpath = "//a[@aria-controls='wizard-flight-pwa']")
	private WebElement flightsTab;
	
	@FindBy(xpath = "//a[@aria-controls='wizard-flight-tab-oneway']")
	private WebElement oneWayButton;
	
	@FindBy(xpath = "//button[contains(@aria-label, 'Leaving from')]")
	private WebElement originCityButton;
	
	@FindBy(xpath = "//input[@placeholder='Where are you leaving from?']")
	private WebElement originCityField;
	
	@FindBy(xpath = "//button[contains(@aria-label, 'Going to')]")
	private WebElement destinationCityButton;
	
	@FindBy(xpath = "//input[@placeholder='Where are you going to?']")
	private WebElement destinationCityField;
	
	@FindBy(id = "d1-btn")
	private WebElement departDateButton;
	
	@FindBy(xpath = "//div[@class = 'uitk-new-date-picker date-picker-menu']")
	private WebElement calendarElement;
	
	@FindBy(xpath = "//button[text()='Search']")
	private WebElement flightSearchButton;
	
	/**
	 * Constructor
	 * @param driver
	 */
	public FlightsOneWay(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	/**
	 * Clicks on flight tab
	 * @return void
	 * @param void
	 */
	public void clickFlightsTab() {
		clickWithoutWait(flightsTab);
		log.info("clicking flights tab");
	}
	
	/**
	 * Clicks on one way button
	 * @return void
	 * @param void
	 */
	public void clickOneWay() {
		clickWithoutWait(oneWayButton);
		log.info("clicking 'one-way' in flights tab");
	}
	
	/**
	 * sends origin city
	 * @return void
	 * @param cityValue
	 */
	public void provideOriginCity(String cityValue) {
		clickWithoutWait(originCityButton);
		sendTextToElement(originCityField, true, cityValue);
		sendSpecialKeyStrokes(originCityField, Keys.ENTER);
		log.info("providing origin city for one - way flights # " + cityValue);
		//GeneralUtility.findCity(cityValue, "origin", driver);
	}
	
	/**
	 * sends destination city
	 * @return void
	 * @param cityValue
	 */
	public void provideDestCity(String cityValue) {
		clickWithoutWait(destinationCityButton);
		sendTextToElement(destinationCityField, true, cityValue);
		sendSpecialKeyStrokes(destinationCityField, Keys.ENTER);
		log.info("providing destination city for one - way flights as " + cityValue);
		//GeneralUtility.findCity(cityValue, "destination", driver);
	}
	
	/**
	 * Sends the departure date
	 * @return void
	 * @param date
	 * @throws InterruptedException 
	 */
	public void provideDepartDate(String date) {
		clickWithoutWait(departDateButton);
		clickDateElementIfExists(date);
	}
	
	/**
	 * Clicks on submit button after entering all the necessary details 
	 * @return void
	 */
	public FlightsResultPage searchFlights() {
		clickWithoutWait(flightSearchButton);
		log.info("Searching flights using Search button");
		GeneralUtility.doHardWaitFor(3000);
		return new FlightsResultPage(driver);
	}
}

