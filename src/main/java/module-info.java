module currencyExchange {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.logging.log4j;
    requires lombok;
    requires java.desktop;
    requires java.sql;

    opens currencyExchange to javafx.fxml;
    exports currencyExchange;
    exports currencyExchange.controller;
    opens currencyExchange.controller to javafx.fxml;
}