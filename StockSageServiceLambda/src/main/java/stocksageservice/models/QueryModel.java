package stocksageservice.models;

import java.util.Objects;

public class QueryModel {

    private final String username;
    private final String queryId;
    private final String dateRequested;
    private final String startDate;
    private final String endDate;
    private final String frequency;
    private final String symbol;
    private final String saved;


    public QueryModel(String username, String queryId, String dateRequested, String startDate, String endDate, String frequency, String symbol, String saved) {
        this.username = username;
        this.queryId = queryId;
        this.dateRequested = dateRequested;
        this.startDate = startDate;
        this.endDate = endDate;
        this.frequency = frequency;
        this.symbol = symbol;
        this.saved = saved;
    }

    public String getUsername() {
        return username;
    }

    public String getQueryId() {
        return queryId;
    }

    public String getDateRequested() {
        return dateRequested;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getFrequency() {
        return frequency;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getSaved() {
        return saved;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QueryModel that = (QueryModel) o;
        return Objects.equals(username, that.username) &&
                Objects.equals(queryId, that.queryId) &&
                Objects.equals(dateRequested, that.dateRequested) &&
                Objects.equals(startDate, that.startDate) &&
                Objects.equals(endDate, that.endDate) &&
                Objects.equals(frequency, that.frequency) &&
                Objects.equals(symbol, that.symbol) &&
                Objects.equals(saved, that.saved);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, queryId, dateRequested, startDate, endDate, frequency, symbol, saved);
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String username;
        private String queryId;
        private String dateRequested;
        private String startDate;
        private String endDate;
        private String frequency;
        private String symbol;
        private String saved;


        public Builder withUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder withQueryId(String queryId) {
            this.queryId = queryId;
            return this;
        }

        public Builder withDateRequested(String dateRequested) {
            this.dateRequested = dateRequested;
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

        public Builder withFrequency(String frequency) {
            this.frequency = frequency;
            return this;
        }

        public Builder withSymbol(String symbol) {
            this.symbol = symbol;
            return this;
        }

        public Builder withSaved(String saved) {
            this.saved = saved;
            return this;
        }

        public QueryModel build() {
            return new QueryModel(username, queryId, dateRequested, startDate, endDate, frequency, symbol, saved);
        }
    }
}

