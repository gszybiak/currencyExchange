package currencyExchange.helpers;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiNbpHelper {

    /**
     * Metoda do pobierania aktualnego kursu waluty
     */
    public static Double loadDataFromBank(String currency) {
        Double currencyPrice = null;
        try {
            String apiUrl = "http://api.nbp.pl/api/exchangerates/rates/a/" + currency + "/?format=json";

            HttpURLConnection connection = request(apiUrl);
            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                currencyPrice = getPriceFromJson(readerResponse(reader));
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
    private static Double getPriceFromJson(String response){
        JSONObject responseFromBank = new JSONObject(response);
        JSONArray rates = responseFromBank.getJSONArray("rates");
        JSONObject midPrice = rates.getJSONObject(0);
        return  midPrice.getDouble("mid");
    }
}
