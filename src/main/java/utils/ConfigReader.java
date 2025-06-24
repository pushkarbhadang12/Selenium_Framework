package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Properties;
import io.opentelemetry.api.internal.StringUtils;

public class ConfigReader {

	static Properties prop = new Properties();

	public static String readConfigValue(String key) {

		//String filepath = System.getProperty("user.dir") + "/src/test/resources/Project.properties";
		String filepath = Paths.get("").toAbsolutePath().toString() + "/src/test/resources/Project.properties";
		System.out.println("Properties file path: "+filepath);
		try {
			FileInputStream fis = new FileInputStream(filepath);
			prop.load(fis);
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
