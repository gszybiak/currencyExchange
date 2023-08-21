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
    private String tableName = "Customers";
    public void addCustomer(String name, String surname, String email, String password, String address, int phoneNumber, Statement statement) {
        String passworBase64 = Base64.getEncoder().encodeToString(password.getBytes());
        try {
            String sqlQuery = "INSERT INTO " + tableName  + " (Name, Surname, Email, Password, Address, PhoneNumber) " +
                    "VALUES ('" + name + "', '" + surname + "', '" + email + "', '" + passworBase64 + "', '" +
                    address + "', " + phoneNumber + ")";
            statement.execute(sqlQuery);
        } catch (SQLException e) {
            customerLog.error("Error while adding a new client", new Exception(e.getMessage()));
        }
    }
    public Customer getCustomerById(int customerId, Statement statement) {
        try {
            String sqlQuery = "SELECT * FROM " + tableName + " where Id = " + customerId;
            ResultSet resultSet = statement.executeQuery(sqlQuery);

            Customer customer = new Customer(resultSet.getInt("ID") ,resultSet.getString("Name"), resultSet.getString("Surname"),
                    resultSet.getString("Email"), resultSet.getString("Password"), resultSet.getString("Address"),
                    resultSet.getInt("PhoneNumber"));

            resultSet.close();
            return customer;

        } catch (SQLException e) {
            customerLog.error("Error retrieving customer data", new Exception(e.getMessage()));
            return null;
        }
    }

    public Customer getCustomerToLog(String email, String password,  Statement statement) {
        String passwordBase64 = Base64.getEncoder().encodeToString(password.getBytes());
        Customer customer = null;
        String sqlQuery = "SELECT * FROM " + tableName + " where email = \"" + email + "\" and password = \"" + passwordBase64 + "\" ";
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