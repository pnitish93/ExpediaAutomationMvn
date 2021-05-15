package com.expedia.ExpediaMavenFramework.pageclasses.basePageClass;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import com.expedia.ExpediaMavenFramework.utilities.GeneralUtility;

public class CustomDriver {
	WebDriver driver;
	JavascriptExecutor jsExe;
	public static Logger logCD = LogManager.getLogger(CustomDriver.class.getName());
	List<Integer> displayedMonthNumbers;
	List<Integer> displayedYears;

	public CustomDriver(WebDriver driver) {
		this.driver = driver;
		jsExe = (JavascriptExecutor)driver;
	}

	/***
	 * Returns an appropriate by type to find an element if required
	 * 
	 * @param byType  - type of locator to be used passed as String
	 * @param locator - locator value passed as String
	 * @return
	 */
	public By getByType(String byType, String locator) {
		By byValue = null;
		try {
			if (byType.equalsIgnoreCase("id")) {
				byValue = By.id(locator);
			} else if (byType.equalsIgnoreCase("xpath")) {
				byValue = By.xpath(locator);
			} else if (byType.equalsIgnoreCase("classname")) {
				byValue = By.className(locator);
			} else if (byType.equalsIgnoreCase("cssSelector")) {
				byValue = By.cssSelector(locator);
			} else if (byType.equalsIgnoreCase("linktext")) {
				byValue = By.linkText(locator);
			} else if (byType.equalsIgnoreCase("partialLinkText")) {
				byValue = By.partialLinkText(locator);
			} else if (byType.equalsIgnoreCase("tagname")) {
				byValue = By.tagName(locator);
			} else if (byType.equalsIgnoreCase("name")) {
				byValue = By.name(locator);
			} else {
				logCD.error("Not a valid locator.");
			}
		} catch (Exception e) {
			logCD.error(e.getMessage());
		}
		return byValue;
	}

	/**
	 * Finds webElement if required - combinedLocator sent in form of
	 * locatorType=>locator
	 * 
	 * @param combinedLocator - locatorType=>locatorValue
	 * @return
	 */
	public WebElement getWebElement(String combinedLocator) {
		WebElement webEle = null;
		String[] strArr = combinedLocator.split("=>");
		String locatorType = strArr[0];
		String locator = strArr[1];
		try {
			webEle = driver.findElement(getByType(locatorType, locator));
		} catch (Exception e) {
			logCD.error(e.getMessage());
		}
		return webEle;
	}

	/**
	 * Finds list of webElements if required - combinedLocator sent in form of
	 * locatorType=>locator
	 * 
	 * @param combinedLocator - locatorType=>locatorValue
	 * @return
	 */
	public List<WebElement> getWebElements(String combinedLocator) {
		List<WebElement> elements = null;
		String[] strArr = combinedLocator.split("=>");
		String locatorType = strArr[0];
		String locator = strArr[1];
		elements = driver.findElements(getByType(locatorType, locator));
		return elements;
	}

	/**
	 * Used to click an element and wait a specified time
	 * 
	 * @param element             - web element to be clicked on.
	 * @param waitTimeInMilliSecs - waiting time.
	 */
	public void clickAndWait(WebElement element, long waitTimeInMilliSecs) {
		try {
			element.click();
			if (waitTimeInMilliSecs != 0) {
				com.expedia.ExpediaMavenFramework.utilities.GeneralUtility.doHardWaitFor(waitTimeInMilliSecs);
			}
		} catch (Exception e) {
			logCD.error("Cannot click on the element.");
		}

	}

	/**
	 * Overloaded method - Used to click an without any wait
	 * 
	 * @param element - web element to be clicked on.
	 */
	public void clickWithoutWait(WebElement element) {
		clickAndWait(element, 0);
		logCD.info("Clicked on an element");
	}

	/**
	 * Checks if an element is enabled and if so, clicks on the element
	 * 
	 * @param ele - WebElement to be clicked
	 */
	public void clickIfEnabled(WebElement ele) {
		if (checkIfElementEnabled(ele)) {
			clickWithoutWait(ele);
		} else if (!checkIfElementEnabled(ele)) {
			logCD.error("Element is disabled and hence cannot be clicked.");
		}
	}

	/**
	 * Checks if an element is enabled and if so, clicks on the element
	 * 
	 * @param locator - Locator of the WebElement to be clicked
	 */
	public void clickIfEnabled(String locator) {
		WebElement ele = getWebElement(locator);
		clickIfEnabled(ele);
	}

	/**
	 * Checks if the provided web element is enabled
	 * 
	 * @param ele - WebElement to be checked for being enabled
	 * @return - true if web element is enabled else false
	 */
	public boolean checkIfElementEnabled(WebElement ele) {
		return ele.isEnabled();
	}

	/**
	 * Checks if the provided webElement from provided locator is enabled
	 * 
	 * @param eleLocator - Locator of the WebElement to be checked for being enabled
	 * @return - true if web element of the provided locator is enabled else false
	 */
	public boolean checkIfElementEnabled(String eleLocator) {
		WebElement ele = getWebElement(eleLocator);
		return checkIfElementEnabled(ele);
	}

	/**
	 * Used to send text to an element and clear it if required
	 * 
	 * @param element     - web element where text is to be entered.
	 * @param wantToClear - if true, then clears the field.
	 * @param text        - text to be typed.
	 */
	public void sendTextToElement(WebElement element, boolean wantToClear, String text) {
		try {
			if (wantToClear) {
				element.clear();
			}
			element.sendKeys(text);
			logCD.info("Text entered to element.");
		} catch (Exception e) {
			logCD.error("Cannot type text in the element.");
		}

	}

	/**
	 * Scrolling down one time with the specified measure using java script executor
	 * 
	 * @param measureScrollDown - amount by which to scroll down.
	 */
	public void scrollDownBy(long measureScrollDown) {
		long absValue = Math.abs(measureScrollDown);
		jsExe.executeScript("window.scrollBy(0, " + absValue + ");");
	}

	/**
	 * Scrolling up one time with the specified measure using java script executor
	 * 
	 * @param measureScrollDown - amount by which to scroll up.
	 */
	public void scrollUpBy(long measureScrollUp) {
		long absValue = Math.abs(measureScrollUp);
		measureScrollUp = 0 - absValue;
		jsExe.executeScript("window.scrollBy(0, " + measureScrollUp + ");");
	}
	
	/***
	 * Method to scroll to an element using JavascriptExecutor
	 * @param element
	 */
	public void scrollToTheElement(WebElement element) {
		jsExe.executeScript("arguments[0].scrollIntoView(true);",element);
	}

	/**
	 * Sending special keystrokes like
	 * 
	 * @param element
	 * @param keyStrokes
	 */
	public void sendSpecialKeyStrokes(WebElement element, Keys keyStrokes) {
		try {
			element.sendKeys(keyStrokes);
		} catch (Exception e) {
			logCD.error("Special keystrokes cannot be sent to the element");
		}
	}

	/***
	 * Finds a single element for the date
	 * 
	 * @param date - string specifying the date in 'dd mmm yyyy' format
	 * @return
	 */
	public List<WebElement> findDateElements(String date) {
		String xpath = "//button[contains(@aria-label, '" + date + "')]";
		List<WebElement> elements = getWebElements("xpath=>" + xpath);
		GeneralUtility.doHardWaitFor(1000);
		return elements;
	}

	/**
	 * Updates the list of displayed months in the UI
	 */
	public void updateDisplayedListDetails() {
		List<WebElement> monthsDisplayed = getWebElements(
				"xpath=>//h2[@class='uitk-date-picker-month-name uitk-type-medium']");
		displayedMonthNumbers = new ArrayList<Integer>();
		displayedYears = new ArrayList<Integer>();
		for (int i = 0; i < monthsDisplayed.size(); i++) {
			String[] eleArr = monthsDisplayed.get(i).getText().split(" ");
			displayedYears.add(Integer.parseInt(eleArr[1]));
			displayedMonthNumbers
					.add(GeneralUtility.returnMonthNumber(eleArr[0].substring(0, 3)));
		}
	}

	/***
	 * Clicks on the date element and goes to next page if required
	 * 
	 * @param date - string specifying the date in 'dd mmm yyyy' format
	 */
	public void clickDateElementIfExists(String date) {
		scrollDownBy(250);
		WebElement doneButton = getWebElement("xpath=>//span[text()='Done']");
		WebElement nextButton = getWebElement("xpath=>//button[contains(@class, 'uitk-button-paging')][position()=2]");
		WebElement prevButton = getWebElement("xpath=>//button[contains(@class, 'uitk-button-paging')][position()=1]");
		String[] dateEles = date.split(" ");
		String desiredMonth = dateEles[1];
		int desiredMonthNum = -1;
		try {
			desiredMonthNum = GeneralUtility.returnMonthNumber(desiredMonth);
			if (desiredMonthNum == -1)
				throw new Exception();
		} catch (Exception e) {
			logCD.error("Proper month not provided.");
		}
		int desiredYear = Integer.parseInt(dateEles[2]);
		List<WebElement> dateElements = findDateElements(date);
		if (dateElements.size() != 0) {
			clickWithoutWait(dateElements.get(0));
			clickWithoutWait(doneButton);
			logCD.info("Date element found and clicked on");
		} 
		else {
			do {
				updateDisplayedListDetails();
				if (desiredMonthNum > displayedMonthNumbers.get(1) && desiredYear == displayedYears.get(1)) {
					clickIfEnabled(nextButton);
				} else if (desiredMonthNum < displayedMonthNumbers.get(0) && desiredYear == displayedYears.get(0)) {
					clickIfEnabled(prevButton);
				} else if (desiredMonthNum < displayedMonthNumbers.get(1) && desiredYear > displayedYears.get(1)) {
					clickIfEnabled(nextButton);
				} else if (desiredMonthNum < displayedMonthNumbers.get(0) && desiredYear < displayedYears.get(0)) {
					clickIfEnabled(prevButton);
				}
				dateElements = findDateElements(date);
				GeneralUtility.doHardWaitFor(1000);
				if (dateElements.size() != 0) {
					clickWithoutWait(dateElements.get(0));
					clickWithoutWait(doneButton);
					break;
				}
			} while (dateElements.size() == 0);
			logCD.info("Providing departure date");
		}
	}
	
	/***
	 * Returns an attribute value for a WebElement
	 * @param element
	 * @param attributeName
	 * @return
	 */
	public String getAnAttribute(WebElement element, String attributeName) {
		String attributeValue = element.getAttribute(attributeName);
		return attributeValue;
	}
	
	/***
	 * Method to adjust values using increment or decrement buttons
	 * @param incrementButton
	 * @param decrementButton
	 * @param currentValue
	 * @param desiredValue
	 */
	public void adjustValuesUsingButtons(WebElement incrementButton, WebElement decrementButton, int currentValue, int desiredValue) {
		int difference = desiredValue - currentValue;
		int absDifference = Math.abs(difference);
		if(difference < 0) {
			for(int i = 0; i < absDifference; i++) {
				clickAndWait(decrementButton, 2);
			}
		}
		if(difference > 0) {
			for(int i = 0; i < absDifference; i++) {
				clickAndWait(incrementButton, 2);
			}
		}
	}
	
	/***
	 * Method to select dropdown value using visible text
	 * @param dropdownElement
	 * @param value
	 */
	public void selectDropDownValueByVisibleText(WebElement dropdownElement, String value) {
		Select dropDownSelect = new Select(dropdownElement);
		dropDownSelect.selectByVisibleText(GeneralUtility.removeLeadingTrailingSpaces(value));
	}
	
	public void clickRadioIfNotSelectedAndWait(WebElement radioElement, long waitInMillsecs) {
		if(!radioElement.isSelected()) {
			clickAndWait(radioElement, waitInMillsecs);
		}
	}
	
	public void clickRadioIfNotSelectedWithoutWait(WebElement radioElement) {
		if(!radioElement.isSelected()) {
			clickWithoutWait(radioElement);
		}
	}
}
