package currencyExchange.database;

import currencyExchange.Customer;
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
            customerLog.error("Błąd podczas dodawania nowego klienta", new Exception(e.getMessage()));
        }
    }
    public Customer getCustomerById(int customerId, Statement statement) {
        try {
            String sqlQuery = "SELECT * FROM " + tableName + " where Id = " + customerId;
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            statement.execute(sqlQuery);

            Customer customer = new Customer(resultSet.getInt("ID") ,resultSet.getString("Name"), resultSet.getString("Surname"),
                    resultSet.getString("Email"), resultSet.getString("Password"), resultSet.getString("Address"),
                    resultSet.getInt("PhoneNumber"));

            resultSet.close();
            return customer;

        } catch (SQLException e) {
            customerLog.error("Błąd podczas pobierania danych klienta", new Exception(e.getMessage()));
            return null;
        }
    }
}
