package com.example.framework.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

public class TestDataManager {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static JsonNode root;

    static {
        try (InputStream is = TestDataManager.class.getClassLoader().getResourceAsStream("data/users.json")) {
            if (is != null) root = MAPPER.readTree(is);
        } catch (IOException ignored) {}
    }

    public static String get(String jsonPointer) {
        if (root == null) return null;
        JsonNode node = root.at("/" + jsonPointer.replace(".", "/"));
        return node.isMissingNode() ? null : node.asText();
    }
}
