package currencyExchange.controller;

import currencyExchange.database.DatabaseConnection;
import currencyExchange.database.DatabaseOperationTransactions;
import currencyExchange.email.SendEmail;
import currencyExchange.enums.CurrencyType;
import currencyExchange.enums.WindowType;
import currencyExchange.helpers.WindowHelper;
import currencyExchange.model.Statistic;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.Base64;
import java.util.Optional;

import static currencyExchange.helpers.TypeAndFormatHelper.formatDouble;

public class MainWindowController {
    public Label txtInfoUsd;
    public Label txtInfoEur;
    public Label txtInfoGbp;
    public Label txtUsers;
    public Button btnClose;
    Toolkit toolkit = Toolkit.getDefaultToolkit();
    Dimension screenSize = toolkit.getScreenSize();
    public static Statistic statisticUsd = null;
    public static Statistic statisticEur = null;
    public static Statistic statisticGbp = null;

    public void initialize(){
        txtUsers.setText("Użytkownik: " + LoginWindowController.customer.getName() + " " + LoginWindowController.customer.getSurname());
        loadStatistic();
    }

    public void loadStatistic(){
        loadStatisticUsd();
        if(statisticUsd != null)
            txtInfoUsd.setText(formatDouble(statisticUsd.getBought()) + " / " + formatDouble(statisticUsd.getSold()) + " / "
                    + formatDouble(statisticUsd.getBalance()));
        loadStatisticEur();
        if(statisticEur != null)
            txtInfoEur.setText(formatDouble(statisticEur.getBought()) + " / " + formatDouble(statisticEur.getSold()) + " / "
                    + formatDouble(statisticEur.getBalance()));
        loadStatisticGbp();
        if(statisticGbp != null)
            txtInfoGbp.setText(formatDouble(statisticGbp.getBought()) + " / " + formatDouble(statisticGbp.getSold()) + " / "
                    + formatDouble(statisticGbp.getBalance()));
    }

    public void loadStatisticUsd(){
        DatabaseConnection databaseConnection = new DatabaseConnection();
        DatabaseOperationTransactions databaseOperationTransactions = new DatabaseOperationTransactions();
        statisticUsd = databaseOperationTransactions.getStatisticById(LoginWindowController.customer.getId(), CurrencyType.DOLAR, databaseConnection.getStatement());
        databaseConnection.disconnect();
    }
    public void loadStatisticEur(){
        DatabaseConnection databaseConnection = new DatabaseConnection();
        DatabaseOperationTransactions databaseOperationTransactions = new DatabaseOperationTransactions();
        statisticEur = databaseOperationTransactions.getStatisticById(LoginWindowController.customer.getId(), CurrencyType.EURO, databaseConnection.getStatement());
        databaseConnection.disconnect();
    }

    public void loadStatisticGbp(){
        DatabaseConnection databaseConnection = new DatabaseConnection();
        DatabaseOperationTransactions databaseOperationTransactions = new DatabaseOperationTransactions();
        statisticGbp = databaseOperationTransactions.getStatisticById(LoginWindowController.customer.getId(), CurrencyType.FUNT, databaseConnection.getStatement());
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

        String textToSave = prepareData();
        StringSelection selection = new StringSelection(textToSave);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, selection);
    }

    /**
     * Metoda Wyślij maila
     */
    public void btnMailClicked(ActionEvent actionEvent) {
        String textToSave = prepareData();
        TextInputDialog dialog = new TextInputDialog("Adres email");
        dialog.setTitle("Wysyłanie email");
        dialog.setHeaderText("Podaj email:");
        dialog.setContentText("Email:");
        Optional<String> result = dialog.showAndWait();

        result.ifPresent(name -> {
            String encodedString = Base64.getEncoder().encodeToString(name.getBytes());
            byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
            String decodedString = new String(decodedBytes);
            SendEmail.sendEmail(decodedString, textToSave);
        });
    }

    private String prepareData(){
        String textToSave = "Statystyki transakcji: \n";
        textToSave += "USD(Kupiono/Sprzedano/Bilans): " + txtInfoUsd.getText() + "\n";
        textToSave += "EURO(Kupiono/Sprzedano/Bilans): " +  txtInfoEur.getText() + "\n";
        textToSave += "GBP(Kupiono/Sprzedano/Bilans): " +  txtInfoGbp.getText();
        return textToSave;
    }
}
