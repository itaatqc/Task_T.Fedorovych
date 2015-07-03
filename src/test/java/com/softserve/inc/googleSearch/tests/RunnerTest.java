package com.softserve.inc.googleSearch.tests;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import com.softserve.inc.googleSearch.pages.GoogleSearchPage;
import com.softserve.inc.googleSearch.tools.BrowserRepository;
import com.softserve.inc.googleSearch.tools.WebDriverUtils;

public class RunnerTest {
	static Logger logger =Logger.getLogger(RunnerTest.class);
	private static final String URL = "http://www.google.com";
	private static final String SEARCH_QUERY = "banana";
	private static final int RESULT_COUNT = 10;
	private static final int RESULT_WORD = 20; 
	
	

	@Test
	public void testBananaCount(){
		GoogleSearchPage googleSearchPage = GoogleSearchPage.load(BrowserRepository.getFirefoxByTemporaryProfile(), URL);
		
		googleSearchPage.searchText(SEARCH_QUERY);
		googleSearchPage.addResultsToMap(RESULT_COUNT);

		Assert.assertTrue(googleSearchPage.isResultMoreThan(RESULT_WORD, SEARCH_QUERY)) ;
		
	}
	
	@AfterTest
	public void tearDown(){
		WebDriverUtils.get().stop();
	}
}
