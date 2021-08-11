package blazedemo.blazedemo.v1.testcases;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

public class FunctionalTests extends BaseClass {

	@Test
	public void checkThePriceBeforeAndAfterFlighSelection() {
		driver.get(baseURL);
		Select selectfrom = new Select(driver.findElement(By.name("fromPort")));
		selectfrom.selectByValue("Mexico City");
		Select selectTo = new Select(driver.findElement(By.name("toPort")));
		selectTo.selectByValue("Berlin");
		driver.findElement(By.xpath("//input[@type='submit']")).click();
		String xpathChooseFlight = "//form[@name=\'" + "VA43" + "\']//parent::tr//td/input";
		String expectedPrice = driver.findElement(By.xpath("//form[@name='VA43']//parent::tr//td[6]")).getText().substring(1);
		driver.findElement(By.xpath(xpathChooseFlight)).click();
		String str[]=driver.findElement(By.xpath("//p[contains(text(),'Price')]")).getText().split(":");
		
		Assert.assertEquals(str[1],expectedPrice);//Comparing both the prices before and after selcting flight
	}
	
	@Test(groups = "Sanity")
	public void checkAllAvailableAirlinesDisplayedonReservePage() {
		driver.get(baseURL);
		Select selectfrom = new Select(driver.findElement(By.name("fromPort")));
		selectfrom.selectByValue("Mexico City");
		Select selectTo = new Select(driver.findElement(By.name("toPort")));
		selectTo.selectByValue("Berlin");
		driver.findElement(By.xpath("//input[@type='submit']")).click();
		Assert.assertTrue(driver.findElement(By.xpath("//td[contains(text(),'Virgin America')]")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//td[contains(text(),'United Airlines')]")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//td[contains(text(),'Aer Lingus')]")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//td[contains(text(),'Lufthansa')]")).isDisplayed());
		
		
	}
}
