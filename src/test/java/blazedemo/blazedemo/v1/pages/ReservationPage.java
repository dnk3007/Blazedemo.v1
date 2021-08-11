package blazedemo.blazedemo.v1.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ReservationPage {
	WebDriver driver;

	public ReservationPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);

	}

	public void chooseFlight(String flightName) {
		//String xpathChooseFlight = "//form[@name=\'" + flightName + "\']//parent::tr//td/input";
		String xpathChooseFlight ="//tbody[1]/tr[1]//input[@class='btn btn-small']";
		// WebElement chooseFlight = driver.findElement(By.xpath(xpathChooseFlight));
		WebDriverWait wait = new WebDriverWait(driver, 20);
		WebElement chooseFlight = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathChooseFlight)));
		chooseFlight.click();

	}

}
