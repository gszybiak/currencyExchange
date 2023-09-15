package currencyExchange.controller;

import currencyExchange.database.DatabaseConnection;
import currencyExchange.database.DatabaseOperationTransactions;
import currencyExchange.helpers.ApiNbpHelper;
import currencyExchange.helpers.MsgHelper;
import currencyExchange.helpers.TypeAndFormatHelper;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;

import static currencyExchange.enums.CurrencyType.getNameForKey;

public class ExchangeWindowController {

    /* Currency */
    public ComboBox cbCurrency;
    /* Rate */
    public TextField txtCurrencyRate;
    /* Quantity */
    public TextField txtQuantityCurrency;
    /* Value */
    public TextField txtValueCurrency;
    /* Button Buy/Sell */
    public Button btnBuyOrSell;
    /* Flag if screen opened in Buy mode */
    public static boolean buyMode = false;

    public void initialize(){
        btnBuyOrSell.setText(buyMode ? "Buy" : "Sell");
        cbCurrency.valueProperty().addListener(x -> setRest());
        txtQuantityCurrency.textProperty().addListener(x -> setValue());
        txtCurrencyRate.textProperty().addListener(x -> setValue());
    }

    private void setRest(){
        String currencyName = cbCurrency.getValue().toString();
        txtCurrencyRate.setText((ApiNbpHelper.loadTodayDataFromBank(getNameForKey(currencyName)).toString()));
    }

    private void setValue(){
        if(!txtCurrencyRate.getText().isBlank() && !txtQuantityCurrency.getText().isBlank()) {
            BigDecimal value = TypeAndFormatHelper.convertToBigDecimalWithFormatter(txtCurrencyRate.getText())
                    .multiply(TypeAndFormatHelper.convertToBigDecimalWithFormatter(txtQuantityCurrency.getText()));
            txtValueCurrency.setText(value.toString());
        }
    }

    public void btnBuyOrSellClicked(ActionEvent actionEvent) {
        if(cbCurrency.getValue() == null){
            MsgHelper.showError("Fill in transaction details.", "Please enter currency");
            return;
        }

        if(txtQuantityCurrency.getText().isBlank()){
            MsgHelper.showError("Fill in transaction details.", "Please enter quantity");
            return;
        }
        insertTransactionToDatabase();
    }

    public void insertTransactionToDatabase(){
        DatabaseConnection databaseConnection = new DatabaseConnection();
        DatabaseOperationTransactions databaseOperationTransactions = new DatabaseOperationTransactions();
        databaseOperationTransactions.addTransaction(LoginWindowController.customer.getId(), Date.valueOf(LocalDate.now()), new BigDecimal(txtValueCurrency.getText()),
                getNameForKey(cbCurrency.getValue().toString()), buyMode ? "buy" : "sell",  new BigDecimal(txtCurrencyRate.getText()), databaseConnection.getStatement());
        databaseConnection.disconnect();
    }
}
