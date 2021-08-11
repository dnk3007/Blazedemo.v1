package blazedemo.blazedemo.v1.testcases;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

public class FunctionalTests extends BaseClass {

	@Test
	public void checkThePriceBeforeAndAfterFlighSelection() {
		try {
		driver.get(baseURL);
		Select selFrom = new Select(driver.findElement(By.name("fromPort")));
		selFrom.selectByValue("Mexico City");
		Select selTo = new Select(driver.findElement(By.name("toPort")));
		selTo.selectByValue("Berlin");
		driver.findElement(By.xpath("//input[@type='submit']")).click();
		
		String expPrice = driver.findElement(By.xpath("//form[@name='VA43']//parent::tr//td[6]")).getText().substring(1);
		String chooseFlight = "//form[@name=\'" + "VA43" + "\']//parent::tr//td/input";
		driver.findElement(By.xpath(chooseFlight)).click();
		String str[]=driver.findElement(By.xpath("//p[contains(text(),'Price')]")).getText().split(":");
		
		Assert.assertEquals(str[1],expPrice);//Comparing both the prices before and after selecting flight
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	@Test
	public void checkAllAvailableAirlinesDisplayedonReservePage() {
		driver.get(baseURL);
		Select selFrom = new Select(driver.findElement(By.name("fromPort")));
		selFrom.selectByValue("Mexico City");
		Select selTo = new Select(driver.findElement(By.name("toPort")));
		selTo.selectByValue("Berlin");
		driver.findElement(By.xpath("//input[@type='submit']")).click();
		Assert.assertTrue(driver.findElement(By.xpath("//td[contains(text(),'Virgin America')]")).isDisplayed());		
		Assert.assertTrue(driver.findElement(By.xpath("//td[contains(text(),'Aer Lingus')]")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//td[contains(text(),'Lufthansa')]")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//td[contains(text(),'United Airlines')]")).isDisplayed());
		
	}
}
