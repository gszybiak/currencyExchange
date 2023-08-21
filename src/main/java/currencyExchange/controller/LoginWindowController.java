package currencyExchange.controller;

import currencyExchange.model.Customer;
import currencyExchange.database.DatabaseConnection;
import currencyExchange.database.DatabaseOperationCustomers;
import currencyExchange.enums.WindowType;
import currencyExchange.helpers.MsgHelper;
import currencyExchange.helpers.WindowHelper;
import javafx.event.ActionEvent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static currencyExchange.helpers.WindowHelper.screenSize;

public class LoginWindowController {
    /* Object logger */
    private static final Logger loginWindowLog = LogManager.getLogger(WindowHelper.class);
    /* Incorrect login count */
    private int incorrectLoginCount = 0;
    /* Password */
    public PasswordField txtPassword;
    /* Login */
    public TextField txtUser;
    /* Object customer */
    public static Customer customer = null;

    public void initialize() {
        txtPassword.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                btnLoginClicked(null);
            }
        });
    }

    /**
     * Method checks if the login details are correct, if they are it goes to the main screen, if not it displays it
     * message, if the message appears 3 times, the login window turns off
     */
    public void btnLoginClicked(ActionEvent actionEvent) {
        String email = txtUser.getText();
        String password = txtPassword.getText();

        customer = userValidationAndGetIdUser(email, password);
        if (incorrectLoginCount >= 3) {
            ((Stage) txtPassword.getScene().getWindow()).close();
        }
    else if (customer.getId() != null) {
            ((Stage) txtPassword.getScene().getWindow()).close();
            WindowHelper.openWindow(WindowType.MAIN_WINDOW, screenSize.width, screenSize.height);
        }
        else {
            MsgHelper.showError("Login failed", "Enter valid login details");
            loginWindowLog.error("Login failed");
            incorrectLoginCount++;
        }
    }

    /**
     * Method that checks whether there is a user with the entered login data
     */
    private Customer userValidationAndGetIdUser(String email, String password){
        DatabaseConnection databaseConnection = new DatabaseConnection();
        DatabaseOperationCustomers databaseOperationCustomers = new DatabaseOperationCustomers();
        Customer customer = databaseOperationCustomers.getCustomerToLog(email, password, databaseConnection.getStatement());
        databaseConnection.disconnect();
        return customer;
    }
}