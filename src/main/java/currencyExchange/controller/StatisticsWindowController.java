package currencyExchange.controller;

import currencyExchange.email.SendEmail;
import currencyExchange.enums.WindowType;
import currencyExchange.helpers.ApiNbpHelper;
import currencyExchange.helpers.MsgHelper;
import currencyExchange.helpers.TypeAndFormatHelper;
import currencyExchange.helpers.WindowHelper;
import javafx.event.ActionEvent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import static currencyExchange.helpers.DataManagementHelper.*;
import static currencyExchange.helpers.TypeAndFormatHelper.printBigDecimalList;
import static currencyExchange.helpers.WindowHelper.screenSize;

public class StatisticsWindowController {

    /* Currency */
    public static String currency;
    /* Range */
    public ComboBox cbRange;
    /* Statistics */
    public TextArea txtStatisctics;
    /* Min */
    public CheckBox chbMin;
    /* Max */
    public CheckBox chbMax;
    /* Average */
    public CheckBox chbAvg;
    /* Days */
    public CheckBox chbDays;

    public void initialize(){
        cbRange.valueProperty().addListener(x -> setStatistics());
        chbMin.setOnAction(event -> checkOnlyOneCheckbox(chbMin));
        chbMax.setOnAction(event -> checkOnlyOneCheckbox(chbMax));
        chbAvg.setOnAction(event -> checkOnlyOneCheckbox(chbAvg));
        chbDays.setOnAction(event -> checkOnlyOneCheckbox(chbDays));
        ((Stage) chbMin.getScene().getWindow()).setOnCloseRequest((WindowEvent event) -> {
            WindowHelper.openWindow(WindowType.MAIN_WINDOW, screenSize.height, screenSize.width);
        });
    }

    /**
     * Method that sets only one checkbox checked
     */
    public void checkOnlyOneCheckbox(CheckBox selectedCheckbox){
        if (selectedCheckbox.isSelected()) {
            if (selectedCheckbox != chbMin) chbMin.setSelected(false);
            if (selectedCheckbox != chbMax) chbMax.setSelected(false);
            if (selectedCheckbox != chbAvg) chbAvg.setSelected(false);
            if (selectedCheckbox != chbDays) chbDays.setSelected(false);
        }
    }

    /**
     * Method that completes statistics data
     */
    private void setStatistics() {
        Integer countDays = Integer.parseInt(cbRange.getValue().toString());
        switch (countDays) {
            case 1:
                txtStatisctics.setText((ApiNbpHelper.loadTodayDataFromBank(currency)).toString());
                break;
            case 5:
                txtStatisctics.setText(printBigDecimalList(ApiNbpHelper.loadRangePeriodDataFromBank(currency, 5)));
                break;
            case 10:
                txtStatisctics.setText(printBigDecimalList(ApiNbpHelper.loadRangePeriodDataFromBank(currency, 10)));
                break;
            case 30:
                txtStatisctics.setText(printBigDecimalList(ApiNbpHelper.loadRangePeriodDataFromBank(currency, 30)));
                break;
            default:
                break;
        }
    }

    /**
     * Method to prepare the data for use
     */
    public String prepareData(){
        String textStatistics = txtStatisctics.getText();

        List<BigDecimal> bigDecimalValues = TypeAndFormatHelper.parseStringToBigDecimalList(textStatistics);
        if(chbMin.isSelected())
            return bigDecimalValues.stream().min(BigDecimal::compareTo).orElse(BigDecimal.ZERO).toString();
        else if(chbMax.isSelected())
            return bigDecimalValues.stream().max(BigDecimal::compareTo).orElse(BigDecimal.ZERO).toString();
        else if(chbAvg.isSelected())
            return BigDecimal.valueOf(bigDecimalValues.stream().mapToDouble(BigDecimal::doubleValue).average().orElse(0.0)).toString();
        else if(chbDays.isSelected())
            return textStatistics;
        else
            return textStatistics;
    }

    /**
     * Method after clicking the "Copy to clipboard" button
     */
    public void btnCopyClicked(ActionEvent actionEvent){
        copyToClickboard(prepareData());
    }

    /**
     * Method after clicking the "Save to file" button
     */
    public void btnSaveToFileClicked(ActionEvent actionEvent){
        String textStatistics = txtStatisctics.getText();
        if(textStatistics.isBlank()){
            MsgHelper.showError("Select range", "No data to save.");
            return;
        }
        saveToFile(prepareData());
    }

    /**
     * Method Send Email
     */
    public void btnMailClicked(ActionEvent actionEvent) {
        String textStatistics = txtStatisctics.getText();
        if(textStatistics.isBlank()){
            MsgHelper.showError("Select range", "No data to send.");
            return;
        }
        sendMail(prepareData());
    }
}