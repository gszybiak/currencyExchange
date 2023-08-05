package currencyExchange.controller;

import currencyExchange.enums.CurrencyType;
import currencyExchange.helpers.ApiNbpHelper;
import currencyExchange.helpers.TypeAndFormatHelper;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

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

    }
}
