package utils;

import java.nio.file.Paths;

public class CommonFunctions {
	
	private static String projectPath = null;
	
	public static String getProjectPath() {
		projectPath = Paths.get("").toAbsolutePath().toString();		
		return projectPath;
	}

}
