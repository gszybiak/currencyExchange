package currencyExchange.controller;

import currencyExchange.helpers.ApiNbpHelper;
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
    public Button btnBuyOrSell;
    public static boolean buyMode = false;

    public void initialize(){

        btnBuyOrSell.setText(buyMode ? "Kup" : "Sprzedaj");
        cbCurrency.valueProperty().addListener(x -> setRest());
        txtQuantityCurrency.textProperty().addListener(x -> setValue());
        txtCurrencyRate.textProperty().addListener(x -> setValue());
    }

    private void setRest(){
        Double priceUsd = ApiNbpHelper.loadDataFromBank("usd");
        Double priceEur = ApiNbpHelper.loadDataFromBank("eur");
        Double priceGbp = ApiNbpHelper.loadDataFromBank("gbp");

        String currencyName = cbCurrency.getValue().toString();
        switch (currencyName) {
            case "EURO":
                txtCurrencyRate.setText(Double.toString(priceEur));
                break;
            case "DOLAR":
                txtCurrencyRate.setText(Double.toString(priceUsd));
                break;
            case "FUNT":
                txtCurrencyRate.setText(Double.toString(priceGbp));
                break;
            default:
                break;
        }
    }

    private void setValue(){
        if(!txtCurrencyRate.getText().isBlank() && !txtQuantityCurrency.getText().isBlank()) {
            Double Value = Double.parseDouble(txtCurrencyRate.getText()) * Double.parseDouble(txtQuantityCurrency.getText());
            txtValueCurrency.setText(Value.toString());
        }
    }

    public void btnBuyOrSellClicked(ActionEvent actionEvent) {

    }
}
