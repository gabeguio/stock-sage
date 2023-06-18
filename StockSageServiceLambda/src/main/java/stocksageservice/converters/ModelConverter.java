package stocksageservice.converters;

import stocksageservice.dynamodb.models.Query;
import stocksageservice.models.QueryModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Converts between Data and API models.
 */
public class ModelConverter {

    public QueryModel toQueryModel(Query query) {
        return QueryModel.builder()
                .withUsername(query.getUsername())
                .withQueryId(query.getQueryId())
                .withDateRequested(query.getDateRequested())
                .withStartDate(query.getStartDate())
                .withEndDate(query.getEndDate())
                .withFrequency(query.getFrequency())
                .withFrequency(query.getFrequency())
                .withSymbol(query.getSymbol())
                .withSaved(query.getSaved())
                .build();
    }

    public List<QueryModel> toQueryModelList(List<Query> queries) {
        List<QueryModel> queryModelList = new ArrayList<>();
        for (Query query : queries) {
            queryModelList.add(toQueryModel(query));
        }
        return queryModelList;
    }

}