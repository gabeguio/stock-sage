package stocksageservice.alphavantageservice;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class AlphaVantageServiceClient {
    ObjectMapper objectMapper = new ObjectMapper();

    public Map<String, JsonNode> getTimeSeriesFromPayload(String function, String symbol) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(generateApiUrl(function, symbol));
        String validTimeSeries = getValidTimeSeries(function);

        try {
            HttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                try (InputStream inputStream = entity.getContent()) {
                    JsonNode jsonRootNode = objectMapper.readTree(inputStream);
                    JsonNode timeSeries = jsonRootNode.get(validTimeSeries);
                    Map<String, JsonNode> dateDataMap = new HashMap<>();
                    System.out.println("created dateDataMap");

                    Iterator<Map.Entry<String, JsonNode>> iterator = timeSeries.fields();
                    System.out.println("created iterator");
                    while (iterator.hasNext()) {
                        Map.Entry<String, JsonNode> entry = iterator.next();
                        String date = entry.getKey();
                        JsonNode data = entry.getValue();
                        System.out.println(date + " " + data);
                        dateDataMap.put(date, data);
                    }
                    return dateDataMap;
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
        String API_KEY = "YOUR_API_KEY";
        String apiKeyParam = "apikey=" + API_KEY;
        String functionParam = "function=" + function;
        String symbolParam = "symbol=" + symbol;
        String API_BASE_URL = "https://www.alphavantage.co/query";
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
