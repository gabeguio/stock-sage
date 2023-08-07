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

// QUERY: Call AlphaVantage API by params: date range, aggregation, and symbol
// RETURNS: Historical pricing data from AlphaVantage API to StockModel DAO

// HELPER METHODS to generate API url with valid key and params.

public class AlphaVantageServiceClient {
    ObjectMapper objectMapper = new ObjectMapper();

    public Map<String, JsonNode> getTimeSeriesFromPayload(String function, String symbol) {
        // Open client call API
        CloseableHttpClient httpClient = HttpClients.createDefault();

        // Call helper method to return valid aggregation period
        String validFunctionForApiParam = getValidTimeSeriesForApiParam(function);

        // Call helper method to build API url
        HttpGet httpGet = new HttpGet(generateApiUrl(validFunctionForApiParam, symbol));

        // Call helper method to retrieve proper key value mapping for JSON deserialization
        String validTimeSeries = getValidTimeSeriesForJson(function);

        // Executing GET method for AlphaVantage API
        try {
            HttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                try (InputStream inputStream = entity.getContent()) {
                    // Begin JSON deserialization to build requested range for the data set
                    JsonNode jsonRootNode = objectMapper.readTree(inputStream);
                    JsonNode timeSeries = jsonRootNode.get(validTimeSeries);

                    Map<String, JsonNode> dateDataMap = new HashMap<>();
                    Iterator<Map.Entry<String, JsonNode>> iterator = timeSeries.fields();
                    // Iterator to store each data as a JSON node
                    while (iterator.hasNext()) {
                        Map.Entry<String, JsonNode> entry = iterator.next();
                        String date = entry.getKey();
                        JsonNode data = entry.getValue();
                        dateDataMap.put(date, data);
                    }

                    // Return Datamap to API DAO class
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

    private String getValidTimeSeriesForApiParam(String function) {
        switch (function) {
            case "Weekly":
                return "TIME_SERIES_WEEKLY";
            case "Monthly":
                return "TIME_SERIES_MONTHLY";
        }
        return "";
    }
    private String getValidTimeSeriesForJson(String function) {
        switch (function) {
            case "Weekly":
                return "Weekly Time Series";
            case "Monthly":
                return "Monthly Time Series";
        }
        return "";
    }


}
