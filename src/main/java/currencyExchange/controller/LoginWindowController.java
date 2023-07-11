package currencyExchange.controller;

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

    /**
     * Metoda sprawdza czy dane logowania są poprawne, jeśli sa to przechodzi do ekranu głównego, jeśli nie to wyświetla
     * komunikat, jeśli komunikat pokaże się 3 razy okienko logowania się wyłącza
     */
    public void btnLoginClicked(ActionEvent actionEvent) {
        String password = txtPassword.getText();
        String login = txtUser.getText();

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();

        if (incorrectLoginCount >= 3) {
            ((Stage) txtPassword.getScene().getWindow()).close();
        }
        else if (login.equals("admin") && password.equals("admin")) {
            ((Stage) txtPassword.getScene().getWindow()).close();
            WindowHelper.openWindow(WindowType.MAIN_WINDOW, screenSize.width, screenSize.height);
        }
        else {
            MsgHelper.showError("Błąd logowania", "Podaj poprawne dane logowania");
            loginWindowLog.error("Błąd logowania");
            incorrectLoginCount++;
        }
    }

    /**
     * Metoda pozwala na przejście do logowania  za pomoca klawisza enter
     */
    public void initialize() {
        txtPassword.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                btnLoginClicked(null);
            }
        });
    }
}