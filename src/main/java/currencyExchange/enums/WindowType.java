package currencyExchange.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Możliwe do otwarcia okna.
 */
@Getter
@AllArgsConstructor
public enum WindowType {

    /** Główny ekran aplikacji */
    MAIN_WINDOW("mainWindow.fxml", "Kantor", 1200, 800),
    /** Okno logowania */
    LOGIN_WINDOW("loginWindow.fxml", "Logowanie", 300, 200),

    /** Okno statystyk */
    STATISTICS_WINDOW("statisticsWindow.fxml","Statystyki",300,200),
    /** Okno wymiany walut */
    EXCHANGE_WINDOW("exchangeWindow.fxml","Wymiana walut",300,200);

    private String viewPath;
    private String title;
    private Integer width;
    private Integer height;

    public void setTitle(String title) {
        this.title = title;
    }
}

