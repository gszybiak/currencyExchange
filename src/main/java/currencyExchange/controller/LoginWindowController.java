package currencyExchange.controller;

import currencyExchange.enums.WindowType;
import currencyExchange.helpers.MsgHelper;
import currencyExchange.helpers.WindowHelper;
import currencyExchange.model.Customer;
import javafx.event.ActionEvent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static currencyExchange.helpers.WindowHelper.screenSize;
import static currencyExchange.services.LoginService.isLoginPermitted;

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
    public void btnLoginClicked(ActionEvent actionEvent) {
        String email = txtUser.getText();
        String password = txtPassword.getText();

        customer = isLoginPermitted(email, password);
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
}