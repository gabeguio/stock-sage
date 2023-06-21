package stocksageservice.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = UnsaveQueryRequest.Builder.class)
public class UnsaveQueryRequest {
    private final String username;
    private final String queryId;

    public UnsaveQueryRequest(String username, String queryId) {
        this.username = username;
        this.queryId = queryId;
    }

    public String getUsername() {
        return username;
    }

    public String getQueryId() {
        return queryId;
    }

    @Override
    public String toString() {
        return "UnsaveQueryRequest{" +
                "username='" + username + '\'' +
                ", queryId='" + queryId + '\'' +
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

        public Builder withUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder withQueryId(String queryId) {
            this.queryId = queryId;
            return this;
        }

        public UnsaveQueryRequest build() {
            return new UnsaveQueryRequest(username, queryId);
        }
    }
}

