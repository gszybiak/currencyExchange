package currencyExchange.helpers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class TypeAndFormatHelper {

    public static BigDecimal convertToBigDecimalWithFormatter(String numberString) {
        BigDecimal number = new BigDecimal(numberString);
        return number.setScale(2, RoundingMode.HALF_UP);
    }
    public static String printBigDecimalList(List<BigDecimal> list) {
        return list.stream()
                .map(BigDecimal::toString)
                .collect(Collectors.joining("\n"));
    }
    public static List<BigDecimal> parseStringToBigDecimalList(String input) {
        List<BigDecimal> bigDecimalList = new ArrayList<>();
        String[] lines = input.split("\n");

        for (String line : lines) {
            BigDecimal value = new BigDecimal(line);
            bigDecimalList.add(value);
        }

        return bigDecimalList;
    }

    public static String formatBigDecimal(BigDecimal number) {
        if (number == null)
            return "0.00";
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());
        symbols.setDecimalSeparator(',');

        DecimalFormat df = new DecimalFormat("#0.00", symbols);
        return df.format(number);
    }
}