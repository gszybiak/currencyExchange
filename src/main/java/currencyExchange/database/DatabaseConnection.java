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

    public DatabaseConnection(){
        try {
            loadConnectionData();
            connect();
            this.statement = connection.createStatement();
        } catch (SQLException e) {
            connectionLog.error("Błąd podczas tworzenia statementu:", new Exception(e.getMessage()));
        }
    }

    private void connect() {
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            connectionLog.error("Błąd podczas łączenia z bazą danych:", new Exception(e.getMessage()));
        }
    }

    public void disconnect() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                connectionLog.error("Błąd podczas zamykania połączenia:", new Exception(e.getMessage()));
            }
        }
    }

    private void loadConnectionData(){
        try{
            Properties properties = new Properties();

            InputStream inputStream = App.class.getClassLoader().getResourceAsStream("connection.properties");
            properties.load(inputStream);

            url = properties.getProperty("db.url");
            username = properties.getProperty("db.username");
            password = properties.getProperty("db.password");

        } catch (IOException e) {
            connectionLog.error("Błąd podczas pobierania danych połączenia do bazy:", new Exception(e.getMessage()));
        }
    }
}
