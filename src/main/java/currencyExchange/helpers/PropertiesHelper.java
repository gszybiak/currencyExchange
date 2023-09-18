package currencyExchange.helpers;

import currencyExchange.App;
import currencyExchange.database.DatabaseConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesHelper {
    private static final Logger propertiesLog = LogManager.getLogger(DatabaseConnection.class);

    public static Properties loadPropertiesConnection(){
        try {
            Properties properties = new Properties();
            String filePath = "D:/currencyExchange-master/currencyExchange-master/src/main/resources/currencyExchange/properties/connection.properties";
            FileInputStream fileInputStream = new FileInputStream(filePath);
            properties.load(fileInputStream);

            return properties;
        } catch (IOException e) {
            propertiesLog.error("Error while downloading connection data to the database:", new Exception(e.getMessage()));
            return null;
        }
    }

    public static Properties loadPropertiesEmail(){
        try {
            Properties properties = new Properties();
            String filePath = "D:/currencyExchange-master/currencyExchange-master/src/main/resources/currencyExchange/properties/email.properties";
            FileInputStream fileInputStream = new FileInputStream(filePath);
            properties.load(fileInputStream);
            return properties;
        } catch (
                IOException e) {
            propertiesLog.error("Error while downloading properties email:", new Exception(e.getMessage()));
            return null;
        }
    }

    public static Properties loadPropertiesPath(){
        try {
            Properties properties = new Properties();
            String filePath = "D:/currencyExchange-master/currencyExchange-master/src/main/resources/currencyExchange/properties/path.properties";
            FileInputStream fileInputStream = new FileInputStream(filePath);
            properties.load(fileInputStream);
            return properties;
        } catch (
                IOException e) {
            propertiesLog.error("Error while downloading properties path:", new Exception(e.getMessage()));
            return null;
        }
    }
}
