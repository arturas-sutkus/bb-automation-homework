package utils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import api.helper.TestDataLoader;
import api.models.CalculateRequest;

public class JsonFileUtils {
    static String projectRoot = System.getProperty("user.dir");

    public static String getJsonResponseFile(String fileName) {
        String jsonContent = "";
        Path filePath = Paths.get(projectRoot, "src", "test", "resources", "api", "response", fileName);

        try {
            jsonContent = new String(Files.readAllBytes(filePath));
        } catch (Exception e) {
            System.out.println("Error reading the file: " + e.getMessage());
        }
        return jsonContent;
    }

    public static List<CalculateRequest> loadTestDataFromFile(String fileName) {
        return TestDataLoader.getTestData(
                String.valueOf(Paths.get(projectRoot, "src", "test", "resources", "api", "data", fileName))
        );
    }
}
