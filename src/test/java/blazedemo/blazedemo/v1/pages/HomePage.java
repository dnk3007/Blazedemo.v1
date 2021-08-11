package blazedemo.blazedemo.v1.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {

	WebDriver driver;

	public HomePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);

	}

	@FindBy(name = "fromPort")
	@CacheLookup
	WebElement fromPort;

	@FindBy(name = "toPort")
	@CacheLookup
	WebElement toPort;

	@FindBy(xpath = "//input[@type='submit']")
	@CacheLookup
	WebElement submitBtn;

	@FindBy(css = "h1")
	WebElement heading;

	public void navigateToReservePage(String fromCountry, String toCountry) {
		WebDriverWait wait = new WebDriverWait(driver, 20);
		System.out.println("This is the current url "+driver.getCurrentUrl() + " Title = " +driver.getTitle());
		wait.until(ExpectedConditions.elementToBeClickable(fromPort));
		wait.until(ExpectedConditions.elementToBeClickable(toPort));
		
		Select selectfrom = new Select(fromPort);
		selectfrom.selectByValue(fromCountry);
		Select selectto = new Select(toPort);
		selectto.selectByValue(toCountry);
		submitBtn.click();
	}

	public boolean checkPageElements() {
		boolean headingcheck = heading.getText().contains(" Simple Travel Agency!");
		return (heading.isDisplayed() && headingcheck && fromPort.isDisplayed() && toPort.isDisplayed()
				&& submitBtn.isDisplayed());

	}

}
