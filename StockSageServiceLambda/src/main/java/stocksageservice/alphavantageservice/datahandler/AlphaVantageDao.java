package stocksageservice.alphavantageservice.datahandler;

import stocksageservice.alphavantageservice.service.AlphaVantageServiceClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AlphaVantageDao {

    AlphaVantageServiceClient client = new AlphaVantageServiceClient();

    public List<String> getDatesInRange(String symbol, String function, String fromDate, String toDate) {
        List<String> datesInRange = new ArrayList<>();
        Map<String, Object>  timeSeries = client.getTimeSeriesFromPayload(symbol, function);

        for (String date : timeSeries.keySet()) {
            System.out.println(date);
            if (date.compareTo(fromDate) >= 0 && date.compareTo(toDate) <= 0) {
                datesInRange.add(date);
            }
        }
        return datesInRange;

    }
}
