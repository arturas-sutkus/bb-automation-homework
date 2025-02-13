package api.helper;

import api.models.CalculateRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class TestDataLoader {
    public static List<CalculateRequest> getTestData(String jsonFilePath) {
        ObjectMapper objectMapper = new ObjectMapper();
        List<CalculateRequest> collection = null;
        try {
            collection = objectMapper.readValue(new File(jsonFilePath),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, CalculateRequest.class));
        } catch (IOException e) {
            throw new RuntimeException("Error while mapping test data", e);
        }
        return collection;
    }
}