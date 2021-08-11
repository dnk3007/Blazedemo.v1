package blazedemo.blazedemo.v1.testcases;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import blazedemo.blazedemo.v1.utilities.ReadCofiguration;

public class BaseClass {

	ReadCofiguration config = new ReadCofiguration();
	public final String baseURL = config.getBaseURL();
	public static WebDriver driver;
	public static Logger log;
	public SoftAssert sassert;
	public int timeout = 10;
	ExtentHtmlReporter htmlReporter;
	ExtentReports extent;
	ExtentTest logger;

	@BeforeTest
	public void startReport() {
		htmlReporter = new ExtentHtmlReporter("extentReport.html");
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
		logger = extent.createTest("DEBUG");
		sassert = new SoftAssert();

	}

	@Parameters("browsertype")
	@BeforeClass
	public void setUp() {
		PropertyConfigurator.configure("log4j.properties");
		log = Logger.getLogger(BaseClass.class.getName());
		String browser = "chrome";
		if (browser.equals("chrome")) {
			System.setProperty("webdriver.chrome.driver", config.getChromepath());
			driver = new ChromeDriver();
		} else if (browser.equals("firefox")) {
			System.setProperty("webdriver.gecko.driver", config.getFirefoxPath());
			driver = new FirefoxDriver();
		}
		logger.log(Status.INFO, "Browser Type is selected, Driver initialized");
		log.debug("Browser Type is selected, Driver initialized");

		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(timeout, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS); 

		log.debug("SetUp Completed");

	}

	@AfterClass
	public void tearDown() {
		log.debug("Quitting Browser");
		driver.quit();

	}

	public boolean getPageLoadStatus(WebDriver driver) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		return js.executeScript("return document.readyState").equals("complete");
	}

	public void explicitWaitForElement(WebElement element) {

		WebDriverWait wait = new WebDriverWait(driver, timeout);
		wait.until(ExpectedConditions.visibilityOf(element));

	}

	public void fluentWait(WebElement element) {
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver);
		wait.withTimeout(timeout, TimeUnit.MILLISECONDS);
		wait.ignoring(NoSuchElementException.class);
		wait.pollingEvery(250, TimeUnit.MILLISECONDS);
		wait.until(ExpectedConditions.visibilityOf(element));

	}

}
