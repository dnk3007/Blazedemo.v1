package blazedemo.blazedemo.v1.testcases;

import java.util.NoSuchElementException;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import blazedemo.blazedemo.v1.base.BaseClass;
import blazedemo.blazedemo.v1.pages.HomePage;
import blazedemo.blazedemo.v1.testData.DataProviders;
import blazedemo.blazedemo.v1.utilities.Screenshot;

import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.Logger;

public class HomePageTest extends BaseClass {



	@BeforeClass
	public void initPage() {
		PropertyConfigurator.configure("log4j.properties");
		log = Logger.getLogger(NavigationEndToEnd.class.getName());
		page = new HomePage(driver);

	}

	@BeforeMethod
	public void launchBaseUrl() {
		driver.get(baseURL);
	}

	/**
	 * Verify Home Page UI
	 */
	@Test(priority = 0, groups = { "sanity" })
	public void verifyPageUI() {
		log.debug("TC :verifyPageUI ");
		try {
			boolean pageLoadStatus = getPageLoadStatus(driver);
			sassert.assertEquals(pageLoadStatus, true);
			sassert.assertEquals(page.checkPageElements(), true);
		} catch (NoSuchElementException ex) {
			log.debug("Element Not Found on WebPage");
			Screenshot.takeScreenshot("verifyPageUI");
		} catch (Exception ex) {
			log.debug("TC :homePageWebElemetsCheck :" + ex.toString());

		}

	}

	/**
	 * Verify user navigation to departure city
	 * 
	 * @param depaturecity
	 * @param destination
	 */
	@Test(priority = 1, groups = {
			"sanity" }, dataProvider = "getDataDepartureCity", dataProviderClass = DataProviders.class)
	public void verifyDepartureAndDestinationSelection(String depaturecity, String destination) {
		log.debug("TC :verifyDepartureAndDestinationSelection ");
		try {
			boolean pageLoadStatus = getPageLoadStatus(driver);
			sassert.assertEquals(pageLoadStatus, true);
			page.navigateToReservePage(depaturecity, destination);
			driver.navigate().back();
			sassert.assertEquals(driver.getTitle(), "BlazeDemo - reserve");
		} catch (NoSuchElementException ex) {
			log.debug("Element Not Found on WebPage");
			Screenshot.takeScreenshot("verifyDepartureAndDestinationSelection");
		} catch (Exception ex) {
			log.debug("TC :verifyDepartureAndDestinationSelection :" + ex.toString());

		}
	}

	/**
	 * Check user is able to navigate upon selection. Verify Page load status after
	 * navigation
	 */
	@Test(priority = 2, dataProvider = "getDataDepartureCity", dataProviderClass = DataProviders.class)
	public void checkNavigationToReservePage(String depaturecity, String destination) {
		log.debug("TC :checkNavigationToReservePage ");
		try {

			boolean pageLoadStatus = getPageLoadStatus(driver);
			sassert.assertEquals(pageLoadStatus, true);
			page.navigateToReservePage(depaturecity, destination);
			String title = driver.getTitle();
			sassert.assertEquals(title, homePageTitle);
			sassert.assertEquals(getPageLoadStatus(driver), true);
		} catch (NoSuchElementException ex) {
			log.debug("Element Not Found on WebPage");
			Screenshot.takeScreenshot("verifyDepartureAndDestinationSelection");
		} catch (Exception ex) {
			ex.printStackTrace();
			log.debug("TC :checkNavigationToReservePage" + ex.toString());
		}

	}

	/**
	 * Verify navigation to destination week
	 */
	@Test
	public void checkNavigationToDestinationOfTheWeek() {
		log.debug("TC :checkNavigationAllCountries ");
		sassert.assertTrue(page.navigateToDestOfWeek());
	}

	/**
	 * Verify functionality of selecting various countries and navigation to reserve
	 * page
	 * 
	 * @param fromCountry
	 * @param toCountry
	 */
	@Test(priority = 3, dataProvider = "getCountriesMap", dataProviderClass = DataProviders.class)
	public void checkNavigationAllCountries(String fromCountry, String toCountry) {
		log.debug("TC :checkNavigationAllCountries ");
		try {
			if (driver.getCurrentUrl() != baseURL)
				driver.get(baseURL);
			boolean pageLoadStatus = getPageLoadStatus(driver);
			sassert.assertEquals(pageLoadStatus, true);
			page.navigateToReservePage(fromCountry, toCountry);
			String title = driver.getTitle();
			sassert.assertEquals(title, reservePageTitle);
			boolean nextpageLoadStatus = getPageLoadStatus(driver);
			sassert.assertEquals(nextpageLoadStatus, true);
		} catch (NoSuchElementException ex) {
			ex.printStackTrace();
		} catch (Exception ex) {
			log.debug("TC :checkNavigationAllCountries" + ex.toString());
		}

	}

}
