package stocksageservice.alphavantageservice.activity;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class AlphaVantageServiceClient {

    String API_KEY = "YOUR_API_KEY";
    String API_BASE_URL = "https://www.alphavantage.co/query";
    String symbol = "TSLA";
    String apiKeyParam = "apikey=" + API_KEY;
    String symbolParam = "symbol=" + symbol;
    String functionParam = "function=TIME_SERIES_MONTHLY"; // Example API function
    String apiUrl = API_BASE_URL + "?" + apiKeyParam + "&" + symbolParam + "&" + functionParam;

    ObjectMapper objectMapper = new ObjectMapper();

    Map<String, Object> timeSeries = new HashMap<>();

    public Map<String, Object> getStocksFromClient() {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(apiUrl);

        try {
            HttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                try (InputStream inputStream = entity.getContent()) {
                    Map<String, Object> jsonResponse = objectMapper.readValue(inputStream, Map.class);
                    timeSeries = (Map<String, Object>) jsonResponse.get("Monthly Time Series");
//                    Map<String, Object> specificDate = (Map<String, Object>) timeSeries.get("2023-06-09");
//                    System.out.println(specificDate);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return timeSeries;
    }
}
