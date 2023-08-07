package currencyExchange.controller;

import currencyExchange.database.DatabaseConnection;
import currencyExchange.database.DatabaseOperationTransactions;
import currencyExchange.enums.CurrencyType;
import currencyExchange.enums.WindowType;
import currencyExchange.helpers.WindowHelper;
import currencyExchange.model.StatisticEur;
import currencyExchange.model.StatisticGbp;
import currencyExchange.model.StatisticUsd;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.awt.*;


public class MainWindowController {
    public Label txtInfoUsd;
    public Label txtInfoEur;
    public Label txtInfoGbp;
    public Label txtUsers;
    public Button btnClose;
    Toolkit toolkit = Toolkit.getDefaultToolkit();
    Dimension screenSize = toolkit.getScreenSize();
    public static StatisticUsd statisticUsd = null;
    public static StatisticEur statisticEur = null;
    public static StatisticGbp statisticGbp = null;

    public void initialize(){
        txtUsers.setText("Użytkownik: " + LoginWindowController.customer.getName() + " " + LoginWindowController.customer.getSurname());
        loadStatisticUsd();
        if(statisticUsd != null)
            txtInfoUsd.setText(statisticUsd.getBought().toString() + "\"" + statisticUsd.getSold() + "\""
            + statisticUsd.getBalance());
        loadStatisticEur();
        if(statisticEur != null)
            txtInfoEur.setText(statisticEur.getBought().toString() + "\"" + statisticEur.getSold() + "\""
                    + statisticEur.getBalance());
        loadStatisticGbp();
        if(statisticGbp != null)
            txtInfoGbp.setText(statisticGbp.getBought().toString() + "\"" + statisticGbp.getSold() + "\""
                    + statisticGbp.getBalance());
    }

    public void loadStatisticUsd(){
        DatabaseConnection databaseConnection = new DatabaseConnection();
        DatabaseOperationTransactions databaseOperationTransactions = new DatabaseOperationTransactions();
        statisticUsd = databaseOperationTransactions.getStatisticUsdById(LoginWindowController.customer.getId(), databaseConnection.getStatement());
        databaseConnection.disconnect();
    }
    public void loadStatisticEur(){
        DatabaseConnection databaseConnection = new DatabaseConnection();
        DatabaseOperationTransactions databaseOperationTransactions = new DatabaseOperationTransactions();
        statisticEur = databaseOperationTransactions.getStatisticEurById(LoginWindowController.customer.getId(), databaseConnection.getStatement());
        databaseConnection.disconnect();
    }

    public void loadStatisticGbp(){
        DatabaseConnection databaseConnection = new DatabaseConnection();
        DatabaseOperationTransactions databaseOperationTransactions = new DatabaseOperationTransactions();
        statisticGbp = databaseOperationTransactions.getStatisticGbpById(LoginWindowController.customer.getId(), databaseConnection.getStatement());
        databaseConnection.disconnect();
    }

    public void btnBuyClicked(ActionEvent actionEvent) {
        ExchangeWindowController.buyMode = true;
        WindowType.EXCHANGE_WINDOW.setTitle("Kup walutę");
        WindowHelper.openWindow(WindowType.EXCHANGE_WINDOW, screenSize.width, screenSize.height);
    }

    public void btnLogOut(ActionEvent actionEvent) {
        ((Stage) btnClose.getScene().getWindow()).close();
        WindowHelper.openWindow(WindowType.LOGIN_WINDOW);
    }

    public void btnSellClicked(ActionEvent actionEvent) {
        ExchangeWindowController.buyMode = false;
        WindowType.EXCHANGE_WINDOW.setTitle("Sprzedaj walutę");
        WindowHelper.openWindow(WindowType.EXCHANGE_WINDOW, screenSize.width, screenSize.height);
    }

    public void btnEuroClicked(ActionEvent actionEvent) {
        StatisticsWindowController.currency = CurrencyType.EURO.getName();
        WindowType.STATISTICS_WINDOW.setTitle("Statystyki Euro");
        WindowHelper.openWindow(WindowType.STATISTICS_WINDOW, screenSize.width, screenSize.height);

    }

    public void btnDollarClicked(ActionEvent actionEvent) {
        StatisticsWindowController.currency = CurrencyType.DOLAR.getName();
        WindowType.STATISTICS_WINDOW.setTitle("Statystyki Dollar");
        WindowHelper.openWindow(WindowType.STATISTICS_WINDOW, screenSize.width, screenSize.height);
    }

    public void btnPoundClicked(ActionEvent actionEvent) {
        StatisticsWindowController.currency = CurrencyType.FUNT.getName();
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

//            TextInputDialog dialog = new TextInputDialog("Adres email");
//            dialog.setTitle("Wysyłanie email");
//            dialog.setHeaderText("Podaj email:");
//            dialog.setContentText("Email:");
//            Optional<String> result = dialog.showAndWait();
//
//            result.ifPresent(name -> {
//                String encodedString = Base64.getEncoder().encodeToString(name.getBytes());
//                byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
//                String decodedString = new String(decodedBytes);
//                SendEmail.sendEmail(decodedString);
//            });
    }
}
