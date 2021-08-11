package blazedemo.blazedemo.v1.pages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class PurchasePage {
	WebDriver driver;

	public PurchasePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		
	}

	@FindBy(id = "inputName")
	@CacheLookup
	WebElement name;

	@FindBy(id = "inputName")
	@CacheLookup
	WebElement address;

	@FindBy(id = "state")
	@CacheLookup
	WebElement state;

	@FindBy(id = "zipCode")
	@CacheLookup
	WebElement zipCode;

	@FindBy(id = "cardType")
	@CacheLookup
	WebElement cardType;

	@FindBy(id = "creditCardNumber")
	@CacheLookup
	WebElement creditCardNumber;

	@FindBy(id = "creditCardMonth")
	@CacheLookup
	WebElement creditCardMonth;

	@FindBy(id = "creditCardYear")
	@CacheLookup
	WebElement creditCardYear;

	@FindBy(id = "nameOnCard")
	@CacheLookup
	WebElement nameOnCard;

	@FindBy(xpath = "//input[@type='submit']")
	@CacheLookup
	WebElement submitPurchase;

	public void navigateToConfirmationPage(HashMap customerDetails) {
		Iterator<Entry<String, String>> it = customerDetails.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, String> set = (Map.Entry<String, String>) it.next();
			Logger.getLogger("PurchasePage").debug(set.getKey() + "=" + set.getValue());
		}
		Logger.getLogger("PurchasePage").debug("Entering customer details");
		name.sendKeys(customerDetails.get("inputName").toString());
		address.sendKeys(customerDetails.get("address").toString());
		state.sendKeys(customerDetails.get("state").toString());
		zipCode.sendKeys(customerDetails.get("zipCode").toString());
		cardType.sendKeys(customerDetails.get("cardType").toString());
		creditCardNumber.sendKeys(customerDetails.get("creditCardNumber").toString());
		creditCardMonth.sendKeys(customerDetails.get("creditCardMonth").toString());
		creditCardYear.sendKeys(customerDetails.get("creditCardYear").toString());
		nameOnCard.sendKeys(customerDetails.get("nameOnCard").toString());
		submitPurchase.click();

	}
	
	public boolean checkPageElements() {
		return (name.isDisplayed()
				&& address.isDisplayed()
				&& state.isDisplayed()
				&& zipCode.isDisplayed()
				&& cardType.isDisplayed()
				&& creditCardMonth.isDisplayed()
				&& creditCardNumber.isDisplayed()
				&& creditCardYear.isDisplayed()
				&& nameOnCard.isDisplayed()
				&& submitPurchase.isDisplayed()
				&& submitPurchase.isEnabled());
	}

}
