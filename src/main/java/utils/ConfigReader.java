package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Properties;
import io.opentelemetry.api.internal.StringUtils;

public class ConfigReader {

	static Properties prop = new Properties();

	public static String readConfigValue(String key) {
		
		String filepath = CommonFunctions.getProjectPath() + "/src/test/resources/Project.properties";		
		try {
			FileInputStream fis = new FileInputStream(filepath);
			prop.load(fis);
		} catch (IOException e) {			
			e.printStackTrace();
		}

		String value = prop.get(key).toString();

		if (StringUtils.isNullOrEmpty(value)) {
			try {
				throw new Exception("Value is not specified for key " + key + " in Properties file");
			} catch (Exception e) {

			}
		}
		
		return value;
	}
}
