package stocksageservice.converters;

import stocksageservice.dynamodb.models.Query;
import stocksageservice.models.QueryModel;

/**
 * Converts between Data and API models.
 */
public class ModelConverter {

    public QueryModel toQueryModel(Query query) {
        return QueryModel.builder()
                .withUsername(query.getUsername())
                .withQueryId(query.getQueryId())
                .withDateRequested(query.getDateRequested())
                .withFromDate(query.getFromDate())
                .withToDate(query.getToDate())
                .withFrequency(query.getFrequency())
                .withFrequency(query.getFrequency())
                .withSymbol(query.getSymbol())
                .withSaved(query.getSaved())
                .build();
    }

}