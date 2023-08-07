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

import java.awt.*;

public class LoginWindowController {
    private static final Logger loginWindowLog = LogManager.getLogger(WindowHelper.class);
    private int incorrectLoginCount = 0;
    public PasswordField txtPassword;
    public TextField txtUser;
    public static Customer customer = null;

    public void initialize() {
        txtPassword.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                btnLoginClicked(null);
            }
        });
    }

    /**
     * Metoda sprawdza czy dane logowania są poprawne, jeśli sa to przechodzi do ekranu głównego, jeśli nie to wyświetla
     * komunikat, jeśli komunikat pokaże się 3 razy okienko logowania się wyłącza
     */
    public void btnLoginClicked(ActionEvent actionEvent) {
        String email = txtUser.getText();
        String password = txtPassword.getText();
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();

        customer = userValidationAndGetIdUser(email, password);
        if (incorrectLoginCount >= 3) {
            ((Stage) txtPassword.getScene().getWindow()).close();
        }
    else if (customer.getId() != null) {
            ((Stage) txtPassword.getScene().getWindow()).close();
            WindowHelper.openWindow(WindowType.MAIN_WINDOW, screenSize.width, screenSize.height);
        }
        else {
            MsgHelper.showError("Błąd logowania", "Podaj poprawne dane logowania");
            loginWindowLog.error("Błąd logowania");
            incorrectLoginCount++;
        }
    }

    private Customer userValidationAndGetIdUser(String email, String password){
        DatabaseConnection databaseConnection = new DatabaseConnection();
        DatabaseOperationCustomers databaseOperationCustomers = new DatabaseOperationCustomers();
        Customer customer = databaseOperationCustomers.getCustomerToLog(email, password, databaseConnection.getStatement());
        databaseConnection.disconnect();
        return customer;
    }
}