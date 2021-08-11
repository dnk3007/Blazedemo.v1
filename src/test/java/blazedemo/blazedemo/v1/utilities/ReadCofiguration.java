package blazedemo.blazedemo.v1.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import java.lang.*;

import org.apache.log4j.Logger;

public class ReadCofiguration {
	Properties prop;

	public ReadCofiguration() {

		File configfolder = new File("./configuration/config.properties");
		try {
			FileInputStream istream = new FileInputStream(configfolder);
			prop = new Properties();
			prop.load(istream);
		} catch (Exception ex) {
			Logger.getLogger("ReadCOnfiguration").debug("Exception Occurred while reading config");
			ex.printStackTrace();

		}
	}
	
	public String getBaseURL() {
		System.out.println("Getting Base URL "+ prop.getProperty("baseURL"));
		return prop.getProperty("baseURL");	
	}

	public String getChromepath() {
		return prop.getProperty("chromepath");	
	}
	
	public String getFirefoxPath() {
		return prop.getProperty("firefoxpath");	
	}
	
	
}
