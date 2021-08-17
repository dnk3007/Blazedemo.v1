package blazedemo.blazedemo.v1.testcases;

import java.util.HashMap;
import java.util.NoSuchElementException;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import blazedemo.blazedemo.v1.base.BaseClass;
import blazedemo.blazedemo.v1.pages.ConfirmationPage;
import blazedemo.blazedemo.v1.pages.HomePage;
import blazedemo.blazedemo.v1.pages.PurchasePage;
import blazedemo.blazedemo.v1.pages.ReservationPage;
import blazedemo.blazedemo.v1.testData.DataProviders;
import blazedemo.blazedemo.v1.utilities.Screenshot;

import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.Logger;

/**
 * End to End Test for booking flight ticket
 * 
 * @author Naveen D
 *
 */
public class NavigationEndToEnd extends BaseClass {
	public static Logger log;
	HomePage page;
	ReservationPage rpage;
	PurchasePage ppage;
	ConfirmationPage cpage;

	@BeforeClass
	public void init() {
		page = new HomePage(driver);
		rpage = new ReservationPage(driver);
		ppage = new PurchasePage(driver);
		cpage = new ConfirmationPage(driver);
		PropertyConfigurator.configure("log4j.properties");
		log = Logger.getLogger(NavigationEndToEnd.class.getName());

	}

	/**
	 * Check user is able to navigate upon selection of city navigation
	 */
	@Test(priority = 2, dataProvider = "getDataDepartureCity", dataProviderClass = DataProviders.class)
	public void checkNavigationToReservePage(String depaturecity, String destination) {
		log.debug("TC :checkNavigationToReservePage ");
		try {

			boolean pageLoadStatus = getPageLoadStatus(driver);
			sassert.assertEquals(pageLoadStatus, true);
			page.navigateToReservePage(depaturecity, destination);
			String title = driver.getTitle();
			sassert.assertEquals(title, reservePageTitle);
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
	 * Check Navigation to purchase page
	 */
	@Test(priority = 3, dependsOnMethods = "checkNavigationToReservePage")
	public void checkNavigationToPurchasePage() {
		try {
			log.debug("TC :checkNavigationToPurchasePage");

			rpage.chooseFlight(testFlight);
			sassert.assertEquals(getPageLoadStatus(driver), true);
			String title = driver.getTitle();
			if (title.contains(homePageTitle)) {
				Assert.assertTrue(true);
			} else {
				Assert.assertTrue(false);
			}
			sassert.assertEquals(ppage.checkPageElements(), true);

		} catch (Exception ex) {
			ex.printStackTrace();
			log.debug("TC :checkNavigationToPurchasePage" + ex.toString());
		}

	}

	/**
	 * Check functionality of navigating to confirmation page
	 * 
	 * @param map
	 */
	@Test(priority = 4, dependsOnMethods = "checkNavigationToPurchasePage", dataProvider = "getUserInfo")
	public void checkEnteringCustomerDetails(Object map) {
		try {
			log.debug("TC :checkEnteringCustomerDetails");
			HashMap<String, String> userinfo = (HashMap) map;
			ppage.navigateToConfirmationPage(userinfo);
			String title = driver.getTitle();
			sassert.assertEquals(getPageLoadStatus(driver), true);
			if (title.contains(confimPageTitle)) {
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
	 * check for confirmation details
	 */
	@Test(priority = 5, dependsOnMethods = "checkEnteringCustomerDetails")
	public void checkForConfirmationPage() {
		try {
			log.debug("TC :checkForConfirmationPage");

			Assert.assertTrue(cpage.isBookingIdDisplayed());
			Assert.assertTrue(cpage.isAmountDisplayed());
			Assert.assertTrue(cpage.isAuthCodeDisplayed());
			Assert.assertTrue(cpage.isBookingDate());
			Assert.assertTrue(cpage.isCardNumberDisplayed());
			Assert.assertTrue(cpage.isExpirationDateDisplayed());
			Assert.assertTrue(cpage.isStatusDisplayed());
		} catch (Exception ex) {
			ex.printStackTrace();
			log.debug("TC :checkForConfirmationPage" + ex.toString());
		}

	}

}
