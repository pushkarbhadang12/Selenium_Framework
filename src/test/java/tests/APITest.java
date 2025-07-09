package tests;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import api.RestMethods;
import base.BaseTest;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import utils.CommonFunctions;
import utils.ConfigReader;
import utils.ExtentReportManager;
import utils.Log;

public class APITest extends BaseTest{
	String userid = null;
	String username = null;
	String token = null;
	String isbn = null;
	String extraIsbn = null;
	
	@Test(priority = 1)
	public void createUserAPITest(Method method) {
		test = ExtentReportManager.createTest(method.getName());
		
		test.info("createUser API URL: "+ConfigReader.readConfigValue("api_URL")+ConfigReader.readConfigValue("createUserAPIURL"));
		Log.info("createUser API URL: "+ConfigReader.readConfigValue("api_URL")+ConfigReader.readConfigValue("createUserAPIURL"));	
		
		Map<String, String> headerMap = new HashMap<>();
		headerMap.put("Content-Type", "application/json");
		JSONObject header = new JSONObject(headerMap);
		
		test.info("Header to be passed in API:"+headerMap);
		Log.info("Header to be passed in API:"+headerMap);
		
		username = CommonFunctions.generateRandomUsername();
		
		Map<String, Object> map = new HashMap<>();
		map.put("userName", username);
		map.put("password", ConfigReader.readConfigValue("api_Password"));		
		
		JSONObject requestBody = new JSONObject(map);	
		
		test.info("API Request Body: "+requestBody);
		Log.info("API Request Body: "+requestBody);
		
		Response response = RestMethods.sendPostRequest(headerMap, requestBody, ConfigReader.readConfigValue("createUserAPIURL"), method.getName());
		
		RestMethods.validateResponse(response, 201);
		
		userid = RestMethods.extractNodeTextFromJsonResponse(response, "userID");
		
		if(userid!=null) {
			test.pass("Extracted userid successfully from Create User API Response...UserID: "+userid);
			Log.info("Extracted userid successfully from Create User API Response...UserID: "+userid);
		} else {
			test.fail("Unable to extract Node text from userID...");
			Log.error("Unable to extract Node text from userID...");
		}
	}
	
	@Test(priority = 2)
	public void generateTokenAPITest(Method method) {
		test = ExtentReportManager.createTest(method.getName());
		
		test.info("generateToken API URL: "+ConfigReader.readConfigValue("api_URL")+ConfigReader.readConfigValue("generateTokenAPIURL"));
		Log.info("generateToken API URL: "+ConfigReader.readConfigValue("api_URL")+ConfigReader.readConfigValue("generateTokenAPIURL"));	
	
		Map<String, String> headerMap = new HashMap<>();
		headerMap.put("Content-Type", "application/json");
		JSONObject header = new JSONObject(headerMap);
		
		test.info("Header to be passed in API:"+headerMap);
		Log.info("Header to be passed in API:"+headerMap);
		
		Map<String, Object> map = new HashMap<>();
		map.put("userName", username);
		map.put("password", ConfigReader.readConfigValue("api_Password"));		
		
		JSONObject requestBody = new JSONObject(map);	
		
		test.info("API Request Body: "+requestBody);
		Log.info("API Request Body: "+requestBody);
		
		Response response = RestMethods.sendPostRequest(headerMap, requestBody, ConfigReader.readConfigValue("generateTokenAPIURL"), method.getName());
		
		RestMethods.validateResponse(response, 200);
		
		token = RestMethods.extractNodeTextFromJsonResponse(response, "token");
		
		if(token!=null) {
			test.pass("Extracted token successfully from Generate Token API Response...token: "+token);
			Log.info("Extracted token successfully from Generate Token API Response...token: "+token);
		} else {
			test.fail("Unable to extract Node text from userID...");
			Log.error("Unable to extract Node text from userID...");
		}
		
	}
	
	@Test(priority = 3)
	public void getBooksAPITest(Method method) {		
		
		test = ExtentReportManager.createTest(method.getName());	
		
		test.info("GetBook API URL: "+ConfigReader.readConfigValue("api_URL")+ConfigReader.readConfigValue("getBooksAPIURL"));
		Log.info("GetBook API URL: "+ConfigReader.readConfigValue("api_URL")+ConfigReader.readConfigValue("getBooksAPIURL"));		
		
		Response response = RestMethods.sendGetRequest(ConfigReader.readConfigValue("getBooksAPIURL"), method.getName());
		
		RestMethods.validateResponse(response, 200);		
		
		isbn = RestMethods.extractFirstNodeTextFromJsonResponse(response, "books", "isbn");
		extraIsbn = RestMethods.extractSecondNodeTextFromJsonResponse(response, "books", "isbn");
		
		if(isbn!=null) {
			test.pass("Extracted isbn successfully from Get Books API Response...isbn: "+isbn);
			Log.info("Extracted isbn successfully from Get Books API Response...isbn: "+isbn);
		} else {
			test.fail("Unable to extract Node text from isbn...");
			Log.error("Unable to extract Node text from isbn...");
			Assert.fail("Unable to extract Node text from isbn...");
		}
	}
	
	@Test(priority = 4)
	public void addBookAPITest(Method method) {
		test = ExtentReportManager.createTest(method.getName());
		
		test.info("addBook API URL: "+ConfigReader.readConfigValue("api_URL")+ConfigReader.readConfigValue("addBookAPIURL"));
		Log.info("addBook API URL: "+ConfigReader.readConfigValue("api_URL")+ConfigReader.readConfigValue("addBookAPIURL"));	
	
		Map<String, String> headerMap = new HashMap<>();
		headerMap.put("Content-Type", "application/json");
		headerMap.put("Authorization", "Bearer "+token);
		JSONObject header = new JSONObject(headerMap);		
		test.info("Header to be passed in API:"+headerMap);
		Log.info("Header to be passed in API:"+headerMap);
		
		Map<String, Object> map = new HashMap<>();
		map.put("userId", userid);
		
		JSONObject isbnData = new JSONObject();
		isbnData.put("isbn", isbn);
		
		JSONArray array = new JSONArray();
		array.add(isbnData);
		
		map.put("collectionOfIsbns", array);
		
		JSONObject requestBody = new JSONObject(map);	
		test.info("API Request Body: "+requestBody);
		Log.info("API Request Body: "+requestBody);

		Response response = RestMethods.sendPostRequest(headerMap, requestBody, ConfigReader.readConfigValue("addBookAPIURL"), method.getName());
		
		RestMethods.validateResponse(response, 201);
	}
	
	@Test(priority = 5)
	public void editBookAPITest(Method method) {
		test = ExtentReportManager.createTest(method.getName());
		
		test.info("editBook API URL: "+ConfigReader.readConfigValue("api_URL")+ConfigReader.readConfigValue("addBookAPIURL")+"/"+isbn);
		Log.info("editBook API URL: "+ConfigReader.readConfigValue("api_URL")+ConfigReader.readConfigValue("addBookAPIURL")+"/"+isbn);	
	
		Map<String, String> headerMap = new HashMap<>();
		headerMap.put("Content-Type", "application/json");
		headerMap.put("Authorization", "Bearer "+token);
		JSONObject header = new JSONObject(headerMap);		
		test.info("Header to be passed in API:"+headerMap);
		Log.info("Header to be passed in API:"+headerMap);
				
		Map<String, Object> map = new HashMap<>();
		map.put("userId", userid);
		map.put("isbn", extraIsbn);
		
		JSONObject requestBody = new JSONObject(map);
		test.info("API Request Body: "+requestBody);
		Log.info("API Request Body: "+requestBody);
		
		Response response = RestMethods.sendPutRequest(headerMap, requestBody, ConfigReader.readConfigValue("addBookAPIURL")+"/"+isbn, method.getName());
		
		RestMethods.validateResponse(response, 200);
		
	}
	
	@Test(priority = 6)
	public void deleteBookAPITest(Method method) {
		test = ExtentReportManager.createTest(method.getName());
		
		test.info("deleteBook API URL: "+ConfigReader.readConfigValue("api_URL")+ConfigReader.readConfigValue("deleteBookAPIURL"));
		Log.info("deleteBook API URL: "+ConfigReader.readConfigValue("api_URL")+ConfigReader.readConfigValue("deleteBookAPIURL"));	
	
		Map<String, String> headerMap = new HashMap<>();
		headerMap.put("Content-Type", "application/json");
		headerMap.put("Authorization", "Bearer "+token);
		JSONObject header = new JSONObject(headerMap);		
		test.info("Header to be passed in API:"+headerMap);
		Log.info("Header to be passed in API:"+headerMap);
				
		Map<String, Object> map = new HashMap<>();
		map.put("userId", userid);
		map.put("isbn", extraIsbn);
		
		JSONObject requestBody = new JSONObject(map);
		test.info("API Request Body: "+requestBody);
		Log.info("API Request Body: "+requestBody);
		
		Response response = RestMethods.sendDeleteRequest(headerMap, requestBody, ConfigReader.readConfigValue("deleteBookAPIURL"), method.getName());
						
		int expectedStatusCode = 204;
		test.info("Expected Response Status Code: "+expectedStatusCode);
		Log.info("Expected Response Status Code: "+expectedStatusCode);
		
		test.info("API Response Status Code: "+response.getStatusCode());
		Log.info("API Response Status Code: "+response.getStatusCode());
		
		Assert.assertEquals(response.getStatusCode(), expectedStatusCode);
		
		if(response.getStatusCode() == expectedStatusCode) {
			test.pass("API executed successfully...");
			Log.info("API executed successfully...");
			
		} else
		{
			test.fail("API execution Failed...");
			Log.error("API execution Failed...");
		}
		
	}

}
