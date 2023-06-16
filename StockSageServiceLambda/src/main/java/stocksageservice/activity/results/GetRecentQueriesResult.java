package stocksageservice.activity.results;

import stocksageservice.activity.requests.GetRecentQueriesRequest;
import stocksageservice.models.QueryModel;

import java.util.List;

public class GetRecentQueriesResult {

    private final List<QueryModel> queryModelList;

    public GetRecentQueriesResult(List<QueryModel> queryModelList) {
        this.queryModelList = queryModelList;
    }

    public List<QueryModel> getQueryModelList() {
        return queryModelList;
    }

    @Override
    public String toString() {
        return "GetRecentQueriesResult{" +
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

        public GetRecentQueriesResult build() {
            return new GetRecentQueriesResult(queryModelList);
        }
    }

}
