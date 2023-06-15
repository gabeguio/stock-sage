package stocksageservice.alphavantageservice;

import java.util.List;

public class GetStocksActivity {

    private final AlphaVantageDao alphaVantageDao = new AlphaVantageDao();

    // return GetStocksResults
    public List<StockModel> handlRequest(GetStocksRequest getStocksRequest) {
        String symbol = getStocksRequest.getSymbol();
        String function = getStocksRequest.getFunction();
        String startDate = getStocksRequest.getStartDate();
        String endDate = getStocksRequest.getEndDate();

        return alphaVantageDao.getDatesInRange(symbol, function, startDate, endDate);
    }


}
