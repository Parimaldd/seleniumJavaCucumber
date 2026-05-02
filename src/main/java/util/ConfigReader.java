package util;

import region.Region;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    private static Properties prop = new Properties();

    static {
        try {
            String path = System.getProperty("user.dir")
                    + "/src/main/java/data/"
                    + Region.REGION
                    + "/details.properties";

            FileInputStream fis = new FileInputStream(path);
            prop.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load properties file");
        }
    }

    public static String get(String key) {
        return prop.getProperty(key);
    }
}