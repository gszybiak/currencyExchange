package currencyExchange.controller;

import currencyExchange.database.DatabaseConnection;
import currencyExchange.database.DatabaseOperationCustomers;
import currencyExchange.database.DatabaseOperationTransactions;
import currencyExchange.enums.CurrencyType;
import currencyExchange.helpers.ApiNbpHelper;
import currencyExchange.helpers.MsgHelper;
import currencyExchange.helpers.TypeAndFormatHelper;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.sql.Date;
import java.time.LocalDate;

public class ExchangeWindowController {

    /* Waluta */
    public ComboBox cbCurrency;
    /* Kurs */
    public TextField txtCurrencyRate;
    /* Ilość */
    public TextField txtQuantityCurrency;
    /* Wartość */
    public TextField txtValueCurrency;
    /* Przycisk Kup/Sprzedaj */
    public Button btnBuyOrSell;
    /* Flaga czy ekran otwarty w trybie Kup */
    public static boolean buyMode = false;

    public void initialize(){

        btnBuyOrSell.setText(buyMode ? "Kup" : "Sprzedaj");
        cbCurrency.valueProperty().addListener(x -> setRest());
        txtQuantityCurrency.textProperty().addListener(x -> setValue());
        txtCurrencyRate.textProperty().addListener(x -> setValue());
    }

    private void setRest(){
        String currencyName = cbCurrency.getValue().toString();
        switch (currencyName) {
            case "EURO":
                txtCurrencyRate.setText(Double.toString(ApiNbpHelper.loadTodayDataFromBank(CurrencyType.DOLAR.getName())));
                break;
            case "DOLAR":
                txtCurrencyRate.setText(Double.toString(ApiNbpHelper.loadTodayDataFromBank(CurrencyType.EURO.getName())));
                break;
            case "FUNT":
                txtCurrencyRate.setText(Double.toString(ApiNbpHelper.loadTodayDataFromBank(CurrencyType.FUNT.getName())));
                break;
            default:
                break;
        }
    }

    private void setValue(){
        if(!txtCurrencyRate.getText().isBlank() && !txtQuantityCurrency.getText().isBlank()) {
            Double value = TypeAndFormatHelper.convertToDoubleWithFormatter(txtCurrencyRate.getText())
                    * TypeAndFormatHelper.convertToDoubleWithFormatter(txtQuantityCurrency.getText());
            txtValueCurrency.setText(value.toString());
        }
    }

    public void btnBuyOrSellClicked(ActionEvent actionEvent) {
        if(cbCurrency.getValue() == null){
            MsgHelper.showError("Uzupełnij dane transakcji.", "Proszę o podanie waluty");
            return;
        }

        if(txtQuantityCurrency.getText().isBlank()){
            MsgHelper.showError("Uzupełnij dane transakcji.", "Proszę o podanie ilości");
            return;
        }
        insertTransactionToDatabase();
    }

    public void insertTransactionToDatabase(){
        DatabaseConnection databaseConnection = new DatabaseConnection();
        DatabaseOperationTransactions databaseOperationTransactions = new DatabaseOperationTransactions();
        databaseOperationTransactions.addTransaction(LoginWindowController.customer.getId(), Date.valueOf(LocalDate.now()), Double.parseDouble(txtValueCurrency.getText()),
                CurrencyType.getNameForKey(cbCurrency.getValue().toString()), buyMode ? "buy" : "sell",  Double.parseDouble(txtCurrencyRate.getText()), databaseConnection.getStatement());
        databaseConnection.disconnect();
    }
}
