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
import javafx.stage.WindowEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;

public class WindowHelper {

    private static final Logger windowHelperLog = LogManager.getLogger(WindowHelper.class);
    public static Toolkit toolkit = Toolkit.getDefaultToolkit();
    public static Dimension screenSize = toolkit.getScreenSize();

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

    public static void closeWindow(Node cmp) {
        try {
            ((Stage) cmp.getScene().getWindow()).close();
        } catch (Exception e) {
            MsgHelper.showError(String.format("Error closing windows ",e.getLocalizedMessage()),"try again");
            windowHelperLog.error("Error closing windows", new Exception(e.getMessage()));
        }
    }

    public static void openStatisticWindow(CurrencyType currencyType, Node cmp) {
        StatisticsWindowController.currency = java.util.Optional.ofNullable(currencyType.getName());
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
            stage.setOnCloseRequest((WindowEvent event) -> {
                WindowHelper.openWindow(WindowType.MAIN_WINDOW, screenSize.width, screenSize.height);
            });
        } catch (Exception e) {
            MsgHelper.showError(String.format("Error opening windows %s", windowType.getViewPath()), e.getLocalizedMessage());
            windowHelperLog.error(String.format("Error opening windows %s", windowType.getViewPath()), new Exception(e.getMessage()));
            e.printStackTrace();
        }
    }

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
            stage.setOnCloseRequest((WindowEvent event) -> {
                WindowHelper.openWindow(WindowType.MAIN_WINDOW, screenSize.width, screenSize.height);
            });
        } catch (Exception e) {
            MsgHelper.showError(String.format("Error opening windows %s", windowType.getViewPath()), e.getLocalizedMessage());
            windowHelperLog.error(String.format("Error opening windows %s", windowType.getViewPath()), new Exception(e.getMessage()));
            e.printStackTrace();
        }
    }
}