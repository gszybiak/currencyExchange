package currencyExchange.database;

import currencyExchange.App;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

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

    /**
     * The method that call load connection data and connect
     */
    public DatabaseConnection(){
        try {
            loadConnectionData();
            connect();
            this.statement = connection.createStatement();
        } catch (SQLException e) {
            connectionLog.error("Error creating statement:", new Exception(e.getMessage()));
        }
    }

    /**
     * The method that connect
     */
    private void connect() {
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            connectionLog.error("Error connecting to the database:", new Exception(e.getMessage()));
        }
    }

    /**
     * The method that disconnect
     */
    public void disconnect() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                connectionLog.error("Error closing connection:", new Exception(e.getMessage()));
            }
        }
    }

    /**
     * The method that loads the connection data
     */
    private void loadConnectionData(){
        try{
            Properties properties = new Properties();

            InputStream inputStream = App.class.getClassLoader().getResourceAsStream("connection.properties");
            properties.load(inputStream);

            url = properties.getProperty("db.url");
            username = properties.getProperty("db.username");
            password = properties.getProperty("db.password");

        } catch (IOException e) {
            connectionLog.error("Error while downloading connection data to the database:", new Exception(e.getMessage()));
        }
    }
}
