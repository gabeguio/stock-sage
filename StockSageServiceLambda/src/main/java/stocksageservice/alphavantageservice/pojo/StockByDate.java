package stocksageservice.alphavantageservice.pojo;

import java.util.Map;
import java.util.Objects;

public class StockByDate {

    Map<String, Stock> stockByDate;

    public Map<String, Stock> getStockByDate() {
        return stockByDate;
    }

    public void setStockByDate(Map<String, Stock> stockByDate) {
        this.stockByDate = stockByDate;
    }

    @Override
    public String toString() {
        return "StockByDate{" +
                "stockByDate=" + stockByDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StockByDate that = (StockByDate) o;
        return Objects.equals(stockByDate, that.stockByDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stockByDate);
    }
}
