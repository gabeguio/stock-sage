package stocksageservice.alphavantageservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import stocksageservice.alphavantageservice.pojo.Stock;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class AlphaVantageServiceClient {
    ObjectMapper objectMapper = new ObjectMapper();
    private final String API_KEY = "YOUR_API_KEY";
    private final String API_BASE_URL = "https://www.alphavantage.co/query";

    public Map<String, Object> getTimeSeriesFromPayload(String function, String symbol) {
        String apiUrl = generateApiUrl(function, symbol);
        Map<String, Object> jsonPayload = getEntirePayload(apiUrl);
        String validTimeSeries = getValidTimeSeries(function);
        Map<String, Object> timeSeries = (Map<String, Object>) jsonPayload.get(validTimeSeries);
//        Map<String, Stock> timeSeries = (Map<String, Stock>) jsonPayload.get(validTimeSeries);
        return timeSeries;
    }

    public Map<String, Object> getEntirePayload(String apiUrl) {
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

    private String generateApiUrl(String function, String symbol) {
        String apiKeyParam = "apikey=" + API_KEY;
        String functionParam = "function=" + function;
        String symbolParam = "symbol=" + symbol;
        return API_BASE_URL + "?" + apiKeyParam + "&" + symbolParam + "&" + functionParam;
    }

    private String getValidTimeSeries(String function) {
        switch (function) {
            case "TIME_SERIES_WEEKLY":
                return "Weekly Time Series";
            case "TIME_SERIES_MONTHLY":
                return "Monthly Time Series";
        }
        return "";
    }
}
