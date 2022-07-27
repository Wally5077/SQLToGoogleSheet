package models.jdbc;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static java.lang.Thread.currentThread;

/**
 * @author - johnny850807@gmail.com (Waterball)
 */
public class PropertiesUtils {
    public static Properties getProperties(String propertyFileClassPath) throws IOException {
        try (InputStream in = currentThread().getContextClassLoader().getResourceAsStream(propertyFileClassPath)) {
            Properties properties = new Properties();
            properties.load(in);
            return properties;
        }
    }
}
