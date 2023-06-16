package stocksageservice.activity.results;

import stocksageservice.alphavantageservice.StockModel;

import java.util.List;

public class CreateQueryResult {
    private final List<StockModel> stockModels;

    private CreateQueryResult(List<StockModel> stockModels) {
        this.stockModels = stockModels;
    }

    public List<StockModel> getStockModels() {
        return stockModels;
    }

    @Override
    public String toString() {
        return "CreateQueryResult{" +
                "stockModels=" + stockModels +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private List<StockModel> stockModels;

        public Builder withStockModels(List<StockModel> stockModels) {
            this.stockModels = stockModels;
            return this;
        }

        public CreateQueryResult build() {
            return new CreateQueryResult(stockModels);
        }
    }
}
