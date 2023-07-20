package currencyExchange.helpers;

import currencyExchange.App;
import currencyExchange.enums.WindowType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WindowHelper {

    private static final Logger windowHelperLog = LogManager.getLogger(WindowHelper.class);

    /**
     * Otwiera wybrane okno.
     *
     * @param windowType typ okna do otwarcia
     */
    public static void openWindow(WindowType windowType) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(windowType.getViewPath()));
            Scene scene = new Scene(fxmlLoader.load(), windowType.getWidth(), windowType.getHeight());
            Stage stage = new Stage();
            stage.setTitle(windowType.getTitle());
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            MsgHelper.showError(String.format("Błąd otwierania ekranu %s", windowType.getViewPath()), e.getLocalizedMessage());
            windowHelperLog.error(String.format("Błąd otwierania okna %s", windowType.getViewPath()), new Exception(e.getMessage()));
        }
    }

    /**
     * Otwiera wybrane okno z ustawieniem wielkości okna
     *
     * @param windowType typ okna do otwarcia
     *                   width - szerokość okna
     *                   height - wysokość okna
     */
    public static void openWindow(WindowType windowType, int width, int height) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(windowType.getViewPath()));
            Scene scene = new Scene(fxmlLoader.load(), windowType.getWidth(), windowType.getHeight());
            Stage stage = new Stage();
            stage.setTitle(windowType.getTitle());
            stage.setScene(scene);
            stage.setWidth(width);
            stage.setHeight(height);
            stage.show();
        } catch (Exception e) {
            MsgHelper.showError(String.format("Błąd otwierania ekranu %s", windowType.getViewPath()), e.getLocalizedMessage());
            windowHelperLog.error(String.format("Błąd otwierania okna %s", windowType.getViewPath()), new Exception(e.getMessage()));
            e.printStackTrace();
        }
    }

    /**
     * Metoda ta zamyka okno po przekazaniu jednego z jego komponentów.
     *
     * @param cmp komponent z ekrenu do zamknięcia
     */
    public static void closeWindow(Node cmp) {
        try {
            ((Stage) cmp.getScene().getWindow()).close();
        } catch (Exception e) {
            MsgHelper.showError(String.format("Błąd zamykania ekranu ",e.getLocalizedMessage()),"Spróbuj ponownie");
            windowHelperLog.error("Błąd zamykania okna", new Exception(e.getMessage()));
        }
    }
}