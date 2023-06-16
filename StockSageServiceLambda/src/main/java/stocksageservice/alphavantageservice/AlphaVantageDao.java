package stocksageservice.alphavantageservice;

import com.fasterxml.jackson.databind.JsonNode;

import javax.inject.Inject;
import java.util.*;

public class AlphaVantageDao {

    private final AlphaVantageServiceClient client = new AlphaVantageServiceClient();

    public List<StockModel> getDatesInRange(String startDate, String endDate, String function, String symbol) {
        List<StockModel> datesInRange = new ArrayList<>();
        Map<String, JsonNode>  timeSeries = client.getTimeSeriesFromPayload(function, symbol);
        for (String date : timeSeries.keySet()) {
            if (date.compareTo(startDate) >= 0 && date.compareTo(endDate) <= 0) {
                JsonNode stockData = timeSeries.get(date);
                StockModel stockModel = StockModel.builder()
                        .withDate(date)
                        .withOpen(stockData.get("1. open").asText())
                        .withHigh(stockData.get("2. high").asText())
                        .withLow(stockData.get("3. low").asText())
                        .withClose(stockData.get("4. close").asText())
                        .withVolume(stockData.get("5. volume").asText())
                        .build();
                datesInRange.add(stockModel);
            }
        }
        return datesInRange;

    }
}
