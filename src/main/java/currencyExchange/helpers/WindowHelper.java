package currencyExchange.helpers;

import currencyExchange.App;
import currencyExchange.controller.ExchangeWindowController;
import currencyExchange.controller.StatisticsWindowController;
import currencyExchange.enums.CurrencyType;
import currencyExchange.enums.WindowType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;

public class WindowHelper {

    private static final Logger windowHelperLog = LogManager.getLogger(WindowHelper.class);
    public static Toolkit toolkit = Toolkit.getDefaultToolkit();
    public static Dimension screenSize = toolkit.getScreenSize();

    /**
     * Method opens the selected window
     *
     * @param windowType type of window to open
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
            MsgHelper.showError(String.format("Error opening windows %s", windowType.getViewPath()), e.getLocalizedMessage());
            windowHelperLog.error(String.format("Error opening windows %s", windowType.getViewPath()), new Exception(e.getMessage()));
        }
    }

    /**
     * Method opens the selected window with the window size setting
     *
     * @param windowType type of window
     *                   width
     *                   height
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
            MsgHelper.showError(String.format("Error opening windows %s", windowType.getViewPath()), e.getLocalizedMessage());
            windowHelperLog.error(String.format("Error opening windows %s", windowType.getViewPath()), new Exception(e.getMessage()));
            e.printStackTrace();
        }
    }

    /**
     * Method closes the window after passing one of its components
     *
     * @param cmp - component from the screen to close
     */
    public static void closeWindow(Node cmp) {
        try {
            ((Stage) cmp.getScene().getWindow()).close();
        } catch (Exception e) {
            MsgHelper.showError(String.format("Error closing windows ",e.getLocalizedMessage()),"try again");
            windowHelperLog.error("Error closing windows", new Exception(e.getMessage()));
        }
    }

    /**
     * Method opens the statistic window
     *
     * @param currencyType
     * @param cmp
     */
    public static void openStatisticWindow(CurrencyType currencyType, Node cmp) {
        StatisticsWindowController.currency = currencyType.getName();
        ((Stage) cmp.getScene().getWindow()).close();
        WindowType windowType = WindowType.STATISTICS_WINDOW;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(windowType.getViewPath()));
            Scene scene = new Scene(fxmlLoader.load(), windowType.getWidth(), windowType.getHeight());
            Stage stage = new Stage();
            stage.setTitle(currencyType.getName() + " statistics");
            stage.setScene(scene);
            stage.setWidth(screenSize.width);
            stage.setHeight(screenSize.height);
            stage.show();
        } catch (Exception e) {
            MsgHelper.showError(String.format("Error opening windows %s", windowType.getViewPath()), e.getLocalizedMessage());
            windowHelperLog.error(String.format("Error opening windows %s", windowType.getViewPath()), new Exception(e.getMessage()));
            e.printStackTrace();
        }
    }

    /**
     * Method opens the buy/sell window
     *
     * @param buyMode
     */
    public static void openBuySellWindow(boolean buyMode ,Node cmp) {
        ExchangeWindowController.buyMode = buyMode;
        WindowType.EXCHANGE_WINDOW.setTitle(buyMode ? "Buy" : "Sell" + " currency");
        ((Stage) cmp.getScene().getWindow()).close();

        WindowType windowType = WindowType.EXCHANGE_WINDOW;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(windowType.getViewPath()));
            Scene scene = new Scene(fxmlLoader.load(), windowType.getWidth(), windowType.getHeight());
            Stage stage = new Stage();
            stage.setTitle(buyMode ? "Buy" : "Sell");
            stage.setScene(scene);
            stage.setWidth(screenSize.width);
            stage.setHeight(screenSize.height);
            stage.show();
        } catch (Exception e) {
            MsgHelper.showError(String.format("Error opening windows %s", windowType.getViewPath()), e.getLocalizedMessage());
            windowHelperLog.error(String.format("Error opening windows %s", windowType.getViewPath()), new Exception(e.getMessage()));
            e.printStackTrace();
        }
    }
}