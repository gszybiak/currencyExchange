package currencyExchange.controller;

import currencyExchange.helpers.ApiNbpHelper;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class ExchangeWindowController {

    public ComboBox cbCurrency;
    public TextField txtCurrencyRate;
    public Button btnBuyOrSell;
    public static boolean buyMode = false;

    public void initialize(){

        btnBuyOrSell.setText(buyMode ? "Kup" : "Sprzedaj");
        cbCurrency.valueProperty().addListener(x -> setRest());
    }

    private void setRest(){
        Double priceUsd = ApiNbpHelper.loadDataFromBank("usd");
        Double priceEur = ApiNbpHelper.loadDataFromBank("eur");
        Double priceGbp = ApiNbpHelper.loadDataFromBank("gbp");

        //przerobić na switch, dodać pusty element do combobox
        if(cbCurrency.getValue().equals("EURO"))
            txtCurrencyRate.setText(Double.toString(priceEur));
        else if(cbCurrency.getValue().equals("DOLAR"))
            txtCurrencyRate.setText(Double.toString(priceUsd));
        else if(cbCurrency.getValue().equals("FUNT"))
            txtCurrencyRate.setText(Double.toString(priceGbp));
    }

    public void btnBuyOrSellClicked(ActionEvent actionEvent) {

    }
}
