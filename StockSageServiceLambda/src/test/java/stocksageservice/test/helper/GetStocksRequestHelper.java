package stocksageservice.test.helper;

import stocksageservice.alphavantageservice.GetStocksRequest;

public class GetStocksRequestHelper {

    private GetStocksRequestHelper() {
    }

    public static GetStocksRequest generateWeeklyStocksRequest() {
        GetStocksRequest stockRequest = new GetStocksRequest.Builder()
                .withStartDate("2023-04-28")
                .withEndDate("2023-06-02")
                .withFunction("Weekly")
                .withSymbol("IBM")
                .build();

        return stockRequest;
    }

    public static GetStocksRequest generateMonthlyStocksRequest() {
        GetStocksRequest stockRequest = new GetStocksRequest.Builder()
                .withStartDate("2023-04-28")
                .withEndDate("2023-06-02")
                .withFunction("Monthly")
                .withSymbol("IBM")
                .build();

        return stockRequest;
    }
}
