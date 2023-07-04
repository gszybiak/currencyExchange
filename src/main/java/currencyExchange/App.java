package currencyExchange;

import currencyExchange.database.DatabaseConnection;
import currencyExchange.database.DatabaseOperationCustomers;
import currencyExchange.database.DatabaseOperationTransactions;
import currencyExchange.enums.WindowType;
import currencyExchange.helpers.WindowHelper;
import javafx.application.Application;
import javafx.stage.Stage;

import java.sql.Date;
import java.time.LocalDate;

public class App extends Application {

    /**
     * Otwiera otwiera ekran logowania
     */
    @Override
    public void start(Stage stage) {
        WindowHelper.openWindow(WindowType.LOGIN_WINDOW);
    }

    /**
     * Metoda startowa aplikacji.
     */
    public static void main(String[] args){
        DatabaseConnection databaseConnection = new DatabaseConnection();
        DatabaseOperationCustomers databaseOperationCustomers = new DatabaseOperationCustomers();
        DatabaseOperationTransactions databaseOperationTransactions = new DatabaseOperationTransactions();

        databaseOperationCustomers.addCustomer("Gabriel", "Szybiak", "g_szybiak@wp.pl",
                "mario", "Ostrów 404", 733939777 , databaseConnection.getStatement());

        databaseOperationTransactions.addTransaction(1, Date.valueOf(LocalDate.now()), 120.0,
                "USD", "sell", 4.09, databaseConnection.getStatement());

        Customer customer = databaseOperationCustomers.getCustomerById(1, databaseConnection.getStatement());
        Transaction transaction = databaseOperationTransactions.getTransactionById(1, databaseConnection.getStatement());

        databaseConnection.disconnect();
        launch();
    }
}
