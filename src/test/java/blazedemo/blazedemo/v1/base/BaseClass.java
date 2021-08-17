package blazedemo.blazedemo.v1.base;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import blazedemo.blazedemo.v1.pages.ConfirmationPage;
import blazedemo.blazedemo.v1.pages.HomePage;
import blazedemo.blazedemo.v1.pages.PurchasePage;
import blazedemo.blazedemo.v1.pages.ReservationPage;
import blazedemo.blazedemo.v1.utilities.ReadCofiguration;
import blazedemo.blazedemo.v1.utilities.Screenshot;

public class BaseClass {

	ReadCofiguration config = new ReadCofiguration();
	public final String baseURL = config.getBaseURL();
	public static WebDriver driver;
	public static Logger log;
	public SoftAssert sassert;
	public int timeout = 10;
	public String testFlight = "AL969";
	public String homePageTitle = "BlazeDemo Purchase";
	public String confimPageTitle = "BlazeDemo Confirmation";
	public String reservePageTitle = "BlazeDemo - reserve";
	public ConfirmationPage cpage;
	public HomePage page;
	public ReservationPage rpage;
	public PurchasePage ppage;
	

	Screenshot screen;

	@Parameters("browsertype")
	@BeforeClass
	public void setUp(String browser) {
		PropertyConfigurator.configure("log4j.properties");
		sassert = new SoftAssert();
		log = Logger.getLogger(BaseClass.class.getName());
		screen = new Screenshot();
		cpage = new ConfirmationPage(driver);
		page = new HomePage(driver);
		rpage = new ReservationPage(driver);
		ppage = new PurchasePage(driver);

		if (browser.equals("chrome")) {
			System.setProperty("webdriver.chrome.driver", config.getChromepath());
			driver = new ChromeDriver();
		} else if (browser.equals("firefox")) {
			System.setProperty("webdriver.gecko.driver", config.getFirefoxPath());
			driver = new FirefoxDriver();
		}

		log.debug("Browser Type is selected, Driver initialized");

		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(timeout, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		log.debug("SetUp Completed");

	}

	@AfterTest
	public void tearDown() {
		log.debug("Quitting Browser");
		driver.quit();

	}

	/**
	 * Get the current page load status
	 * 
	 * @param driver
	 * @return
	 */
	public boolean getPageLoadStatus(WebDriver driver) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		return js.executeScript("return document.readyState").equals("complete");
	}

	/**
	 * Explicitly perform wait on the element passed
	 * 
	 * @param element
	 */
	public void explicitWaitForElement(WebElement element) {

		WebDriverWait wait = new WebDriverWait(driver, timeout);
		wait.until(ExpectedConditions.visibilityOf(element));

	}

}
