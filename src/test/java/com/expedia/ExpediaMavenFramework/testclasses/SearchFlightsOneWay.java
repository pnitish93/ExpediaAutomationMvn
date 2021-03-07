package com.expedia.ExpediaMavenFramework.testclasses;

import org.testng.annotations.Test;

import com.expedia.ExpediaMavenFramework.dataProviders.DataProviderClassOneWayFlights;
import com.expedia.ExpediaMavenFramework.pageclasses.FlightsOneWay;
import com.expedia.ExpediaMavenFramework.pageclasses.FlightsResultPage;
import com.expedia.ExpediaMavenFramework.testclasses.basetestclass.TestConfig;

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

	/**
	 * Test case to search one way flights with a future date without any traveller details 
	 * 
	 * @param from - source city
	 * @param to - destination city
	 * @param date - date of journey - future date
	 */
	@Test(dataProvider = "searchOneWayFlightsPosDate", dataProviderClass = DataProviderClassOneWayFlights.class, enabled = false)
	public void isOneWayFlightSearchSuccessWithDate(String from, String to, String date){
		FlightsOneWay oneWaySearchPage = new FlightsOneWay(driver);
		doCommonOperation(from, to, oneWaySearchPage);
		oneWaySearchPage.provideDepartDate(date);
		FlightsResultPage flOneWayResult = oneWaySearchPage.searchFlights();
		Assert.assertTrue(flOneWayResult.isFlightResultsAppearing());
	}
	
	/**
	 * Test case to search one way flights with the default populated date
	 * 
	 * @param from
	 * @param to
	 * @param date
	 * @throws InterruptedException
	 */
	@Test(dataProvider = "searchOneWayFlightsPos", dataProviderClass = DataProviderClassOneWayFlights.class)
	public void isOneWayFlightSearchSuccess(String from, String to) {
		FlightsOneWay oneWaySearchPage = new FlightsOneWay(driver);
		doCommonOperation(from, to, oneWaySearchPage);
		FlightsResultPage flOneWayResult = oneWaySearchPage.searchFlights();
		Assert.assertTrue(flOneWayResult.isFlightResultsAppearing());
	}

}