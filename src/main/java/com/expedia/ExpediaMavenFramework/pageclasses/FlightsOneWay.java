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
 * Automation of some components of Expedia website using Page Factory
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
	
	@FindBy(xpath = "//a[contains(text(), 'traveller')]")
	private WebElement travellerDropdown;
	
	@FindBy(xpath = "//label[text()='Adults']//following-sibling::div//button[1]")
	private WebElement adultDecrementButton;
	
	@FindBy(xpath = "//label[text()='Adults']//following-sibling::div//button[2]")
	private WebElement adultIncrementButton;
	
	@FindBy(xpath = "//label[text()='Children']//following-sibling::div//button[1]")
	private WebElement childrenDecrementButton;
	
	@FindBy(xpath = "//label[text()='Children']//following-sibling::div//button[2]")
	private WebElement childrenIncrementButton;
	
	@FindBy(xpath = "//label[text()='Infants']//following-sibling::div//button[1]")
	private WebElement infantDecrementButton;
	
	@FindBy(xpath = "//label[text()='Infants']//following-sibling::div//button[2]")
	private WebElement infantIncrementButton;
	
	@FindBy(id = "adult-input-0")
	private WebElement currentAdults;
	
	@FindBy(id = "child-input-0")
	private WebElement currentChildren;
	
	@FindBy(id = "infant-input-0")
	private WebElement currentInfants;
	
	@FindBy(xpath = "//button[@data-testid='guests-done-button']")
	private WebElement travellerDoneButton;
	
	enum Traveller{
		CHILDREN,
		INFANTS,
		ADULTS
	}
	
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
	
	/***
	 * Selects the number of adults, children and Infants
	 * @param adultCount
	 * @param childrenCount
	 * @param infantCount
	 */
	public void selectNumberOfTravellers(String adultCount, String childrenCount, String infantCount) {
		int adultCountInt = Integer.parseInt(adultCount);
		int childrenCountInt = Integer.parseInt(childrenCount);
		int infantCountInt = Integer.parseInt(infantCount);
		int currentAdultCount = Integer.parseInt(getAnAttribute(currentAdults, "value"));
		int currentChildrenCount = Integer.parseInt(getAnAttribute(currentChildren, "value"));
		int currentInfantCount = Integer.parseInt(getAnAttribute(currentInfants, "value"));
		clickAndWait(travellerDropdown, 2);
		incrementOrDecrement(Traveller.ADULTS, currentAdultCount, adultCountInt);
		incrementOrDecrement(Traveller.CHILDREN, currentChildrenCount, childrenCountInt);
		incrementOrDecrement(Traveller.INFANTS, currentInfantCount, infantCountInt);
		clickOnTheTravellerDoneButton();
	}
	
	/***
	 * Selects the number of travellers
	 * @param value
	 * @param currentValue
	 * @param desiredValue
	 */
	private void incrementOrDecrement(Traveller value, int currentValue, int desiredValue) {
		WebElement incrementElement = null;
		WebElement decrementElement = null;
		switch(value) {
		case ADULTS:
			incrementElement = adultIncrementButton;
			decrementElement = adultDecrementButton;
			break;
		case CHILDREN:
			incrementElement = childrenIncrementButton;
			decrementElement = childrenDecrementButton;
			break;
		case INFANTS:
			incrementElement = infantIncrementButton;
			decrementElement = infantDecrementButton;
			break;
		}
		adjustValuesUsingButtons(incrementElement, decrementElement, currentValue, desiredValue);
	}
	
	/***
	 * Clicks on the done button for the travellers - isolated to separate method due to future reusability
	 */
	private void clickOnTheTravellerDoneButton() {
		scrollToTheElement(travellerDoneButton);
		clickAndWait(travellerDoneButton, 2);
	}
}

