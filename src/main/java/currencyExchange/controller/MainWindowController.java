package currencyExchange.controller;

import currencyExchange.email.SendEmail;
import currencyExchange.enums.WindowType;
import currencyExchange.helpers.WindowHelper;
import javafx.event.ActionEvent;
import javafx.scene.control.TextInputDialog;

import java.awt.*;
import java.util.Base64;
import java.util.Optional;

public class MainWindowController {

    Toolkit toolkit = Toolkit.getDefaultToolkit();
    Dimension screenSize = toolkit.getScreenSize();

    public void btnBuyClicked(ActionEvent actionEvent) {
        ExchangeWindowController.buyMode = true;
        WindowType.EXCHANGE_WINDOW.setTitle("Kup walutę");
        WindowHelper.openWindow(WindowType.EXCHANGE_WINDOW, screenSize.width, screenSize.height);
    }

    public void btnLogOut(ActionEvent actionEvent) {
        //jak zamknąć mainWindow
        WindowHelper.openWindow(WindowType.LOGIN_WINDOW);
    }

    public void btnSellClicked(ActionEvent actionEvent) {
        ExchangeWindowController.buyMode = false;
        WindowType.EXCHANGE_WINDOW.setTitle("Sprzedaj walutę");
        WindowHelper.openWindow(WindowType.EXCHANGE_WINDOW, screenSize.width, screenSize.height);
    }

    public void btnEuroClicked(ActionEvent actionEvent) {
        WindowType.STATISTICS_WINDOW.setTitle("Statystyki Euro");
        WindowHelper.openWindow(WindowType.STATISTICS_WINDOW, screenSize.width, screenSize.height);

    }

    public void btnDollarClicked(ActionEvent actionEvent) {
        WindowType.STATISTICS_WINDOW.setTitle("Statystyki Dollar");
        WindowHelper.openWindow(WindowType.STATISTICS_WINDOW, screenSize.width, screenSize.height);
    }

    public void btnPoundClicked(ActionEvent actionEvent) {
        WindowType.STATISTICS_WINDOW.setTitle("Statystyki Funta");
        WindowHelper.openWindow(WindowType.STATISTICS_WINDOW, screenSize.width, screenSize.height);
    }

    /**
     * Metoda po kliknięciu przycisku "Kopiuj do schowka"
     */
    public void btnCopyClicked(ActionEvent actionEvent){

//        StringSelection selection = new StringSelection();
//        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
//        clipboard.setContents(selection, selection);
    }

    /**
     * Metoda Wyślij maila
     */
    public void btnMailClicked(ActionEvent actionEvent) {

            TextInputDialog dialog = new TextInputDialog("Adres email");
            dialog.setTitle("Wysyłanie email");
            dialog.setHeaderText("Podaj email:");
            dialog.setContentText("Email:");
            Optional<String> result = dialog.showAndWait();

            result.ifPresent(name -> {
                String encodedString = Base64.getEncoder().encodeToString(name.getBytes());
                byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
                String decodedString = new String(decodedBytes);
                SendEmail.sendEmail(decodedString);
            });
    }
}
