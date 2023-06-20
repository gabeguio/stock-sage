package stocksageservice.activity.requests;

public class GetSavedQueriesRequest {

    private final String username;

    public GetSavedQueriesRequest(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "GetSavedQueriesRequest{" +
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

        public GetSavedQueriesRequest build() {
            return new GetSavedQueriesRequest(username);
        }
    }


}


