package currencyExchange.helpers;

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

import static currencyExchange.email.SendEmail.sendEmail;

public class DataManagementHelper {

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
            if(sendEmail(decodedString, textToSend))
                MsgHelper.showInfo("Mail sent", "Mail was sent");
        });
    }

    public static void copyToClipboard(String textToCopy) {
        try {
            StringSelection selection = new StringSelection(textToCopy);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(selection, selection);
        } catch (Exception e) {
            e.printStackTrace();
        }
        MsgHelper.showInfo("Data saved", "text copied to the clipboard");
    }

    public static boolean saveToFile(String textToSave, Path filePath){
        try {
            Files.write(filePath, textToSave.getBytes(), StandardOpenOption.CREATE);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
