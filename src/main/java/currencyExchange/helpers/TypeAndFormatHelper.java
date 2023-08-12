package currencyExchange.helpers;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TypeAndFormatHelper {

    public static double convertToDoubleWithFormatter(String numberString) {
            double number = Double.parseDouble(numberString);
            return Math.round(number * 100.0) / 100.0;
    }

    public static String printDoubleList(List<Double> list) {
        String listCurrency = "";
        for (Double number : list) {
            listCurrency += number.toString() + "\n";
        }
        return listCurrency;
    }

    public static List<Double> parseStringToDoubleList(String input) {
        List<Double> doubleList = new ArrayList<>();
        String[] lines = input.split("\n");

        for (String line : lines) {
            double value = Double.parseDouble(line);
            doubleList.add(value);
        }

        return doubleList;
    }

    public static String formatDouble(Double number) {
        if (number == null)
            return "0.00";
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());
        symbols.setDecimalSeparator(',');

        DecimalFormat df = new DecimalFormat("#0.00", symbols);
        return df.format(number);
    }
}
