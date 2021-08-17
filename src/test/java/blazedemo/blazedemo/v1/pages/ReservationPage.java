package blazedemo.blazedemo.v1.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ReservationPage {
	WebDriver driver;

	public ReservationPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);

	}

	@FindBy(xpath = "//tbody[1]/tr[1]//input[@class='btn btn-small']")
	WebElement flightChooser;

	/**
	 * Snippet to perform flight selection
	 * 
	 * @param flightName
	 */
	public void chooseFlight(String flightName) {
		WebDriverWait wait = new WebDriverWait(driver, 20);
		new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(flightChooser)).click();

	}

}
