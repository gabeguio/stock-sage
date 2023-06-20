package stocksageservice.activity.results;

import stocksageservice.models.QueryModel;

public class SaveQueryResult {
    private final QueryModel queryModel;

    private SaveQueryResult(QueryModel queryModel) {
        this.queryModel = queryModel;
    }

    public QueryModel getQueryModel() {
        return queryModel;
    }

    @Override
    public String toString() {
        return "SaveQueryResult{" +
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

        public SaveQueryResult build() {
            return new SaveQueryResult(queryModel);
        }
    }
}