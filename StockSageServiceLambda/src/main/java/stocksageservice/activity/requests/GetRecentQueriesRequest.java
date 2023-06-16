package stocksageservice.activity.requests;

public class GetRecentQueriesRequest {

    private final String username;

    public GetRecentQueriesRequest(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "GetRecentQueriesRequest{" +
                "username='" + username + '\'' +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String username;

        public Builder withUsername(String username) {
            this.username = username;
            return this;
        }

        public GetRecentQueriesRequest builder() {
            return new GetRecentQueriesRequest(username);
        }
    }
}
