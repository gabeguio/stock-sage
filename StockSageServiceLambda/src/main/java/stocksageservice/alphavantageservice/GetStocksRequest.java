package stocksageservice.alphavantageservice;

public class GetStocksRequest {

    private final String symbol;
    private final String function;
    private final String startDate;
    private final String endDate;


    public GetStocksRequest(String symbol, String function, String startDate, String endDate) {
        this.symbol = symbol;
        this.function = function;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getFunction() {
        return function;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    @Override
    public String toString() {
        return "GetStocksRequest{" +
                "symbol='" + symbol + '\'' +
                ", function='" + function + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String symbol;
        private String function;
        private String startDate;
        private String endDate;

        public Builder withSymbol(String symbol) {
            this.symbol = symbol;
            return this;
        }

        public Builder withFunction(String function) {
            this.function = function;
            return this;
        }

        public Builder withStartDate(String startDate) {
            this.startDate = startDate;
            return this;
        }

        public Builder withEndDate(String endDate) {
            this.endDate = endDate;
            return this;
        }

        public GetStocksRequest build() {
            return new GetStocksRequest(symbol, function, startDate, endDate);
        }
    }








}
