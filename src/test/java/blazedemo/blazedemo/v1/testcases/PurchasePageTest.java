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

import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apache.log4j.Logger;

public class PurchasePageTest extends BaseClass {
	public static Logger log;
	HomePage page;
	ReservationPage rpage;
	PurchasePage ppage;

	@BeforeClass
	public void initPage() {
		PropertyConfigurator.configure("log4j.properties");
		log = Logger.getLogger(NavigationEndToEnd.class.getName());
		page = new HomePage(driver);

	}

	/**
	 * Verify the navigation to purchase page
	 * 
	 * @param departureCountry
	 * @param destinationCountry
	 */
	@Test(priority = 2, dependsOnMethods = "checkNavigationToReservePage", dataProvider = "getCountriesMap", dataProviderClass = DataProviders.class, groups = { "sanity" })
	public void checkNavigationToPurchasePage(String departureCountry, String destinationCountry) {
		try {
			log.debug("TC :checkNavigationToPurchasePage");
			driver.get(baseURL);
			page.navigateToReservePage(departureCountry, destinationCountry);
			ReservationPage rpage = new ReservationPage(driver);
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
	@Test(dependsOnMethods = "checkNavigationToPurchasePage", priority = 3, dataProvider = "getUserInfo")
	public void checkEnteringCustomerDetails(Object map) {
		try {
			log.debug("TC :checkEnteringCustomerDetails");
			HashMap<String, String> userinfo = (HashMap) map;
			ppage.navigateToConfirmationPage(userinfo);
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
	 * Verification of final booking details
	 */
	@Test(dependsOnMethods = "checkEnteringCustomerDetails", priority = 4)
	public void checkForConfirmationPage() {
		try {
			log.debug("TC :checkForConfirmationPage");
			ConfirmationPage cpage = new ConfirmationPage(driver);
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

	/**
	 * Verify navigation different country set
	 * 
	 * @param fromCountry
	 * @param toCountry
	 */
	@Test(priority = 5, dataProvider = "getCountriesMap", dataProviderClass = DataProviders.class)
	public void checkNavigationAllCountries(String fromCountry, String toCountry) {
		log.debug("TC :checkNavigationAllCountries ");
		try {
			if (driver.getCurrentUrl() != baseURL)
				driver.get(baseURL);
			boolean pageLoadStatus = getPageLoadStatus(driver);
			sassert.assertEquals(pageLoadStatus, true);
			page.navigateToReservePage(fromCountry, toCountry);
			String title = driver.getTitle();
			sassert.assertEquals(title, "BlazeDemo - reserve");
			boolean nextpageLoadStatus = getPageLoadStatus(driver);
			sassert.assertEquals(nextpageLoadStatus, true);
		} catch (Exception ex) {
			log.debug("TC :checkNavigationAllCountries" + ex.toString());
		}

	}

	/**
	 * Check for combination of flights. Whether user is able to purchase or not
	 * 
	 * @param Departure Country and Destination Country
	 */
	@Test(priority = 3, dataProvider = "getDataDepartureCity", dataProviderClass = DataProviders.class)
	public void checkNaviPurchasePageOnAllFlights(String departureCountry, String destinationCountry) {
		try {
			log.debug("TC :checkNaviPurchasePageOnAllFlights ");

			driver.get(baseURL);
			driver.navigate().refresh();
			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions
					.refreshed(ExpectedConditions.stalenessOf(driver.findElement(By.name("fromPort")))));
			page.navigateToReservePage(departureCountry, destinationCountry);
			ReservationPage rpage = new ReservationPage(driver);
			rpage.chooseFlight(testFlight);
			String title = driver.getTitle();
			sassert.assertEquals(title, reservePageTitle);
			sassert.assertTrue(getPageLoadStatus(driver));
		} catch (StaleElementReferenceException ex) {
			ex.printStackTrace();
		} catch (Exception ex) {
			log.debug("TC :checkNaviPurchasePageOnAllFlights" + ex.toString());
			ex.printStackTrace();

		} finally {
			JavascriptExecutor wdriver = (JavascriptExecutor) driver;
			wdriver.executeScript("history.go(0)");
			driver.manage().deleteAllCookies();
			driver.navigate().back();
			driver.navigate().back();
		}
	}

	/**
	 * Check The Price before and after flight selection
	 */
	@Test
	public void checkThePriceBeforeAndAfterFlighSelection(String departureCountry, String destinationCountry) {
		try {
			page.navigateToReservePage(departureCountry, destinationCountry);
			driver.findElement(By.xpath(testFlight)).click();
			Assert.assertEquals(ppage.getActualPrice(), ppage.getExpectedPrice());
		} catch (NoSuchElementException ex) {
			ex.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
