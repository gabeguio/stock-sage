package stocksageservice.alphavantageservice;

import stocksageservice.alphavantageservice.AlphaVantageServiceClient;

import java.util.HashMap;
import java.util.Map;

public class AlphaVantageDao {

    AlphaVantageServiceClient client = new AlphaVantageServiceClient();

    public Map<String, Object> getDatesInRange(String startDate, String endDate, String function, String symbol) {
        Map<String, Object> datesInRange = new HashMap<>();
        Map<String, Object>  timeSeries = client.getTimeSeriesFromPayload(symbol, function);
        for (String date : timeSeries.keySet()) {
            if (date.compareTo(startDate) >= 0 && date.compareTo(endDate) <= 0) {
                datesInRange.put(date, timeSeries.get(date));
            }
        }
        return datesInRange;

    }
}
