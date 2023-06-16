package stocksageservice.alphavantageservice;

import java.util.List;

public class GetStocksActivity {

    private final AlphaVantageDao alphaVantageDao = new AlphaVantageDao();

    public List<StockModel> handlRequest(GetStocksRequest getStocksRequest) {
        String startDate = getStocksRequest.getStartDate();
        String endDate = getStocksRequest.getEndDate();
        String function = getStocksRequest.getFunction();
        String symbol = getStocksRequest.getSymbol();

        return alphaVantageDao.getDatesInRange(startDate, endDate, function, symbol);
    }


}
