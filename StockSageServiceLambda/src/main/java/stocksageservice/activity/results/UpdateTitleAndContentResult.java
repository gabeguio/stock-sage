package stocksageservice.activity.results;

import stocksageservice.models.QueryModel;

public class UpdateTitleAndContentResult {
    private final QueryModel queryModel;

    private UpdateTitleAndContentResult(QueryModel queryModel) {
        this.queryModel = queryModel;
    }

    public QueryModel getQueryModel() {
        return queryModel;
    }

    @Override
    public String toString() {
        return "UpdateTitleAndContentResult{" +
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

        public UpdateTitleAndContentResult build() {
            return new UpdateTitleAndContentResult(queryModel);
        }
    }
}