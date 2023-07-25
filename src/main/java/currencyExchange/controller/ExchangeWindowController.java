package currencyExchange.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public class ExchangeWindowController {

    public Button btnBuyOrSell;
    public static boolean buyMode = false;

    public void initialize(){
        btnBuyOrSell.setText(buyMode ? "Kup" : "Sprzedaj");
    }

    public void btnBuyOrSellClicked(ActionEvent actionEvent) {

    }
}
