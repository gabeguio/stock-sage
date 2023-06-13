package stocksageservice.alphavantageservice;

import java.util.Map;

public class GetStocksActivity {

    private AlphaVantageDao alphaVantageDao = new AlphaVantageDao();

    // return GetStocksResults
    public Map<String, Object> handlRequest(GetStocksRequest getStocksRequest) {
        String symbol = getStocksRequest.getSymbol();
        String function = getStocksRequest.getFunction();
        String startDate = getStocksRequest.getStartDate();
        String endDate = getStocksRequest.getEndDate();

        Map<String, Object> stocksInRange = alphaVantageDao.getDatesInRange(symbol, function, startDate, endDate);

        return stocksInRange;
    }


}
