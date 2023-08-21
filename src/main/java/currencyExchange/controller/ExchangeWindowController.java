package currencyExchange.controller;

import currencyExchange.database.DatabaseConnection;
import currencyExchange.database.DatabaseOperationTransactions;
import currencyExchange.enums.WindowType;
import currencyExchange.helpers.ApiNbpHelper;
import currencyExchange.helpers.MsgHelper;
import currencyExchange.helpers.TypeAndFormatHelper;
import currencyExchange.helpers.WindowHelper;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;

import static currencyExchange.enums.CurrencyType.getNameForKey;
import static currencyExchange.helpers.WindowHelper.screenSize;

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
        ((Stage) txtCurrencyRate.getScene().getWindow()).setOnCloseRequest((WindowEvent event) -> {
            WindowHelper.openWindow(WindowType.MAIN_WINDOW, screenSize.width, screenSize.height);
        });
    }

    /**
     * Method that downloads the currency and adds its value
     */
    private void setRest(){
        String currencyName = cbCurrency.getValue().toString();
        txtCurrencyRate.setText((ApiNbpHelper.loadTodayDataFromBank(getNameForKey(currencyName)).toString()));
    }

    /**
     * Method that converts the currency exchange value
     */
    private void setValue(){
        if(!txtCurrencyRate.getText().isBlank() && !txtQuantityCurrency.getText().isBlank()) {
            BigDecimal value = TypeAndFormatHelper.convertToBigDecimalWithFormatter(txtCurrencyRate.getText())
                    .multiply(TypeAndFormatHelper.convertToBigDecimalWithFormatter(txtQuantityCurrency.getText()));
            txtValueCurrency.setText(value.toString());
        }
    }

    /**
     * Method that is called when the buy/sell button is clicked
     */
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


    /**
     * Method that adds transaction information to the database
     */
    public void insertTransactionToDatabase(){
        DatabaseConnection databaseConnection = new DatabaseConnection();
        DatabaseOperationTransactions databaseOperationTransactions = new DatabaseOperationTransactions();
        databaseOperationTransactions.addTransaction(LoginWindowController.customer.getId(), Date.valueOf(LocalDate.now()), new BigDecimal(txtValueCurrency.getText()),
                getNameForKey(cbCurrency.getValue().toString()), buyMode ? "buy" : "sell",  new BigDecimal(txtCurrencyRate.getText()), databaseConnection.getStatement());
        databaseConnection.disconnect();
    }
}
