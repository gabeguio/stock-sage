package stocksageservice.activity.results;

import stocksageservice.models.QueryModel;

public class GetQueryResult {
    private final QueryModel queryModel;

    private GetQueryResult(QueryModel queryModel) {
        this.queryModel = queryModel;
    }

    public QueryModel getQuery() {
        return queryModel;
    }

    @Override
    public String toString() {
        return "GetQueryResult{" +
                "queryModel=" + queryModel +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private QueryModel queryModel;

        public Builder withQuery(QueryModel queryModel) {
            this.queryModel = queryModel;
            return this;
        }

        public GetQueryResult build() {
            return new GetQueryResult(queryModel);
        }
    }
}