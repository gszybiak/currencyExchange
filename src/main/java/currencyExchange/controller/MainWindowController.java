package currencyExchange.controller;

import currencyExchange.enums.CurrencyType;
import currencyExchange.enums.WindowType;
import currencyExchange.helpers.WindowHelper;
import currencyExchange.model.Statistic;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;


import java.util.ArrayList;
import java.util.List;

import static currencyExchange.database.DatabaseOperationTransactions.loadStatistics;
import static currencyExchange.helpers.DataManagementHelper.copyToClickboard;
import static currencyExchange.helpers.DataManagementHelper.sendMail;
import static currencyExchange.helpers.TypeAndFormatHelper.formatBigDecimal;
import static currencyExchange.helpers.WindowHelper.openBuySellWindow;
import static currencyExchange.helpers.WindowHelper.openStatisticWindow;

public class MainWindowController {
    /* Info usd */
    public Label txtInfoUsd;
    /* Info eur */
    public Label txtInfoEur;
    /* Info gbp */
    public Label txtInfoGbp;
    /* Users */
    public Label txtUsers;
    /* Logout Button */
    public Button btnClose;
    /* Object usd statistic */
    public static Statistic statisticUsd = null;
    /* Object eur statistic */
    public static Statistic statisticEur = null;
    /* Object gbp statistic */
    public static Statistic statisticGbp = null;

    public void initialize(){
        txtUsers.setText("User: " + LoginWindowController.customer.getName() + " " + LoginWindowController.customer.getSurname());
        loadStatistic();
    }

    /**
     * Method that causes the statistics to be loaded
     */
    public void loadStatistic(){
        statisticUsd = loadStatistics(CurrencyType.USD);
        statisticEur = loadStatistics(CurrencyType.EUR);
        statisticGbp = loadStatistics(CurrencyType.GBP);

        txtInfoUsd.setText(formatBigDecimal(statisticUsd.getBought()) + " / " + formatBigDecimal(statisticUsd.getSold()) + " / "
                    + formatBigDecimal(statisticUsd.getBalance()));
        txtInfoEur.setText(formatBigDecimal(statisticEur.getBought()) + " / " + formatBigDecimal(statisticEur.getSold()) + " / "
                    + formatBigDecimal(statisticEur.getBalance()));
        txtInfoGbp.setText(formatBigDecimal(statisticGbp.getBought()) + " / " + formatBigDecimal(statisticGbp.getSold()) + " / "
                    + formatBigDecimal(statisticGbp.getBalance()));
    }

    /**
     * Listener after clicking the buy button
     */
    public void btnBuyClicked(ActionEvent actionEvent) {
        openBuySellWindow(true, txtUsers);
    }

    /**
     * Listener after clicking the logout button
     */
    public void btnLogOut(ActionEvent actionEvent) {
        ((Stage) btnClose.getScene().getWindow()).close();
        WindowHelper.openWindow(WindowType.LOGIN_WINDOW);
    }

    /**
     * Method going to the sell screen
     */
    public void btnSellClicked(ActionEvent actionEvent) {
        openBuySellWindow(false, txtUsers);
    }

    /**
     * Method going to the eur statistics screen
     */
    public void btnEuroClicked(ActionEvent actionEvent) {
        openStatisticWindow(CurrencyType.EUR, txtUsers);
    }

    /**
     * Method going to the usd statistics screen
     */
    public void btnDollarClicked(ActionEvent actionEvent) {
        openStatisticWindow(CurrencyType.USD, txtUsers);
    }

    /**
     * Method going to the gbp statistics screen
     */
    public void btnPoundClicked(ActionEvent actionEvent) {
        openStatisticWindow(CurrencyType.GBP, txtUsers);
    }

    /**
     * Method after clicking the "Copy to clipboard" button
     */
    public void btnCopyClicked(ActionEvent actionEvent){
        copyToClickboard(prepareData());
    }

    /**
     * Method Send Email
     */
    public void btnMailClicked(ActionEvent actionEvent) {
        sendMail(prepareData());
    }

    /**
     * Method to prepare the data for use
     */
    private String prepareData(){
        String textToSave = "Transactions Statistics: \n";
        textToSave += "USD(Bought/Sold/Balance): " + txtInfoUsd.getText() + "\n";
        textToSave += "EUR(Bought/Sold/Balance): " +  txtInfoEur.getText() + "\n";
        textToSave += "GBP(Bought/Sold/Balance): " +  txtInfoGbp.getText();
        return textToSave;
    }
}
