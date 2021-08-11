package blazedemo.blazedemo.v1.testcases;

import java.util.HashMap;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import blazedemo.blazedemo.v1.pages.ConfirmationPage;
import blazedemo.blazedemo.v1.pages.HomePage;
import blazedemo.blazedemo.v1.pages.PurchasePage;
import blazedemo.blazedemo.v1.pages.ReservationPage;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apache.log4j.Logger;

public class BookingFlow extends BaseClass {
	public static Logger log;
	HomePage page;

	@BeforeTest
	public void init() {
		PropertyConfigurator.configure("log4j.properties");
		log = Logger.getLogger(BookingFlow.class.getName());
	}

	@BeforeClass
	public void initPage() {
		page = new HomePage(driver);

	}	
	
	
	@Test(priority = 0)
	public void homePageWebElemetsCheck() {
		log.debug("TC :homePageWebElemetsCheck ");
		try {
			driver.get(baseURL);
			boolean pageLoadStatus = getPageLoadStatus(driver);
			sassert.assertEquals(pageLoadStatus, true);
			sassert.assertEquals(page.checkPageElements(), true);// Check for all elements
		} catch (Exception ex) {
			log.debug("TC :homePageWebElemetsCheck :" + ex.toString());

		}

	}

	
	@Test(priority = 2)
	public void checkNavigationToReservePage() {
		try {
			log.debug("TC :checkNavigationToReservePage ");
			//driver.get(baseURL);
			boolean pageLoadStatus = getPageLoadStatus(driver);
			sassert.assertEquals(pageLoadStatus, true);
			
			page.navigateToReservePage("Paris", "London");
			String title = driver.getTitle();
			sassert.assertEquals(title, "BlazeDemo - reserve");
			sassert.assertEquals(getPageLoadStatus(driver), true);	
		}catch(Exception ex) {
			ex.printStackTrace();
			log.debug("TC :checkNavigationToReservePage" + ex.toString());
		}	
		

	}
	
	@Test(priority = 2, dependsOnMethods =  "checkNavigationToReservePage")
	public void checkNavigationToPurchasePage() {
		try {
			log.debug("TC :checkNavigationToPurchasePage");
			
			ReservationPage rpage = new ReservationPage(driver);
			rpage.chooseFlight("AL969");
			sassert.assertEquals(getPageLoadStatus(driver), true);	
			String title = driver.getTitle();			
			if (title.contains("BlazeDemo Purchase")) {
				Assert.assertTrue(true);
			} else {
				Assert.assertTrue(false);
			}
			PurchasePage page = new PurchasePage(driver);
			sassert.assertEquals(page.checkPageElements(), true);	
			
			
		}catch(Exception ex) {
			ex.printStackTrace();
			log.debug("TC :checkNavigationToPurchasePage" + ex.toString());
		}	
		
	}

	@Test(dependsOnMethods = "checkNavigationToPurchasePage",priority = 3)
	public void checkEnteringCustomerDetails() {
		try {
			log.debug("TC :checkEnteringCustomerDetails"); 
			PurchasePage ppage = new PurchasePage(driver);
			HashMap<String, String> map = new HashMap<>();
			map.put("inputName", "Naveen");
			map.put("address", "Varthur");
			map.put("state", "KA");
			map.put("zipCode", "560087");
			map.put("cardType", "VISA");
			map.put("creditCardNumber", "7777-2992-3989-2222");
			map.put("creditCardMonth", "4");
			map.put("creditCardYear", "2022");
			map.put("nameOnCard", "Naveen");
			ppage.navigateToConfirmationPage(map);
			String title = driver.getTitle();
			sassert.assertEquals(getPageLoadStatus(driver), true);	
			if (title.contains("BlazeDemo Confirmation")) {
				Assert.assertTrue(true);
			} else {
				Assert.assertTrue(false);
			}	
		}catch(Exception ex) {
			ex.printStackTrace();
			log.debug("TC :checkEnteringCustomerDetails" + ex.toString());
		}
		
		

	}

	@Test(dependsOnMethods = "checkEnteringCustomerDetails",priority = 4)
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
		}catch(Exception ex) {
			ex.printStackTrace();
			log.debug("TC :checkForConfirmationPage" + ex.toString());
		}
		
	}
	
	@Test(priority = 5, dataProvider ="getCountriesMap", enabled=true)	
	public void checkNavigationAllCountries(String fromCountry, String toCountry) {
		log.debug("TC :checkNavigationAllCountries ");		
		try {
			if(driver.getCurrentUrl()!=baseURL)
				driver.get(baseURL);				
			boolean pageLoadStatus = getPageLoadStatus(driver);
			sassert.assertEquals(pageLoadStatus, true);		
			page.navigateToReservePage(fromCountry, toCountry);
			String title = driver.getTitle();
			sassert.assertEquals(title, "BlazeDemo - reserve");
			boolean nextpageLoadStatus = getPageLoadStatus(driver);
			sassert.assertEquals(nextpageLoadStatus, true);
		}catch(Exception ex) {
			log.debug("TC :checkNavigationAllCountries" + ex.toString());
		}
		

	}
	
	@Test(priority =  6,dataProvider = "getAirlines" , enabled=true)
	public void checkNaviPurchasePageOnAllFlights(String flight) {
		try {
			log.debug("TC :checkNaviPurchasePageOnAllFlights ");			
			//if(driver.getCurrentUrl()!=baseURL)
			driver.get(baseURL);
			driver.navigate().refresh();
			WebDriverWait wait = new WebDriverWait(driver,10);
			wait.until(ExpectedConditions.refreshed(ExpectedConditions.stalenessOf(driver.findElement(By.name("fromPort")))));
			page.navigateToReservePage("Paris", "London");
			ReservationPage rpage = new ReservationPage(driver);
			rpage.chooseFlight(flight);
			String title = driver.getTitle();
			sassert.assertEquals(title, "BlazeDemo - reserve");
			sassert.assertTrue(getPageLoadStatus(driver));
		}catch(Exception ex) {
			log.debug("TC :checkNaviPurchasePageOnAllFlights" + ex.toString());
			ex.printStackTrace();
			
		}
		finally {
			JavascriptExecutor wdriver = (JavascriptExecutor)driver;
			wdriver.executeScript("history.go(0)");
			driver.manage().deleteAllCookies();
			driver.navigate().back();
			driver.navigate().back();
		}
	}

	@DataProvider
	public Object[][] getCountriesMap() {

		return new Object[][] { 
			{"Paris","Buenos Aires"},
			{"Philadelphia","Rome"},
			{"Boston","London"},
			{"Portland","Berlin"},
			{"San Diego","New York"},
			{"Mexico City","Dublin"},
			{"São Paolo","Cairo"}
			};

	}
	
	@DataProvider
	public Object[][] getAirlines(){
		return new Object[][] {
			{"Virgin America"},
			{"United Airlines"},
			{"Aer Lingus"},			
			{"Lufthansa"}
		};
	}

}
