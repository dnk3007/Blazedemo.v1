package blazedemo.blazedemo.v1.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class ConfirmationPage {
	WebDriver driver;

	public ConfirmationPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);

	}

	@FindBy(xpath = "//td[contains(text(),'Id')]/following-sibling::td")
	@CacheLookup
	WebElement bookingId;

	@FindBy(xpath = "	//td[contains(text(),'Status')]/following-sibling::td")
	@CacheLookup
	WebElement status;

	@FindBy(xpath = "	//td[contains(text(),'Amount')]/following-sibling::td")
	@CacheLookup
	WebElement amount;

	@FindBy(xpath = "	//td[contains(text(),'Card')]/following-sibling::td")
	@CacheLookup
	WebElement cardNumber;

	@FindBy(xpath = "	//td[contains(text(),'Expiration')]/following-sibling::td")
	@CacheLookup
	WebElement expirationDate;

	@FindBy(xpath = "	//td[contains(text(),'Auth')]/following-sibling::td")
	@CacheLookup
	WebElement authCode;

	@FindBy(xpath = "	//td[contains(text(),'Date')]/following-sibling::td")
	@CacheLookup
	WebElement bookingDate;

	public boolean isBookingIdDisplayed() {
		return bookingId.isDisplayed();
	}

	public boolean isStatusDisplayed() {
		return status.isDisplayed();
	}

	public boolean isAmountDisplayed() {
		return amount.isDisplayed();
	}

	public boolean isCardNumberDisplayed() {
		return cardNumber.isDisplayed();
	}

	public boolean isExpirationDateDisplayed() {
		return expirationDate.isDisplayed();
	}

	public boolean isAuthCodeDisplayed() {
		return authCode.isDisplayed();
	}

	public boolean isBookingDate() {
		return bookingDate.isDisplayed();
	}

}
