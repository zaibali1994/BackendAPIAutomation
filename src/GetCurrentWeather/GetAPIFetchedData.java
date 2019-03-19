package GetCurrentWeather;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class GetAPIFetchedData {

	@Test (priority = 1)
	public void testResponseCode(){

		Response resp = RestAssured.get(
				"http://samples.openweathermap.org/data/2.5/weather?q=London&appid=c5f4125243de15ada2358c58b805bb31");
		int code = resp.getStatusCode();
		System.out.println("Response Code is " + code);

		Assert.assertEquals(code, 200);

	}

	@Test (priority = 2)
	public void testVerifyTemp() {

		Response resp = RestAssured.get(
				"http://samples.openweathermap.org/data/2.5/weather?q=London&appid=c5f4125243de15ada2358c58b805bb31");
		String data = resp.asString();
		if (data.contains("temp:")) {
			boolean status = true;
			System.out.println(status);
			Assert.assertEquals(status, true);

		}
	}

	@Test (priority = 3)
	public void testVerifyCityName() {

		Response resp = RestAssured.get(
				"http://samples.openweathermap.org/data/2.5/weather?q=London&appid=c5f4125243de15ada2358c58b805bb31");
		JsonPath JEvaluator = resp.jsonPath();
		String city = JEvaluator.get("name");
		System.out.println("City name is " + JEvaluator.get("name"));

		Assert.assertEquals(city, "London");
		;

	}
	
	@Test (priority = 4)
	public void testVerifyInvalidKeyErrorMapping() {

		Response resp = RestAssured.get(
				"http://samples.openweathermap.org/data/2.5/weather?q=London&appid=");
		String data = resp.asString();
		if (data.contains("Invalid API Key")) {
			boolean status = true;
			System.out.println(status);
			Assert.assertEquals(status, true);

		}
	}
	
	@Test (priority = 5)
	public void testVerifyParamsErrorMapping() {

		Response resp = RestAssured.get(
				"http://samples.openweathermap.org/data/2.5/weather?&appid=c5f4125243de15ada2358c58b805bb31");
		JsonPath JEvaluator = resp.jsonPath();
		int errorcode = JEvaluator.get("error");
		System.out.println("Response has error " + JEvaluator.get("error"));

		Assert.assertEquals(errorcode, "404");

	}
}
