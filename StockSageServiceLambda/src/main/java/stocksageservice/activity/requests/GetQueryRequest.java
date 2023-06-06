package stocksageservice.activity.requests;

public class GetQueryRequest {
    private final String username;
    private final String queryId;

    public GetQueryRequest(String username, String queryId) {
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
        return "GetQueryRequest{" +
                "username='" + username + '\'' +
                ", queryId='" + queryId + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

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

        public GetQueryRequest build() {
            return new GetQueryRequest(username, queryId);
        }
    }
}