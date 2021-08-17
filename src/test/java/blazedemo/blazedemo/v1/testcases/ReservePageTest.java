package blazedemo.blazedemo.v1.testcases;

import java.util.NoSuchElementException;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import blazedemo.blazedemo.v1.base.BaseClass;
import blazedemo.blazedemo.v1.pages.HomePage;
import blazedemo.blazedemo.v1.pages.PurchasePage;
import blazedemo.blazedemo.v1.pages.ReservationPage;
import blazedemo.blazedemo.v1.testData.DataProviders;
import blazedemo.blazedemo.v1.utilities.Screenshot;

import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.Logger;

public class ReservePageTest extends BaseClass {
	public static Logger log;
	HomePage page;
	ReservationPage rpage;

	@BeforeClass
	public void initPage() {
		page = new HomePage(driver);
		rpage = new ReservationPage(driver);
		PropertyConfigurator.configure("log4j.properties");
		log = Logger.getLogger(NavigationEndToEnd.class.getName());

	}

	/**
	 * Check if user is able to navigate to purchase page
	 * 
	 * @flight flight number has to be passed
	 */
	@Test(priority = 0, dataProvider = "getFlightNumbers", dataProviderClass = DataProviders.class, groups = {
			"sanity" })
	public void checkNavigationToPurchasePage(String departureCity, String destinationCity) {
		try {
			log.debug("TC :checkNavigationToPurchasePage");
			driver.get(baseURL);
			page.navigateToReservePage(departureCity, destinationCity);
			rpage.chooseFlight(testFlight);
			sassert.assertEquals(getPageLoadStatus(driver), true);
			String title = driver.getTitle();
			if (title.contains("BlazeDemo Purchase")) {
				Assert.assertTrue(true);
			} else {
				Assert.assertTrue(false);
			}
			PurchasePage page = new PurchasePage(driver);
			sassert.assertEquals(page.checkPageElements(), true);

		} catch (NoSuchElementException ex) {
			ex.printStackTrace();
			Screenshot.takeScreenshot("checkNavigationToPurchasePage");
		} catch (Exception ex) {
			ex.printStackTrace();
			log.debug("TC :checkNavigationToPurchasePage" + ex.toString());
		}

	}

	/**
	 * Check if we can enter all the customer details
	 * 
	 * @param userdetails object passed from provider class
	 */
	@Test(priority = 1, dependsOnMethods = "checkNavigationToPurchasePage", dataProvider = "getUserInfo", dataProviderClass = DataProvider.class)
	public void checkEnteringCustomerDetails(Object userdetails) {
		try {
			log.debug("TC :checkEnteringCustomerDetails");
			PurchasePage ppage = new PurchasePage(driver);

			ppage.navigateToConfirmationPage(userdetails);
			String title = driver.getTitle();
			sassert.assertEquals(getPageLoadStatus(driver), true);
			if (title.contains("BlazeDemo Confirmation")) {
				Assert.assertTrue(true);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			log.debug("TC :checkEnteringCustomerDetails" + ex.toString());
		}

	}

	/**
	 * check if we can navigate to next page and come back
	 */
	@Test(priority = 2, dependsOnMethods = "checkEnteringCustomerDetails")
	public void checkForConfirmationPage() {

		log.debug("TC :checkForConfirmationPage");

		sassert.assertTrue(cpage.isBookingIdDisplayed());
		sassert.assertTrue(cpage.isAmountDisplayed());
		sassert.assertTrue(cpage.isAuthCodeDisplayed());
		sassert.assertTrue(cpage.isBookingDate());
		sassert.assertTrue(cpage.isCardNumberDisplayed());
		sassert.assertTrue(cpage.isExpirationDateDisplayed());
		sassert.assertTrue(cpage.isStatusDisplayed());

	}

}
