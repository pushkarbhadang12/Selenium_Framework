package utils;

import java.nio.file.Paths;
import java.util.Random;

import com.github.javafaker.Faker;

public class CommonFunctions {
	
	private static String projectPath = null;
	
	public static String getProjectPath() {
		projectPath = Paths.get("").toAbsolutePath().toString();		
		return projectPath;
	}
	
	public static String generateRandomUsername() {
		Faker faker = new Faker();
		String username = faker.name().username().toString();
		return username;
	}

}
