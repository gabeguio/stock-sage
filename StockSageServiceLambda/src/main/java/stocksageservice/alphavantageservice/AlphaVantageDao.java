package stocksageservice.alphavantageservice;

import stocksageservice.alphavantageservice.AlphaVantageServiceClient;
import stocksageservice.alphavantageservice.pojo.Stock;

import java.util.*;

public class AlphaVantageDao {

    AlphaVantageServiceClient client = new AlphaVantageServiceClient();

    public List<StockModel> getDatesInRange(String startDate, String endDate, String function, String symbol) {
        List<StockModel> datesInRange = new ArrayList<>();
        Map<String, Stock>  timeSeries = client.getTimeSeriesFromPayload(symbol, function);
        for (String date : timeSeries.keySet()) {
            if (date.compareTo(startDate) >= 0 && date.compareTo(endDate) <= 0) {
                Stock stockData = timeSeries.get(date);
                StockModel stockModel = StockModel.builder()
                        .withData(date)
                        .withOpen(stockData.getOpen())
                        .withHigh(stockData.getHigh())
                        .withLow(stockData.getLow())
                        .withClose(stockData.getClose())
                        .withVolume(stockData.getVolume())
                        .build();
                System.out.println("The stock date" + stockData);
                System.out.println("The stock model" + stockModel);
            }
        }
        return datesInRange;

    }
}
