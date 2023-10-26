package io.github.burakkaygusuz.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyUtil {

    private static volatile PropertyUtil instance = null;
    private final Properties properties;

    private PropertyUtil(String fileName) {
        this.properties = new Properties();
        try (InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(fileName)) {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static PropertyUtil getInstance(String fileName) {
        if (instance == null) {
            synchronized (PropertyUtil.class) {
                if (instance == null)
                    instance = new PropertyUtil(fileName);
            }
        }
        return instance;
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}
