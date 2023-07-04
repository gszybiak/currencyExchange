module currencyExchange {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.logging.log4j;
    requires lombok;
    requires java.desktop;

    opens currencyExchange to javafx.fxml;
    exports currencyExchange;
}