package com.expedia.ExpediaMavenFramework.testclasses;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.expedia.ExpediaMavenFramework.dataProviders.DataProviderClassFlights;
import com.expedia.ExpediaMavenFramework.pageclasses.FlightsResultPage;
import com.expedia.ExpediaMavenFramework.pageclasses.FlightsReturn;
import com.expedia.ExpediaMavenFramework.testclasses.basetestclass.TestConfig;
import com.expedia.ExpediaMavenFramework.utilities.GeneralUtility;

public class SearchReturnFlights extends TestConfig {

	public void doCommonOperations(FlightsReturn returnObject, String source, String destination) {
		returnObject.clickOnFlightsTab();
		returnObject.clickOnReturnTab();
		returnObject.provideSource(source);
		returnObject.provideDestination(destination);
	}
	
	@Test(dataProvider = "searchFlightsDefaultDatePos", dataProviderClass = DataProviderClassFlights.class, enabled = false)
	public void searchReturnFlightsDefaultDates(String sourceCode, String destinationCode) {
		FlightsReturn returnWay = new FlightsReturn(driver);
		doCommonOperations(returnWay, sourceCode, destinationCode);
		FlightsResultPage resultPage = returnWay.clickSearchButton();
		Assert.assertTrue(resultPage.isFlightResultsAppearing());
	}
	
	@Test(dataProvider = "searchFlightsPosDate", dataProviderClass = DataProviderClassFlights.class)
	public void searchReturnFlightsWithDates(String sourceCode, String destinationCode) {
		FlightsReturn returnWay = new FlightsReturn(driver);
		doCommonOperations(returnWay, sourceCode, destinationCode);
		String[] dates = GeneralUtility.getTwoFutureDates();
		returnWay.provideDepartDate(dates[0]);
		returnWay.provideReturnDate(dates[1]);
		FlightsResultPage resultPage = returnWay.clickSearchButton();
		Assert.assertTrue(resultPage.isFlightResultsAppearing());
	}
}
