package stocksageservice.models;

import java.util.Objects;

public class QueryModel {

    private final String username;

    private final String queryId;

    public QueryModel(String username, String queryId) {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QueryModel that = (QueryModel) o;
        return Objects.equals(username, that.username) && Objects.equals(queryId, that.queryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, queryId);
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

        public QueryModel build() {
            return new QueryModel(username, queryId);
        }
    }
}

