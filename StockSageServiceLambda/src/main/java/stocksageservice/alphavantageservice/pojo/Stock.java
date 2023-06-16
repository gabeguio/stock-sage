package stocksageservice.alphavantageservice.pojo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import stocksageservice.activity.requests.CreateQueryRequest;

import java.util.Objects;

public class Stock {
    private String open;
    private String high;
    private String low;
    private String close;
    private String volume;

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getClose() {
        return close;
    }

    public void setClose(String close) {
        this.close = close;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stock stock = (Stock) o;
        return Objects.equals(open, stock.open) && Objects.equals(high, stock.high) && Objects.equals(low, stock.low) && Objects.equals(close, stock.close) && Objects.equals(volume, stock.volume);
    }

    @Override
    public int hashCode() {
        return Objects.hash(open, high, low, close, volume);
    }
}