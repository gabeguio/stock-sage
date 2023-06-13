package stocksageservice.alphavantageservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import stocksageservice.alphavantageservice.activity.request.GetStocksRequest;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class AlphaVantageServiceClient {
    ObjectMapper objectMapper = new ObjectMapper();
    private final String API_KEY = "YOUR_API_KEY";
    private final String API_BASE_URL = "https://www.alphavantage.co/query";

    public Map<String, Object> getTimeSeriesFromPayload(String symbol, String function) {
        String apiUrl = generateApiUrl(symbol, function);
        String captilizeFunction = function
                .toLowerCase()
                .substring(0, 1)
                .toUpperCase() + function.substring(1);

        Map<String, Object> jsonPayload = getPayloadFromApi(apiUrl);

        Map<String, Object> timeSeries = (Map<String, Object>) jsonPayload.get(captilizeFunction + " Time Series");

        return timeSeries;
    }

    private Map<String, Object> getPayloadFromApi(String apiUrl) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(apiUrl);
        Map<String, Object> jsonPayload;

        try {
            HttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                try (InputStream inputStream = entity.getContent()) {
                    jsonPayload = objectMapper.readValue(inputStream, Map.class);
                    return jsonPayload;
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
        return null;
    }

    private String generateApiUrl(String symbol, String function) {
        String apiKeyParam = "apikey=" + API_KEY;
        String functionParam = "function=" + function;
        String symbolParam = "symbol=" + symbol;
        return API_BASE_URL + "?" + apiKeyParam + "&" + symbolParam + "&" + functionParam;
    }
}
