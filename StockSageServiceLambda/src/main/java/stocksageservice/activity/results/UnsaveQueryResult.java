package stocksageservice.activity.results;

import stocksageservice.models.QueryModel;

public class UnsaveQueryResult {
    private final QueryModel queryModel;

    private UnsaveQueryResult(QueryModel queryModel) {
        this.queryModel = queryModel;
    }

    public QueryModel getQueryModel() {
        return queryModel;
    }

    @Override
    public String toString() {
        return "UnsaveQueryResult{" +
                "queryModel=" + queryModel +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private QueryModel queryModel;

        public Builder withQueryModel(QueryModel queryModel) {
            this.queryModel = queryModel;
            return this;
        }

        public UnsaveQueryResult build() {
            return new UnsaveQueryResult(queryModel);
        }
    }
}