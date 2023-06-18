package stocksageservice.activity.results;

import stocksageservice.models.QueryModel;

import java.util.List;

public class GetSavedQueriesResult {

    private final List<QueryModel> queryModelList;

    public GetSavedQueriesResult(List<QueryModel> queryModelList) {
        this.queryModelList = queryModelList;
    }

    @Override
    public String toString() {
        return "GetSavedQueriesResult{" +
                "queryModelList=" + queryModelList +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private List<QueryModel> queryModelList;

        public Builder withQueryModelList(List<QueryModel> queryModelList) {
            this.queryModelList = queryModelList;
            return this;
        }

        public GetSavedQueriesResult build() {
            return new GetSavedQueriesResult(queryModelList);
        }
    }
}
