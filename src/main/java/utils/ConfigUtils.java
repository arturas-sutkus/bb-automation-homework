package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class ConfigUtils {

    private static Properties properties;
    static String projectRoot = System.getProperty("user.dir");
    static Path filePath = Paths.get(projectRoot,  "config.properties");

    static {
        try (InputStream input = new FileInputStream(filePath.toString())) {
            properties = new Properties();
            properties.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}
