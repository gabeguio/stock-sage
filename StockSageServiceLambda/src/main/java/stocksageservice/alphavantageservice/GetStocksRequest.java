package stocksageservice.alphavantageservice;

public class GetStocksRequest {

    private final String startDate;
    private final String endDate;
    private final String function;
    private final String symbol;


    public GetStocksRequest(String startDate, String endDate, String function, String symbol) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.function = function;
        this.symbol = symbol;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getFunction() {
        return function;
    }

    public String getSymbol() {
        return symbol;
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String startDate;
        private String endDate;
        private String function;
        private String symbol;

        public Builder withStartDate(String startDate) {
            this.startDate = startDate;
            return this;
        }

        public Builder withEndDate(String endDate) {
            this.endDate = endDate;
            return this;
        }

        public Builder withFunction(String function) {
            this.function = function;
            return this;
        }

        public Builder withSymbol(String symbol) {
            this.symbol = symbol;
            return this;
        }

        public GetStocksRequest build() {
            return new GetStocksRequest(startDate, endDate, function, symbol);
        }
    }








}
