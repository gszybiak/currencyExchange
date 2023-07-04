package currencyExchange.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Możliwe do otwarcia okna.
 */
@Getter
@AllArgsConstructor
public enum WindowType {

    /** Główny ekran aplikacji */
    MAIN_WINDOW("mainWindow.fxml", "Kantor", 1200, 800),
    /** Okno logowania */
    LOGIN_WINDOW("loginWindow.fxml", "Logowanie", 300, 200);

    private String viewPath;
    private String title;
    private Integer width;
    private Integer height;
}

