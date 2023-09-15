package currencyExchange.controller;

import currencyExchange.helpers.ApiNbpHelper;
import currencyExchange.helpers.MsgHelper;
import currencyExchange.helpers.TypeAndFormatHelper;
import javafx.event.ActionEvent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import static currencyExchange.helpers.DataManagementHelper.*;
import static currencyExchange.helpers.PropertiesHelper.loadPropertiesPath;
import static currencyExchange.helpers.TypeAndFormatHelper.printBigDecimalList;

public class StatisticsWindowController {

    /* Currency */
    public static Optional<String> currency;
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
    /* List checkboxes */
    public List<CheckBox> checkBoxes;

    public void initialize(){
        cbRange.valueProperty().addListener(x -> setStatistics());

        checkBoxes = Arrays.asList(chbMin, chbMax, chbAvg, chbDays);
        checkBoxes.forEach((CheckBox checkbox) -> checkbox.setOnAction(event -> checkOnlyOneCheckbox(checkbox)));
    }

    public void checkOnlyOneCheckbox(CheckBox selectedCheckbox){
        checkBoxes.stream()
                .filter(checkbox -> checkbox != selectedCheckbox && checkbox.isSelected())
                .forEach(checkbox -> checkbox.setSelected(false));
    }

    private void setStatistics() {
        Integer countDays = Integer.parseInt(cbRange.getValue().toString());
        if(countDays == 1)
            txtStatisctics.setText((ApiNbpHelper.loadTodayDataFromBank(currency)).toString());
        else
            txtStatisctics.setText(printBigDecimalList(ApiNbpHelper.loadRangePeriodDataFromBank(currency, countDays)));
    }

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

    public void btnCopyClicked(ActionEvent actionEvent){
        copyToClipboard(prepareData());
    }

    public void btnSaveToFileClicked(ActionEvent actionEvent){
        String textStatistics = txtStatisctics.getText();
        if(textStatistics.isBlank()){
            MsgHelper.showError("Select range", "No data to save.");
            return;
        }
        Properties propertiesPath = loadPropertiesPath();
        Path filePath = Path.of(propertiesPath.getProperty("path"));
        if(saveToFile(prepareData(), filePath))
            MsgHelper.showInfo("Data saved", "File path:\n" + filePath.toString());
        else
            return;
    }

    public void btnMailClicked(ActionEvent actionEvent) {
        String textStatistics = txtStatisctics.getText();
        if(textStatistics.isBlank()){
            MsgHelper.showError("Select range", "No data to send.");
            return;
        }
        sendMail(prepareData());
    }
}