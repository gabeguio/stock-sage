package stocksageservice.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = CreateQueryRequest.Builder.class)
public class CreateQueryRequest {
    private final String username;
    private final String queryId;
    private final String dateRequested;
    private final String fromDate;
    private final String toDate;
    private final String frequency;
    private final String symbol;
    private final String saved; // This will always start as false

    public CreateQueryRequest(String username, String queryId, String dateRequested, String fromDate, String toDate, String frequency, String symbol, String saved) {
        this.username = username;
        this.queryId = queryId;
        this.dateRequested = dateRequested;
        this.fromDate = fromDate;
        this.toDate = toDate;
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

    public String getFromDate() {
        return fromDate;
    }

    public String getToDate() {
        return toDate;
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
    public String toString() {
        return "CreateQueryRequest{" +
                "username='" + username + '\'' +
                ", queryId='" + queryId + '\'' +
                ", dateRequested='" + dateRequested + '\'' +
                ", fromDate='" + fromDate + '\'' +
                ", toDate='" + toDate + '\'' +
                ", frequency='" + frequency + '\'' +
                ", symbol='" + symbol + '\'' +
                ", saved='" + saved + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {
        private String username;
        private String queryId;
        private String dateRequested;
        private String fromDate;
        private String toDate;
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

        public Builder withFromDate(String fromDate) {
            this.fromDate = fromDate;
            return this;
        }

        public Builder withToDate(String toDate) {
            this.toDate = toDate;
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

        public CreateQueryRequest build() {
            return new CreateQueryRequest(username, queryId, dateRequested, fromDate, toDate, frequency, symbol, saved);
        }
    }
}