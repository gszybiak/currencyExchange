package currencyExchange.helpers;

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

public class ApiNbpHelper {

    /**
     * Method for downloading the current exchange rate
     */
    public static BigDecimal loadTodayDataFromBank(String currency) {
        BigDecimal currencyPrice = null;
        try {
            String apiUrl = "http://api.nbp.pl/api/exchangerates/rates/a/" + currency + "/?format=json";

            HttpURLConnection connection = request(apiUrl);
            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                currencyPrice = getOnePriceFromJson(readerResponse(reader));
            } else
                System.out.println("Error downloading data. Response code: " + responseCode);

            connection.disconnect();
            return currencyPrice;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Method for downloading exchange rates for a given period
     */
    public static  List<BigDecimal> loadRangePeriodDataFromBank(String currency, int countDays) {
        List<BigDecimal> currencyPrice = new ArrayList<>();
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String endDate = currentDate.format(formatter);
        String startDate = (currentDate.minusDays(countDays).format(formatter));
        try {
            String apiUrl = "http://api.nbp.pl/api/exchangerates/rates/a/"
                    + currency + "/" + startDate + "/" + endDate + "/?format=json";

            HttpURLConnection connection = request(apiUrl);
            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                currencyPrice = getRangePriceFromJson(readerResponse(reader));
            } else
                System.out.println("Error downloading data. Response code: " + responseCode);

            connection.disconnect();
            return currencyPrice;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * method that sends the request to the API
     */
    private static HttpURLConnection request(String apiUrl) throws IOException{
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        return connection;
    }

    /**
     * method that reads the request from the API
     */
    private static String readerResponse(BufferedReader reader) throws IOException{
        String line;
        StringBuilder response = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();
        return response.toString();
    }

    /**
     * Method that retrieves the price from a currency from JSON
     */
    private static BigDecimal getOnePriceFromJson(String response){
        JSONObject responseFromBank = new JSONObject(response);
        JSONArray rates = responseFromBank.getJSONArray("rates");
        JSONObject midPrice = rates.getJSONObject(0);
        return  midPrice.getBigDecimal("mid");
    }

    /**
     * Method that retrieves currency prices from a given period
     */
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