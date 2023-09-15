package currencyExchange.database;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;


import static currencyExchange.helpers.PropertiesHelper.loadPropertiesConnection;

public class DatabaseConnection {
    private static final Logger connectionLog = LogManager.getLogger(DatabaseConnection.class);

    private String url;
    private String username;
    private String password;
    private Connection connection;
    private Statement statement;

    public Statement getStatement() {
        return statement;
    }

    public DatabaseConnection() {
        try {
            loadConnectionData();
            connect();
            this.statement = connection.createStatement();
        } catch (SQLException e) {
            connectionLog.error("Error creating statement:", new Exception(e.getMessage()));
        }
    }

    private void connect() {
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            connectionLog.error("Error connecting to the database:", new Exception(e.getMessage()));
        }
    }

    public void disconnect() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                connectionLog.error("Error closing connection:", new Exception(e.getMessage()));
            }
        }
    }

    private void loadConnectionData() {
        Properties properties = loadPropertiesConnection();
        if (properties != null) {
            url = properties.getProperty("db.url");
            username = properties.getProperty("db.username");
            password = properties.getProperty("db.password");
        }
    }
}
