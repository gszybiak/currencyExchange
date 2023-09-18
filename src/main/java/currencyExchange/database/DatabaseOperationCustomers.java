package currencyExchange.database;

import currencyExchange.model.Customer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Base64;

public class DatabaseOperationCustomers {

    private static final Logger customerLog = LogManager.getLogger(DatabaseOperationCustomers.class);
    private final String tableName = "Customers";

    public Customer getCustomerToLogin(String email, String password,  Statement statement) {
        String passwordBase64 = Base64.getEncoder().encodeToString(password.getBytes());
        Customer customer = null;
        String sqlQuery = String.format("SELECT * FROM %s WHERE email = \"%s\" AND password = \"%s\"",
                tableName, email, passwordBase64);
        try {
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            if (resultSet.next()) {
                customer = new Customer(resultSet.getInt("id"), resultSet.getString("name"),
                        resultSet.getString("surname"), resultSet.getString("email"),
                        resultSet.getString("password"), resultSet.getString("address"),
                        resultSet.getInt("phoneNumber"));
            }
            resultSet.close();
            return customer;

        } catch (SQLException e) {
            customerLog.error("Error retrieving login details", new Exception(e.getMessage()));
            return null;
        }
    }
}