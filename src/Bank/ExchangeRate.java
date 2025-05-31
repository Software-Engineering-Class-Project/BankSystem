package Bank;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import org.json.JSONObject;

// A class that tracks and converts currency rates up to date
public class ExchangeRate {

    public static double getUSDExchangeRate() {
        return fetchRate("USD");
    }

    public static double getEURExchangeRate() {
        return fetchRate("EUR");
    }

    private static double fetchRate(String currency) {
        try {
            String urlStr = "https://api.frankfurter.app/latest?from=TRY&to=USD,EUR";
            URI uri = URI.create(urlStr);
            URL url = uri.toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            reader.close();

            JSONObject json = new JSONObject(response.toString());
            JSONObject rates = json.getJSONObject("rates");

            return rates.getDouble(currency);

        } catch (Exception e) {
            System.out.println("Kur bilgisi alınamadı: " + e.getMessage());
            return -1;
        }
    }
}
