package com.expedia.ExpediaMavenFramework.dataProviders;

import org.testng.annotations.DataProvider;

import com.expedia.ExpediaMavenFramework.constants.Constants;
import com.expedia.ExpediaMavenFramework.utilities.ExcelUtility;

public class DataProviderClassFlights {
	@DataProvider(name = "searchInputs")
	public Object[][] sendInputs() {
		ExcelUtility.setExcelPath(Constants.path+"\\"+Constants.fileName, "FlightsWithHotelsPositive");
		Object[][] objectList = ExcelUtility.getCellData("OneWayHotels");
		return objectList;
	}
	@DataProvider(name = "searchOneWayFlightsPosTravellers")
	public Object[][] searchOneWayFlightsTravellersPos() {
		ExcelUtility.setExcelPath(Constants.path+"\\"+Constants.fileName, "OneWayFlightsWithTravellers");
		Object[][] objectList = ExcelUtility.getCellData("OneWayTravellers");
		return objectList;
	}
	@DataProvider(name = "searchFlightsDefaultDatePos")
	public Object[][] searchFlightsDefaultDatePos(){
		ExcelUtility.setExcelPath(Constants.path+"\\"+Constants.fileName, "FlightsPosDefaultDate");
		Object[][] objectList = ExcelUtility.getCellData("FlightsDefaultDtPos");
		return objectList;
	}
	@DataProvider(name = "searchOneWayFlightsPosDate")
	public Object[][] searchOneWayFlightsPosDate(){
		ExcelUtility.setExcelPath(Constants.path+"\\"+Constants.fileName, "OneWayFlightsPosDate");
		Object[][] objectList = ExcelUtility.getCellData("OneWayFlightsPosDate");
		return objectList;
	}
	@DataProvider(name = "searchInputsFlightsAge")
	public Object[][] sendInputsFlightsAge(){
		ExcelUtility.setExcelPath(Constants.path+"\\"+Constants.fileName, "FlightsOnlyPosWAge");
		Object[][] objectList = ExcelUtility.getCellData("OneWayAge");
		return objectList;
	}
	@DataProvider(name = "searchOneWayFlightsPosTrDt")
	public Object[][] searchOneWayFlightsTravellersDtPos() {
		ExcelUtility.setExcelPath(Constants.path+"\\"+Constants.fileName, "OneWayFlightsPosAllOptions");
		Object[][] objectList = ExcelUtility.getCellData("OneWayFlightsTravellersDate");
		return objectList;
	}
}
