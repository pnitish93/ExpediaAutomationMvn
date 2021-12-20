package com.expedia.ExpediaMavenFramework.pageclasses;

import java.util.ArrayList;
import java.util.Arrays;
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
 * @author Nitish Panda Automation of some components of Expedia website using
 *         Page Factory
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

	@FindBy(id = "ChildOnLap")
	private WebElement lapRadio;

	@FindBy(id = "ChildInSeat")
	private WebElement seatRadio;

	@FindBy(id = "preferred-class-input-trigger")
	private WebElement currentFlightClassButton;

	@FindBy(xpath = "//span[text()='Economy']//parent::a")
	private WebElement economyOption;

	@FindBy(xpath = "//span[text()='Premium economy']//parent::a")
	private WebElement premiumEconomyOption;

	@FindBy(xpath = "//span[text()='Business class']//parent::a")
	private WebElement businessClassOption;

	@FindBy(xpath = "//span[text()='First class']//parent::a")
	private WebElement firstClassOption;

	enum Traveller {
		CHILDREN, INFANTS, ADULTS
	}

	enum MinorTraveller {
		CHILDREN, INFANTS;
	}

	/**
	 * Constructor
	 * 
	 * @param driver
	 */
	public FlightsOneWay(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	/**
	 * Clicks on flight tab
	 * 
	 * @return void
	 * @param void
	 */
	public void clickFlightsTab() {
		clickWithoutWait(flightsTab);
		log.info("clicking flights tab");
	}

	/**
	 * Clicks on one way button
	 * 
	 * @return void
	 * @param void
	 */
	public void clickOneWay() {
		clickWithoutWait(oneWayButton);
		log.info("clicking 'one-way' in flights tab");
	}

	/**
	 * sends origin city
	 * 
	 * @return void
	 * @param cityValue
	 */
	public void provideOriginCity(String cityValue) {
		clickWithoutWait(originCityButton);
		sendTextToElement(originCityField, true, cityValue);
		sendSpecialKeyStrokes(originCityField, Keys.ENTER);
		log.info("providing origin city for one - way flights # " + cityValue);
		// GeneralUtility.findCity(cityValue, "origin", driver);
	}

	/**
	 * sends destination city
	 * 
	 * @return void
	 * @param cityValue
	 */
	public void provideDestCity(String cityValue) {
		clickWithoutWait(destinationCityButton);
		sendTextToElement(destinationCityField, true, cityValue);
		sendSpecialKeyStrokes(destinationCityField, Keys.ENTER);
		log.info("providing destination city for one - way flights as " + cityValue);
		// GeneralUtility.findCity(cityValue, "destination", driver);
	}

	/**
	 * Sends the departure date
	 * 
	 * @return void
	 * @param date
	 * @throws InterruptedException
	 */
	public void provideDepartDate(String date) {
		clickWithoutWait(departDateButton);
		log.info("The date to be provided is "+date);
		clickDateElementIfExists(date);
		
	}

	/**
	 * Clicks on submit button after entering all the necessary details
	 * 
	 * @return void
	 */
	public FlightsResultPage searchFlights() {
		clickWithoutWait(flightSearchButton);
		log.info("Searching flights using Search button");
		doExplicitWaitForAppearanceFor(60, "//span[text()='Choose departing flight']");
		return new FlightsResultPage(driver);
	}

	/***
	 * Selects the number of adults, children and Infants
	 * 
	 * @param adultCount
	 * @param childrenCount
	 * @param infantCount
	 */
	public void selectOnlyNumberOfTravellers(String adultCount, String childrenCount, String infantCount) {
		selectTravellerNumber(adultCount, childrenCount, infantCount);
		GeneralUtility.doHardWaitFor(2000);
		clickOnTheTravellerDoneButton();
	}

	/***
	 * Selects the number of travellers
	 * 
	 * @param value
	 * @param currentValue
	 * @param desiredValue
	 */
	private void incrementOrDecrement(Traveller value, int currentValue, int desiredValue) {
		WebElement incrementElement = null;
		WebElement decrementElement = null;
		switch (value) {
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
	 * Method for selecting the number of travellers
	 * 
	 * @param adultCount
	 * @param childrenCount
	 * @param infantCount
	 */
	private void selectTravellerNumber(String adultCount, String childrenCount, String infantCount) {
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
	}

	/***
	 * Clicks on the done button for the travellers - isolated to separate method
	 * due to future reusability
	 */
	private void clickOnTheTravellerDoneButton() {
		scrollToTheElement(travellerDoneButton);
		clickAndWait(travellerDoneButton, 2);
	}

	/***
	 * Method for selecting the number of travellers, their ages and the infant
	 * sitting position
	 * 
	 * @param adultCount
	 * @param childrenCount
	 * @param childrenAge
	 * @param infantCount
	 * @param infantAges
	 */
	public void selectTravellersWithAllOptions(String adultCount, String childrenCount, String childrenAges,
			String infantCount, String infantAges, String infantSitting, String flightClass) {
		selectTravellerNumber(adultCount, childrenCount, infantCount);
		chooseAges(childrenAges, MinorTraveller.CHILDREN);
		chooseAges(infantAges, MinorTraveller.INFANTS);
		chooseInfantSitting(infantSitting);
		clickOnTheTravellerDoneButton();
		selectFlightClass(flightClass);
	}

	/***
	 * Methods to choose ages of children and infants
	 * 
	 * @param ages - string containing ages of passengers separated by commas
	 * @param trav - enum values denoting if the passenger is an infant or a
	 *             children
	 */
	private void chooseAges(String ages, MinorTraveller trav) {
		ArrayList<String> ageList = new ArrayList<String>();
		List<WebElement> ageDropdowns = null;
		if (ages.contains(",")) {
			String[] ageArray = ages.split(",");
			ageList.addAll(Arrays.asList(ageArray));
		} else if (!ages.contains(",") && ages != "") {
			ageList.add(ages);
		}
		switch (trav) {
		case CHILDREN:
			ageDropdowns = getWebElements("xpath=>//select[contains(@id, 'child-age-input-0')]");
			break;
		case INFANTS:
			ageDropdowns = getWebElements("xpath=>//select[contains(@id, 'infant-age-input-0')]");
			break;
		}
		if (ageList.size() != 0) {
			for (int j = 0; j < ageList.size(); j++) {
				selectDropDownValueByVisibleText(ageDropdowns.get(j), ageList.get(j));
				GeneralUtility.doHardWaitFor(3000);
			}
		}
	}

	/***
	 * Method to configure sitting options for infants
	 * 
	 * @param sittingOption - various infant sitting options offered by Expedia like
	 *                      - on lap, in seat
	 */
	private void chooseInfantSitting(String sittingOption) {
		WebElement sittingRadio = null;
		if (sittingOption.equalsIgnoreCase("on lap")) {
			sittingRadio = lapRadio;
		}
		if (sittingOption.equalsIgnoreCase("in seat")) {
			sittingRadio = seatRadio;
		}
		if (sittingRadio != null) {
			clickRadioIfNotSelectedAndWait(sittingRadio, 2000);
		}
	}

	/***
	 * Method to select appropriate flight class as per the argument passed
	 * 
	 * @param flightClass - any of the flight class categories offered in Expedia
	 *                    like - 'Economy', 'Business class' etc
	 */
	public void selectFlightClass(String flightClass) {
		if (flightClass == null || flightClass.equals("")) {
			log.info("Flightclass is either null or blank.");
		}
		else {
			String classLower = flightClass.toLowerCase();
			if (!currentFlightClassButton.getAttribute("aria-label").toLowerCase().contains(classLower)) {
				currentFlightClassButton.click();
				switch (flightClass.toLowerCase()) {
				case "economy":
					economyOption.click();
					break;
				case "premium economy":
					premiumEconomyOption.click();
					break;
				case "business class":
					businessClassOption.click();
					break;
				case "first class":
					firstClassOption.click();
					break;
				default:
					System.out.println("No such flights class present.");
				}
			}
		}
	}

}
