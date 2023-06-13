package stocksageservice.alphavantageservice.pojo;

import java.util.Objects;

public class Date {

    String date;
    Stock stock;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "Date{" +
                "date='" + date + '\'' +
                ", stock=" + stock +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Date date1 = (Date) o;
        return Objects.equals(date, date1.date) && Objects.equals(stock, date1.stock);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, stock);
    }
}
