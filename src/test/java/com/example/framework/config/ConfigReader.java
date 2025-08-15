package com.example.framework.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
    private static final Properties props = new Properties();

    static {
        load();
    }

    private static void load() {
        String env = System.getProperty("env", System.getProperty("profile", "qa"));
        if (env == null || env.isBlank()) env = "qa";
        String path = "config/" + env + ".properties";
        try (InputStream is = ConfigReader.class.getClassLoader().getResourceAsStream(path)) {
            if (is != null) {
                props.load(is);
            }
        } catch (IOException ignored) {}
    }

    public static String get(String key, String defVal) {
        return System.getProperty(key, props.getProperty(key, defVal));
    }

    public static String get(String key) {
        return get(key, null);
    }
}
