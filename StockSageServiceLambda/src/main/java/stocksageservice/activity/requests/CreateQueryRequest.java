package stocksageservice.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = CreateQueryRequest.Builder.class)
public class CreateQueryRequest {
    private final String username;
    private final String queryId;
    private final String dateRequested;
    private final String startDate;
    private final String endDate;
    private final String frequency;
    private final String symbol;
    private final String saved;
    private final String title;
    private final String content;

    public CreateQueryRequest(String username, String queryId, String dateRequested, String startDate, String endDate, String frequency, String symbol, String saved, String title, String content) {
        this.username = username;
        this.queryId = queryId;
        this.dateRequested = dateRequested;
        this.startDate = startDate;
        this.endDate = endDate;
        this.frequency = frequency;
        this.symbol = symbol;
        this.saved = saved;
        this.title = title;
        this.content = content;
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

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "CreateQueryRequest{" +
                "username='" + username + '\'' +
                ", queryId='" + queryId + '\'' +
                ", dateRequested='" + dateRequested + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", frequency='" + frequency + '\'' +
                ", symbol='" + symbol + '\'' +
                ", saved='" + saved + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
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
        private String startDate;
        private String endDate;
        private String frequency;
        private String symbol;
        private String saved;
        private String title;
        private String content;

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

        public Builder withTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder withContent(String content) {
            this.content = content;
            return this;
        }

        public CreateQueryRequest build() {
            return new CreateQueryRequest(username, queryId, dateRequested, startDate, endDate, frequency, symbol, saved, title, content);
        }
    }
}