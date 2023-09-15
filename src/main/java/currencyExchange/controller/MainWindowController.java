package currencyExchange.controller;

import currencyExchange.enums.CurrencyType;
import currencyExchange.enums.WindowType;
import currencyExchange.helpers.WindowHelper;
import currencyExchange.model.Statistic;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

import static currencyExchange.database.DatabaseOperationTransactions.loadStatistics;
import static currencyExchange.helpers.DataManagementHelper.copyToClipboard;
import static currencyExchange.helpers.DataManagementHelper.sendMail;
import static currencyExchange.helpers.TypeAndFormatHelper.formatBigDecimal;
import static currencyExchange.helpers.WindowHelper.*;

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

//    public void loadStatistic(){
//        // FIXME GS: srpóbuj użyć List<Pair<Label, CurrencyType>> - będzie czytelniej :)
//        statisticUsd = loadStatistics(CurrencyType.USD);
//        statisticEur = loadStatistics(CurrencyType.EUR);
//        statisticGbp = loadStatistics(CurrencyType.GBP);
//
//        txtInfoUsd.setText(formatBigDecimal(statisticUsd.getBought()) + " / " + formatBigDecimal(statisticUsd.getSold()) + " / "
//                + formatBigDecimal(statisticUsd.getBalance()));
//        txtInfoEur.setText(formatBigDecimal(statisticEur.getBought()) + " / " + formatBigDecimal(statisticEur.getSold()) + " / "
//                + formatBigDecimal(statisticEur.getBalance()));
//        txtInfoGbp.setText(formatBigDecimal(statisticGbp.getBought()) + " / " + formatBigDecimal(statisticGbp.getSold()) + " / "
//                + formatBigDecimal(statisticGbp.getBalance()));
//    }

    public void loadStatistic(){
        List<Pair<Label, CurrencyType>> currencyPairs = new ArrayList<>();
        currencyPairs.add(new Pair<>(txtInfoUsd, CurrencyType.USD));
        currencyPairs.add(new Pair<>(txtInfoEur, CurrencyType.EUR));
        currencyPairs.add(new Pair<>(txtInfoGbp, CurrencyType.GBP));

        for (Pair<Label, CurrencyType> pair : currencyPairs) {
            CurrencyType currencyType = pair.getValue();
            Statistic statistics = loadStatistics(currencyType);
            Label label = pair.getKey();

            String text = formatBigDecimal(statistics.getBought()) + " / " +
                    formatBigDecimal(statistics.getSold()) + " / " +
                    formatBigDecimal(statistics.getBalance());

            label.setText(text);
        }
    }

    public void btnBuyClicked(ActionEvent actionEvent) {
        openBuySellWindow(true, txtUsers);
    }

    public void btnLogOut(ActionEvent actionEvent) {
        closeWindow(btnClose);
        WindowHelper.openWindow(WindowType.LOGIN_WINDOW);
    }

    public void btnSellClicked(ActionEvent actionEvent) {
        openBuySellWindow(false, txtUsers);
    }

    public void btnEuroClicked(ActionEvent actionEvent) {
        openStatisticWindow(CurrencyType.EUR, txtUsers);
    }

    public void btnDollarClicked(ActionEvent actionEvent) {
        openStatisticWindow(CurrencyType.USD, txtUsers);
    }

    public void btnPoundClicked(ActionEvent actionEvent) {
        openStatisticWindow(CurrencyType.GBP, txtUsers);
    }

    public void btnCopyClicked(ActionEvent actionEvent){
        copyToClipboard(prepareData());
    }

    public void btnMailClicked(ActionEvent actionEvent) {
        sendMail(prepareData());
    }


    private String prepareData(){
        String textToSave = "Transactions Statistics: \n";
        textToSave += "USD(Bought/Sold/Balance): " + txtInfoUsd.getText() + "\n";
        textToSave += "EUR(Bought/Sold/Balance): " +  txtInfoEur.getText() + "\n";
        textToSave += "GBP(Bought/Sold/Balance): " +  txtInfoGbp.getText();
        return textToSave;
    }
}
