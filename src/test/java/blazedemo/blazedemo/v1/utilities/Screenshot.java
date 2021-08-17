package blazedemo.blazedemo.v1.utilities;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class Screenshot {

	static WebDriver driver;

	/**
	 * Capture screen shot and copy to the file
	 * 
	 * @param file
	 */
	public static void takeScreenshot(String file) {
		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(src, new File("./screenshots/" + file + ".png"));
		} catch (IOException e) {

			e.printStackTrace();
		}

	}
}
