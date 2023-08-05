package currencyExchange.helpers;

import java.util.ArrayList;
import java.util.List;

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
}
