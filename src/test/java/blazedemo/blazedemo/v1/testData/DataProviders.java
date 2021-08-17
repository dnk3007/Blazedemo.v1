package blazedemo.blazedemo.v1.testData;

import java.util.HashMap;

import org.testng.annotations.DataProvider;

/**
 * Test Data source for all modules
 * 
 * @author Naveen D
 *
 */
public class DataProviders {

	@DataProvider
	public Object[][] getCountriesMap() {

		return new Object[][] { { "Paris", "Buenos Aires" }, { "Philadelphia", "Rome" }, { "Boston", "London" },
				{ "Portland", "Berlin" }, { "San Diego", "New York" }, { "Mexico City", "Dublin" },
				{ "São Paolo", "Cairo" } };

	}

	@DataProvider
	public Object[][] getAirlines() {
		return new Object[][] { { "Virgin America" }, { "United Airlines" }, { "Aer Lingus" }, { "Lufthansa" } };
	}

	@DataProvider
	public Object[][] getDataDepartureCity() {

		return new Object[][] { { "Paris", "Buenos Aires" } };

	}

	@DataProvider
	public Object[][] getFlightNumbers() {
		return new Object[][] { { "VA43" } };
	}
	
	@DataProvider
	public Object[][] getUserInfo() {
		HashMap<String, String> map = new HashMap<>();
		map.put("inputName", "Naveen");
		map.put("address", "Varthur");
		map.put("state", "KA");
		map.put("zipCode", "560087");
		map.put("cardType", "VISA");
		map.put("creditCardNumber", "7777-2992-3989-2222");
		map.put("creditCardMonth", "4");
		map.put("creditCardYear", "2022");
		map.put("nameOnCard", "Naveen");

		return new Object[][] { { map } };
	}

}
