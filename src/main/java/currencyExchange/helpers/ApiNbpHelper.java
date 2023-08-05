package currencyExchange.helpers;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ApiNbpHelper {

    /**
     * Metoda do pobierania aktualnego kursu waluty
     */
    public static Double loadTodayDataFromBank(String currency) {
        Double currencyPrice = null;
        try {
            String apiUrl = "http://api.nbp.pl/api/exchangerates/rates/a/" + currency + "/?format=json";

            HttpURLConnection connection = request(apiUrl);
            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                currencyPrice = getOnePriceFromJson(readerResponse(reader));
            } else
                System.out.println("Błąd podczas pobierania danych. Kod odpowiedzi: " + responseCode);

            connection.disconnect();
            return currencyPrice;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Metoda do pobierania kursów walut z podanego okresu
     */
    public static  List<Double> loadRangePeriodDataFromBank(String currency, int countDays) {
        List<Double> currencyPrice = new ArrayList<>();
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
                System.out.println("Błąd podczas pobierania danych. Kod odpowiedzi: " + responseCode);

            connection.disconnect();
            return currencyPrice;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Metoda wysyłająca żądanie do API
     */
    private static HttpURLConnection request(String apiUrl) throws IOException{
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        return connection;
    }

    /**
     * Metoda czytająca odebrane dane od API
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
     * Metoda pobierająca cenę z waluty z JSON
     */
    private static Double getOnePriceFromJson(String response){
        JSONObject responseFromBank = new JSONObject(response);
        JSONArray rates = responseFromBank.getJSONArray("rates");
        JSONObject midPrice = rates.getJSONObject(0);
        return  midPrice.getDouble("mid");
    }

    /**
     * Metoda pobierająca ceny walut z podanego okresu
     */
    private static List<Double> getRangePriceFromJson(String response){
        List<Double> currencyRates = new ArrayList<>();
        JSONObject responseFromBank = new JSONObject(response);
        JSONArray rates = responseFromBank.getJSONArray("rates");
        JSONObject midPrice = rates.getJSONObject(0);

        for (int i = 0; i < rates.length(); i++) {
            JSONObject rateObject = rates.getJSONObject(i);
            double exchangeRate = rateObject.getDouble("mid");
            currencyRates.add(exchangeRate);
        }

        return currencyRates;
    }
}
