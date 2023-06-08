package stocksageservice.activity.results;

import stocksageservice.models.QueryModel;

public class CreateQueryResult {
    private final QueryModel queryModel;

    private CreateQueryResult(QueryModel queryModel) {
        this.queryModel = queryModel;
    }

    public QueryModel getQueryModel() {
        return queryModel;
    }

    @Override
    public String toString() {
        return "CreateQueryResult{" +
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

        public CreateQueryResult build() {
            return new CreateQueryResult(queryModel);
        }
    }
}
