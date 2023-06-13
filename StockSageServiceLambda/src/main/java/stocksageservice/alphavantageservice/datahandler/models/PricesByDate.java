package stocksageservice.alphavantageservice.datahandler.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import stocksageservice.activity.requests.CreateQueryRequest;

@JsonDeserialize(builder = CreateQueryRequest.Builder.class)
public class PricesByDate {

    String date;
    String open;
    String low;
    String close;
    String volume;

    public PricesByDate(String date, String open, String low, String close, String volume) {
        this.date = date;
        this.open = open;
        this.low = low;
        this.close = close;
        this.volume = volume;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
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

    // Implement Builder
}
