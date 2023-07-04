package currencyExchange;

import currencyExchange.enums.WindowType;
import currencyExchange.helpers.WindowHelper;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    /**
     * Otwiera otwiera ekran logowania
     */
    @Override
    public void start(Stage stage) {
        WindowHelper.openWindow(WindowType.LOGIN_WINDOW);
    }

    /**
     * Metoda startowa aplikacji.
     */
    public static void main(String[] args){
        launch();
    }
}
