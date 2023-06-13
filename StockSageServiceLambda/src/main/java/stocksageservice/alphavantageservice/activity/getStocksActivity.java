package stocksageservice.alphavantageservice.activity;

import stocksageservice.alphavantageservice.activity.request.GetStocksRequest;
import stocksageservice.alphavantageservice.activity.result.GetStocksResult;
import stocksageservice.alphavantageservice.datahandler.AlphaVantageDao;

import java.util.List;

public class getStocksActivity {

    private AlphaVantageDao alphaVantageDao = new AlphaVantageDao();

    // return GetStocksResults
    public List<String> handlRequest(GetStocksRequest getStocksRequest) {
        String symbol = getStocksRequest.getSymbol();
        String function = getStocksRequest.getFunction();
        String fromDate = getStocksRequest.getFromDate();
        String toDate = getStocksRequest.getToDate();

        List<String> stocks = alphaVantageDao.getDatesInRange(symbol, function, fromDate, toDate);

        return stocks;
    }


}
