package com.softserve.inc.googleSearch.tools;

import org.openqa.selenium.WebDriver;

public interface IBrowser {

	WebDriver getWebDriver();

	String getWebDriverName();

	void quit();

}
