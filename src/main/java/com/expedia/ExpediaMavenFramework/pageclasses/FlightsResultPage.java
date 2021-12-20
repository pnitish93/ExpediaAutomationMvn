package com.expedia.ExpediaMavenFramework.pageclasses;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class FlightsResultPage {
	private static WebDriver driver;
	
	@FindBy(xpath = "//span[contains(text(), 'Choose departing flight')]")
	private static WebElement flightsResultText;
	
	public FlightsResultPage(WebDriver dr) {
		this.driver = dr;
		PageFactory.initElements(dr, this);
	}
	
	public boolean isFlightResultsAppearing() {
		return flightsResultText.isDisplayed();
	}
	
}
