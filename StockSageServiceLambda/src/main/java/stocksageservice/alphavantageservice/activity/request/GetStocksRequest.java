package stocksageservice.alphavantageservice.activity.request;

import stocksageservice.activity.requests.GetQueryRequest;

public class GetStocksRequest {

    private final String symbol;
    private final String function;
    private final String fromDate;
    private final String toDate;


    public GetStocksRequest(String symbol, String function, String fromDate, String toDate) {
        this.symbol = symbol;
        this.function = function;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getFunction() {
        return function;
    }

    public String getFromDate() {
        return fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    @Override
    public String toString() {
        return "GetStocksRequest{" +
                "symbol='" + symbol + '\'' +
                ", function='" + function + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

    }








}
