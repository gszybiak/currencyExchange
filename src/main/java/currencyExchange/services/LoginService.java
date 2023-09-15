package currencyExchange.services;

import currencyExchange.database.DatabaseConnection;
import currencyExchange.database.DatabaseOperationCustomers;
import currencyExchange.model.Customer;


public class LoginService {
    /* Object customer */
    public static Customer customer = null;
    /* Incorrect login count */
    private int incorrectLoginCount = 0;

    public static Customer isLoginPermitted(String email, String password) {
        customer = userValidationAndGetIdUser(email, password);
        return customer;
    }

    private static Customer userValidationAndGetIdUser(String email, String password){
        DatabaseConnection databaseConnection = new DatabaseConnection();
        DatabaseOperationCustomers databaseOperationCustomers = new DatabaseOperationCustomers();
        Customer customer = databaseOperationCustomers.getCustomerToLogin(email, password, databaseConnection.getStatement());
        databaseConnection.disconnect();
        return customer;
    }
}