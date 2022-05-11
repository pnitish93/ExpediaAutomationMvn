package com.expedia.ExpediaMavenFramework.pageclasses;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.expedia.ExpediaMavenFramework.pageclasses.basePageClass.CustomDriver;

public class FlightsReturn extends CustomDriver {
	WebDriver driver;
	public static Logger log = LogManager.getLogger(FlightsReturn.class.getName());
	
	@FindBy(xpath = "//a[@aria-controls='wizard-flight-pwa']")
	private WebElement flightsTab;
	
	@FindBy(xpath = "//a[@aria-controls='wizard-flight-tab-roundtrip']")
	private WebElement returnFlightsTab;
	
	@FindBy(xpath = "//button[@aria-label='Leaving from']")
	private WebElement originCityButton;
	
	@FindBy(xpath = "//button[@aria-label='Going to']")
	private WebElement destinationCityButton;
	
	@FindBy(xpath = "//button[contains(@aria-label, 'Departing')]")
	private WebElement departingDateButton;
	
	@FindBy(xpath = "//button[contains(@aria-label, 'Returning')]")
	private WebElement returningDateButton;
	
	@FindBy(xpath = "//button[text()='Search']")
	private WebElement searchButton;
	
	@FindBy(xpath = "//input[contains(@placeholder, 'leaving from')]")
	private WebElement originCityField;
	
	@FindBy(xpath = "//input[contains(@placeholder, 'going to')]")
	private WebElement destinationCityField;
	
	public FlightsReturn(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public void clickOnFlightsTab() {
		clickAndWait(flightsTab, 5000);
		log.info("Clicked on flights tab.");
	}
	
	public void clickOnReturnTab() {
		clickAndWait(returnFlightsTab, 2000);
		log.info("Clicked on return tab.");
	}
	
	public void provideSource(String sourceCity) {
		clickAndWait(originCityButton, 2000);
		sendTextToElement(originCityField, true, sourceCity);
		sendSpecialKeyStrokes(originCityField, Keys.ENTER);
	}
	
	public void provideDestination(String destinationCity) {
		clickAndWait(destinationCityButton, 2000);
		sendTextToElement(destinationCityField, true, destinationCity);
		sendSpecialKeyStrokes(destinationCityField, Keys.ENTER);
	}
	
	public FlightsResultPage clickSearchButton() {
		clickWithoutWait(searchButton);
		log.info("Clicked on the search button after providing return flight query details.");
		doExplicitWaitForAppearanceFor(60, "//span[text()='Choose departing flight']");
		return new FlightsResultPage(driver);
	}
	
	/**
	 * Sends the departure date
	 * 
	 * @return void
	 * @param date
	 */
	public void provideDepartDate(String date) {
		clickWithoutWait(departingDateButton);
		log.info("The departing date to be provided is "+date);
		clickDateElementIfExists(date);
	}
	
	/**
	 * Sends the return date
	 * 
	 * @return void
	 * @param date
	 */
	public void provideReturnDate(String date) {
		clickWithoutWait(returningDateButton);
		log.info("The return date to be provided is "+date);
		clickDateElementIfExists(date);
	}
	
}
