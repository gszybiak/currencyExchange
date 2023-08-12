package currencyExchange.controller;

import currencyExchange.email.SendEmail;
import currencyExchange.helpers.ApiNbpHelper;
import currencyExchange.helpers.MsgHelper;
import currencyExchange.helpers.TypeAndFormatHelper;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import static currencyExchange.helpers.TypeAndFormatHelper.printDoubleList;

public class StatisticsWindowController {

    /* Waluta */
    public static String currency;
    /* Zakres */
    public ComboBox cbRange;
    /* Statystyka */
    public TextArea txtStatisctics;
    /* Minimalna */
    public CheckBox chbMin;
    /* Maksymalna */
    public CheckBox chbMax;
    /* Średnia */
    public CheckBox chbAvg;
    /* Dni */
    public CheckBox chbDays;

    public void initialize(){
        cbRange.valueProperty().addListener(x -> setStatistics());
        chbMin.setOnAction(event -> checkOnlyOneCheckbox(chbMin));
        chbMax.setOnAction(event -> checkOnlyOneCheckbox(chbMax));
        chbAvg.setOnAction(event -> checkOnlyOneCheckbox(chbAvg));
        chbDays.setOnAction(event -> checkOnlyOneCheckbox(chbDays));
    }

    public void checkOnlyOneCheckbox(CheckBox selectedCheckbox){
        if (selectedCheckbox.isSelected()) {
            if (selectedCheckbox != chbMin) chbMin.setSelected(false);
            if (selectedCheckbox != chbMax) chbMax.setSelected(false);
            if (selectedCheckbox != chbAvg) chbAvg.setSelected(false);
            if (selectedCheckbox != chbDays) chbDays.setSelected(false);
        }
    }

    private void setStatistics() {
        Integer countDays = Integer.parseInt(cbRange.getValue().toString());
        switch (countDays) {
            case 1:
                txtStatisctics.setText(Double.toString(ApiNbpHelper.loadTodayDataFromBank(currency)));
                break;
            case 5:
                txtStatisctics.setText(printDoubleList(ApiNbpHelper.loadRangePeriodDataFromBank(currency, 5)));
                break;
            case 10:
                txtStatisctics.setText(printDoubleList(ApiNbpHelper.loadRangePeriodDataFromBank(currency, 10)));
                break;
            case 30:
                txtStatisctics.setText(printDoubleList(ApiNbpHelper.loadRangePeriodDataFromBank(currency, 30)));
                break;
            default:
                break;
        }
    }
    public void btnCalculateClicked(ActionEvent actionEvent) {

    }

    /**
     * Metoda przygotowująca dane do użycia
     */
    public String prepareData(){
        String textStatistics = txtStatisctics.getText();
        if(textStatistics.isBlank())
            return "";

        List<Double> doubleValues = TypeAndFormatHelper.parseStringToDoubleList(textStatistics);
        if(chbMin.isSelected())
            return doubleValues.stream().min(Double::compare).orElse(0.0).toString();
        else if(chbMax.isSelected())
            return doubleValues.stream().max(Double::compare).orElse(0.0).toString();
        else if(chbAvg.isSelected())
            return Double.toString(doubleValues.stream().mapToDouble(Double::doubleValue).average().orElse(0.0));
        else if(chbDays.isSelected())
            return textStatistics;
        else
            return textStatistics;
    }

    /**
     * Metoda po kliknięciu przycisku "Kopiuj do schowka"
     */
    public void btnCopyClicked(ActionEvent actionEvent){
        String textToSave = prepareData();
        StringSelection selection = new StringSelection(textToSave);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, selection);
    }

    /**
     * Metoda po kliknięciu przycisku "Zapisz do pliku"
     */
    public void btnSaveToFileClicked(ActionEvent actionEvent){
        String textStatistics = txtStatisctics.getText();
        if(textStatistics.isBlank()){
            MsgHelper.showError("Wybierz zakres ", "Brak danych do zapisania.");
            return;
        }

        String textToSave = prepareData();
        Path filePath = Path.of("C:/Users/Gabriel/Desktop/CurrencyExchange/statistics.txt");
        try {
            Files.write(filePath, textToSave.getBytes(), StandardOpenOption.CREATE);
        } catch (IOException e) {
            e.printStackTrace();
        }
        MsgHelper.showInfo("Dane zapisane ", "Ścieżka do pliku:\n" + filePath.toString());
    }

    /**
     * Metoda Wyślij maila
     */
    public void btnMailClicked(ActionEvent actionEvent) {
        String textStatistics = txtStatisctics.getText();
        if(textStatistics.isBlank()){
            MsgHelper.showError("Wybierz zakres ", "Brak danych do wysłania.");
            return;
        }

        String textToSave = prepareData();
        TextInputDialog dialog = new TextInputDialog("Adres email");
        dialog.setTitle("Wysyłanie email");
        dialog.setHeaderText("Podaj email:");
        dialog.setContentText("Email:");
        Optional<String> result = dialog.showAndWait();

        result.ifPresent(name -> {
            String encodedString = Base64.getEncoder().encodeToString(name.getBytes());
            byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
            String decodedString = new String(decodedBytes);
            SendEmail.sendEmail(decodedString, textToSave);
        });
    }

}
