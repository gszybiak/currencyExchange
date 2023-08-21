package currencyExchange.helpers;

import currencyExchange.email.SendEmail;
import javafx.scene.control.TextInputDialog;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Base64;
import java.util.Optional;

public class DataManagementHelper {

    /**
     * Method Send Email
     *
     * @param textToSend
     */
    public static void sendMail(String textToSend){
        TextInputDialog dialog = new TextInputDialog("E-mail adress");
        dialog.setTitle("Sending email");
        dialog.setHeaderText("Enter email:");
        dialog.setContentText("Email:");
        Optional<String> result = dialog.showAndWait();

        result.ifPresent(name -> {
            String encodedString = Base64.getEncoder().encodeToString(name.getBytes());
            byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
            String decodedString = new String(decodedBytes);
            SendEmail.sendEmail(decodedString, textToSend);
        });
    }

    /**
     * Method after clicking the "Copy to clipboard" button
     *
     * @param textToCopy
     */
    public static void copyToClickboard(String textToCopy){
        StringSelection selection = new StringSelection(textToCopy);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, selection);
    }

    /**
     * Method after clicking the "Save to file" button
     *
     * @param textToSave
     */
    public static void saveToFile(String textToSave){
        Path filePath = Path.of("C:/Users/Gabriel/Desktop/CurrencyExchange/statistics.txt");
        try {
            Files.write(filePath, textToSave.getBytes(), StandardOpenOption.CREATE);
        } catch (IOException e) {
            e.printStackTrace();
        }
        MsgHelper.showInfo("Data saved", "File path:\n" + filePath.toString());
    }
}
