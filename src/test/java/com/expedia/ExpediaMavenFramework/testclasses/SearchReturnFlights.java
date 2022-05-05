package com.expedia.ExpediaMavenFramework.testclasses;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.expedia.ExpediaMavenFramework.dataProviders.DataProviderClassFlights;
import com.expedia.ExpediaMavenFramework.pageclasses.FlightsResultPage;
import com.expedia.ExpediaMavenFramework.pageclasses.FlightsReturn;
import com.expedia.ExpediaMavenFramework.testclasses.basetestclass.TestConfig;

public class SearchReturnFlights extends TestConfig {

	public void doCommonOperations(FlightsReturn returnObject, String source, String destination) {
		returnObject.clickOnFlightsTab();
		returnObject.clickOnReturnTab();
		returnObject.provideSource(source);
		returnObject.provideDestination(destination);
	}
	
	@Test(dataProvider = "searchFlightsDefaultDatePos", dataProviderClass = DataProviderClassFlights.class)
	public void searchReturnFlightsDefaultDates(String sourceCode, String destinationCode) {
		FlightsReturn returnWay = new FlightsReturn(driver);
		doCommonOperations(returnWay, sourceCode, destinationCode);
		FlightsResultPage resultPage = returnWay.clickSearchButton();
		Assert.assertTrue(resultPage.isFlightResultsAppearing());
	}
}
