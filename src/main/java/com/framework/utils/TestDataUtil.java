package com.framework.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;

public class TestDataUtil {
    private JsonNode testData;

    public TestDataUtil(String testDataPath) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            testData = objectMapper.readTree(new File(testDataPath));
        } catch (IOException e) {
            LogUtil.error("Error reading test data file: " + e.getMessage());
        }
    }

    public String getData(String category, String key) {
        return testData.get(category).get(key).asText();
    }

    public JsonNode getJsonNode(String category, String key) {
        return testData.get(category).get(key);
    }
}
