package currencyExchange.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum WindowType {

    /** The main screen of the application */
    MAIN_WINDOW("view/mainWindow.fxml", "Currency exchange", 1200, 800),
    /** Login window */
    LOGIN_WINDOW("view/loginWindow.fxml", "Login", 300, 200),

    /** Statistics window */
    STATISTICS_WINDOW("view/statisticsWindow.fxml","Statistics",300,200),
    /** Exchange window */
    EXCHANGE_WINDOW("view/exchangeWindow.fxml","Currency exchange",300,200);

    private String viewPath;
    private String title;
    private Integer width;
    private Integer height;

    /**
     * Set title of window
     */
    public void setTitle(String title) {
        this.title = title;
    }
}

