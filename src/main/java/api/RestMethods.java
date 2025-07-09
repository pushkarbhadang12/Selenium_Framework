package api;

import static io.restassured.RestAssured.given;

import java.util.List;
import java.util.Map;

import org.testng.Assert;

import base.BaseTest;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import utils.Log;

public class RestMethods extends BaseTest {
	
	public static Response sendGetRequest(String endpoint, String description) {
		test.info("Sending GET Request for "+description);
		Log.info("Sending GET Request for "+description);
		Response response = null;
		try {
			response = given().get(endpoint);
		}catch(Exception e) {
			test.fail("Unable to process Get Request. Received error as "+e.getMessage());
		}
	 return response;
	}
	
	public static Response sendPostRequest(Map<String,String> header, Map<String,Object> requestBody, String endpoint, String description) {
		test.info("Sending POST Request for "+description);
		Log.info("Sending POST Request for "+description);
		Response response = null;
		try {
			response = given().
							headers(header).
							body(requestBody).
							post(endpoint);
		}catch(Exception e) {
			test.fail("Unable to process POST Request. Received error as "+e.getMessage());
		}
	 return response;
	}
	
	public static Response sendPutRequest(Map<String,String> header, Map<String,Object> requestBody, String endpoint, String description) {
		test.info("Sending PUT Request for "+description);
		Log.info("Sending PUT Request for "+description);
		Response response = null;
		try {
			response = given().
							headers(header).
							body(requestBody).
							put(endpoint);
		}catch(Exception e) {
			test.fail("Unable to process PUT Request. Received error as "+e.getMessage());
		}
	 return response;
	}
	
	public static Response sendDeleteRequest(Map<String,String> header, Map<String,Object> requestBody, String endpoint, String description) {
		test.info("Sending DELETE Request for "+description);
		Log.info("Sending DELETE Request for "+description);
		Response response = null;
		try {
			response = given().
							headers(header).
							body(requestBody).
							delete(endpoint);
		}catch(Exception e) {
			test.fail("Unable to process DELETE Request. Received error as "+e.getMessage());
		}
	 return response;
	}
	
	public static void validateResponse(Response response, int expectedStatusCode) {
		test.info("API Response Status Code: "+response.getStatusCode());
		Log.info("API Response Status Code: "+response.getStatusCode());
		
		test.info("API Response Body: <textarea style='width:550px;height:150px;resize:none; ' name = 'description'>"
				+ response.getBody().asPrettyString() + "</textarea>");
		Log.info("API Response Body: "+response.getBody().jsonPath().prettyPrint());
		
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
	
	public static String extractNodeTextFromJsonResponse(Response response, String nodeName) {
		JsonPath jsonPath = response.jsonPath();
		String nodeText = jsonPath.get(nodeName);		
				
		return nodeText;
	}
	
	public static String extractFirstNodeTextFromJsonResponse(Response response, String nodeNameLevel1, String nodeNameLevel2) {
		JsonPath jsonPath = response.jsonPath();
		String nodeText = jsonPath.get(nodeNameLevel1+"."+nodeNameLevel2+"[0]");		
				
		return nodeText;
	}
	
	public static String extractSecondNodeTextFromJsonResponse(Response response, String nodeNameLevel1, String nodeNameLevel2) {
		JsonPath jsonPath = response.jsonPath();
		String nodeText = jsonPath.get(nodeNameLevel1+"."+nodeNameLevel2+"[1]");		
				
		return nodeText;
	}
	
}
