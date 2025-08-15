package com.example.framework.utils;

import io.restassured.module.jsv.JsonSchemaValidator;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class JsonSchemaValidatorUtil {
    public static JsonSchemaValidator matchesSchema(String classpathSchema) {
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(classpathSchema);
        if (is == null) throw new RuntimeException("Schema not found: " + classpathSchema);
        String schema;
        try (Scanner sc = new Scanner(is, StandardCharsets.UTF_8)) {
            schema = sc.useDelimiter("\\A").next();
        }
        return JsonSchemaValidator.matchesJsonSchema(schema);
    }

}
