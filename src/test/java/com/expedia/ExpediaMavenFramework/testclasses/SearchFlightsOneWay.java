package com.expedia.ExpediaMavenFramework.testclasses;

import org.testng.annotations.Test;

import com.expedia.ExpediaMavenFramework.dataProviders.DataProviderClassFlights;
import com.expedia.ExpediaMavenFramework.pageclasses.FlightsOneWay;
import com.expedia.ExpediaMavenFramework.pageclasses.FlightsResultPage;
import com.expedia.ExpediaMavenFramework.testclasses.basetestclass.TestConfig;
import com.expedia.ExpediaMavenFramework.utilities.GeneralUtility;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

public class SearchFlightsOneWay extends TestConfig{
	/**
	 * Method to perform common operation for the one way flights test cases
	 * @param from - source city
	 * @param to - destination city
	 * @param oneWayFlightsPage - instance of one way flights page
	 */
	public void doCommonOperation(String from, String to, FlightsOneWay oneWayFlightsPage) {
		oneWayFlightsPage.clickFlightsTab();
		oneWayFlightsPage.clickOneWay();
		oneWayFlightsPage.provideOriginCity(from);
		oneWayFlightsPage.provideDestCity(to);
	}

	/** 2nd
	 * Test case to search one way flights with a future date without any traveller details 
	 * 
	 * @param from - source city
	 * @param to - destination city
	 * @param date - date of journey - future date
	 */
	@Test(dataProvider = "searchFlightsPosDate", dataProviderClass = DataProviderClassFlights.class)
	public void isOneWayFlightSearchSuccessWithDate(String from, String to){
		FlightsOneWay oneWaySearchPage = new FlightsOneWay(driver);
		doCommonOperation(from, to, oneWaySearchPage);
		String futureDate = GeneralUtility.getAfutureDate();
		oneWaySearchPage.provideDepartDate(futureDate);
		FlightsResultPage flOneWayResult = oneWaySearchPage.searchFlights();
		Assert.assertTrue(flOneWayResult.isFlightResultsAppearing());
	}
	
	/** 1st
	 * Test case to search one way flights with the default populated date
	 * 
	 * @param from
	 * @param to
	 * @param date
	 * @throws InterruptedException
	 */
	@Test(dataProvider = "searchFlightsDefaultDatePos", dataProviderClass = DataProviderClassFlights.class)
	public void isOneWayFlightSearchSuccess(String from, String to) {
		FlightsOneWay oneWaySearchPage = new FlightsOneWay(driver);
		doCommonOperation(from, to, oneWaySearchPage);
		FlightsResultPage flOneWayResult = oneWaySearchPage.searchFlights();
		Assert.assertTrue(flOneWayResult.isFlightResultsAppearing());
	}
	
	/*** 4th
	 * Test case to search one way flights with only traveller information - every other optional field passed with default value
	 * @param from
	 * @param to
	 * @param adults
	 * @param children
	 * @param infants
	 */
	@Test(dataProvider = "searchOneWayFlightsPosTravellers", dataProviderClass = com.expedia.ExpediaMavenFramework.dataProviders.DataProviderClassFlights.class)
	public void isOneWayFlightsSearchSuccessWithTravellers(String from, String to, String adults, String children, String childrenAges, String infants, String infantAges, String infantSitting) {
		FlightsOneWay oneWaySearchPage = new FlightsOneWay(driver);
		doCommonOperation(from, to, oneWaySearchPage);
		oneWaySearchPage.selectTravellersWithAllOptions(adults, children, childrenAges, infants, infantAges, infantSitting, null);
		FlightsResultPage flOneWayResult = oneWaySearchPage.searchFlights();
		Assert.assertTrue(flOneWayResult.isFlightResultsAppearing());
	}
	
	/*** 3rd
	 * Test case to search one way flights with traveller information and date - every other traveller optional field passed with default value
	 * @param from
	 * @param to
	 * @param adults
	 * @param children
	 * @param infants
	 * @param flightClass
	 */
	@Test(dataProvider = "searchOneWayFlightsPosTrDt", dataProviderClass = com.expedia.ExpediaMavenFramework.dataProviders.DataProviderClassFlights.class)
	public void isOneWayFlightsSearchSuccessWithAllOptions(String from, String to, String adults, String children, String childrenAges, String infants, String infantAges, String infantSitting, String flightClass) {
		FlightsOneWay oneWaySearchPage = new FlightsOneWay(driver);
		doCommonOperation(from, to, oneWaySearchPage);
		String futureDate = GeneralUtility.getAfutureDate();
		oneWaySearchPage.provideDepartDate(futureDate);
		oneWaySearchPage.selectTravellersWithAllOptions(adults, children, childrenAges, infants, infantAges, infantSitting, flightClass);
		oneWaySearchPage.selectFlightClass(flightClass);
		FlightsResultPage flOneWayResult = oneWaySearchPage.searchFlights();
		Assert.assertTrue(flOneWayResult.isFlightResultsAppearing());
	}

}