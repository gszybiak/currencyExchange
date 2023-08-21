package currencyExchange.helpers;

import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;
public class MsgHelper {
    /**
     * Displays an error message.
     *
     * @param header The header of the message to display
     * @param msg message to display
     */
    public static void showError(String header, String msg) {
        show(header, msg, Alert.AlertType.ERROR, "An error occured");
    }

    public static void showInfo(String header, String msg) {
        show(header, msg, Alert.AlertType.INFORMATION, "");
    }

    /**
     * Displays an alert on the screen.
     *
     * @param header The header of the message to display
     * @param msg message to display
     * @param type alert type
     * @param title title of the window containing the alert
     */
    private static void show(String header, String msg, Alert.AlertType type, String title) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    /**
     * Displays an alert with a choice of yes or no on the screen
     *
     * @param header The header of the message to display
     * @param msg message to display
     * @param title title of the window containing the alert
     */
    public static boolean showYesOrNoAlert(String header, String msg, String title, Node node) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(msg);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK)
            return true;
        else
            return false;
    }
}