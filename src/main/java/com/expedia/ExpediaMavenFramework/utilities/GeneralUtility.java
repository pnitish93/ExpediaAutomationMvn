package com.expedia.ExpediaMavenFramework.utilities;

public class GeneralUtility {
	public static void doHardWaitFor(long timeInMilliSecs) {
		try{
			Thread.sleep(timeInMilliSecs);
		}
		catch(InterruptedException e) {
			System.out.println("An exception occured. Please review your code");
			e.printStackTrace();
		}
	}
	
	/**
	 * Returns the numeric equivalent of month like 1 for Jan, 2 for Feb
	 * @param mmm - Month name in mmm format (like for January, its Jan)
	 * @return - The month number starting from 0
	 */
	public static int returnMonthNumber(String mmm) {
		int monthNum;
		switch(mmm) {
		case "Jan":
			monthNum = 0;
			break;
		case "Feb":
			monthNum = 1;
			break;
		case "Mar":
			monthNum = 2;
			break;
		case "Apr":
			monthNum = 3;
			break;
		case "May":
			monthNum = 4;
			break;
		case "Jun":
			monthNum = 5;
			break;
		case "Jul":
			monthNum = 6;
			break;
		case "Aug":
			monthNum = 7;
			break;
		case "Sep":
			monthNum = 8;
			break;
		case "Oct":
			monthNum = 9;
			break;
		case "Nov":
			monthNum = 10;
			break;
		case "Dec":
			monthNum = 11;
			break;
		default :
			monthNum = -1;
		}
		return monthNum;
	}
	
	/**
	 * Utility method to return string without any leading or trailing spaces
	 * @param str - original string
	 * @return - string value without any leading and trailing spaces
	 */
	public static String removeLeadingTrailingSpaces(String str) {
		return str.trim();
	}
}
