package currencyExchange.helpers;

import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;
public class MsgHelper {

    public static void showError(String header, String msg) {
        show(header, msg, Alert.AlertType.ERROR, "An error occured");
    }

    public static void showInfo(String header, String msg) {
        show(header, msg, Alert.AlertType.INFORMATION, "");
    }

    private static void show(String header, String msg, Alert.AlertType type, String title) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    public static boolean showYesOrNoAlert(String header, String msg, String title, Node node) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(msg);
        Optional<ButtonType> result = alert.showAndWait();

        return result.get() == ButtonType.OK;
    }
}