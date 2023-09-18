package currencyExchange.helpers;

import currencyExchange.database.DatabaseConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ApiNbpHelper {
    private static final Logger apiNbpLog = LogManager.getLogger(DatabaseConnection.class);
    public static BigDecimal loadTodayDataFromBank(Optional<String> currency) {
        BigDecimal currencyPrice = null;
        try {
            String apiUrl = "http://api.nbp.pl/api/exchangerates/rates/a/" + currency.get() + "/?format=json";

            HttpURLConnection connection = prepareConnection(apiUrl);
            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                currencyPrice = getOnePriceFromJson(readResponse(reader));
            } else
                apiNbpLog.error("Error load Today Data " + currency + " From Bank:");

            connection.disconnect();
            return currencyPrice;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static  List<BigDecimal> loadRangePeriodDataFromBank(Optional<String> currency, int countDays) {
        List<BigDecimal> currencyPrice = new ArrayList<>();
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String endDate = currentDate.format(formatter);
        String startDate = (currentDate.minusDays(countDays).format(formatter));
        try {
            String apiUrl = String.format("http://api.nbp.pl/api/exchangerates/rates/a/%s/%s/%s/?format=json",
                    currency.get(), startDate, endDate);

            HttpURLConnection connection = prepareConnection(apiUrl);
            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                currencyPrice = getRangePriceFromJson(readResponse(reader));
            } else
                apiNbpLog.error("Error load Period Data " + currency + " From Bank:");

            connection.disconnect();
            return currencyPrice;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static HttpURLConnection prepareConnection(String apiUrl) throws IOException{
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        return connection;
    }

    private static String readResponse(BufferedReader reader) throws IOException{
        String line;
        StringBuilder response = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();
        return response.toString();
    }

    private static BigDecimal getOnePriceFromJson(String response){
        JSONObject responseFromBank = new JSONObject(response);
        JSONArray rates = responseFromBank.getJSONArray("rates");
        JSONObject midPrice = rates.getJSONObject(0);
        return  midPrice.getBigDecimal("mid");
    }

    private static List<BigDecimal> getRangePriceFromJson(String response){
        List<BigDecimal> currencyRates = new ArrayList<>();
        JSONObject responseFromBank = new JSONObject(response);
        JSONArray rates = responseFromBank.getJSONArray("rates");
        JSONObject midPrice = rates.getJSONObject(0);

        for (int i = 0; i < rates.length(); i++) {
            JSONObject rateObject = rates.getJSONObject(i);
            BigDecimal exchangeRate = rateObject.getBigDecimal("mid");
            currencyRates.add(exchangeRate);
        }

        return currencyRates;
    }
}