package com.softserve.inc.googleSearch.pages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import com.softserve.inc.googleSearch.tools.ContextVisible;
import com.softserve.inc.googleSearch.tools.IBrowser;
import com.softserve.inc.googleSearch.tools.WebDriverUtils;

public class GoogleSearchPage {

	static Logger logger = Logger.getLogger(GoogleSearchPage.class);
	private Map<String, String> googleSearch = new HashMap();
	private WebElement searchField;
	private WebElement nextLink;
	private WebElement allRes;

	public GoogleSearchPage() {
		logger.info("GoogleSearchPage constructor");
		this.searchField = ContextVisible.get().getPresentWebElement(
				By.id("lst-ib"));
	}

	public static GoogleSearchPage load(IBrowser browser, String url) {
		WebDriverUtils.get(browser).load(url);
		logger.info("GoogleSearchPage load method");
		return new GoogleSearchPage();
	}

	public void searchQuery(String query) {
		searchField.sendKeys(query);
		searchField.sendKeys(Keys.RETURN);
	}

	/**
	 * 
	 * @param count
	 *            describe how mush resuls shoud be added to MAP
	 */
	private void getTitleAndDescriptionFromResult(int count) {

		String title = null;
		String description = null;
		List<WebElement> results = new ArrayList<WebElement>();

		WebElement allRes = ContextVisible.get().getVisibleWebElement(
				By.id("ires")); // data from all page
		List<WebElement> elements = allRes.findElements(By.className("srg"));
		for (WebElement el : elements) {
			results.addAll(el.findElements(By.className("g")));
		}

		for (WebElement res : results) {
			title = res.findElement(By.className("r")).getText();
			description = res.findElement(By.className("st")).getText();
			googleSearch.put(title, description);
			
			logger.info(googleSearch.size());
			if (googleSearch.size() == count)
				break;
		}
	}

	private void clickNextPage() {
		ContextVisible.get().getPresentWebElement(By.id("pnnext")).click();
	}

	public void addFixedNumberResultsToMap(int count) {
		while (googleSearch.size() < count) {
			getTitleAndDescriptionFromResult(count);
			if (googleSearch.size() < count) {
				clickNextPage();
			}
		}
	}

	public void printResult() {
		for (String key : googleSearch.keySet()) {
			System.out.println("-----------\n" + key + " \n"
					+ googleSearch.get(key));
		}
	}

	/**
	 * 
	 * @param mathc string wich are finding in result
	 * @return number of found words in the result
	 */
	private int countResult(String mathc) {
		int count = 0;
		for (String key : googleSearch.keySet()) {
			
			    Pattern p = Pattern.compile("\\b+"+ mathc + "+", Pattern.CASE_INSENSITIVE);
			    Matcher m = p.matcher(key);
			    
			    while(m.find()) count++;
			    m = p.matcher(googleSearch.get(key));
			    while(m.find()) count++;
		}
		return count;
	}
	
	public boolean isResultMoreThan(int expected, String mathc) {
		if (countResult(mathc) > expected) {
			return true;
		} else {
			return false;
		}
	}
	
}
