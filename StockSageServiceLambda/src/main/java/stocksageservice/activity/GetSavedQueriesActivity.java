package stocksageservice.activity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import stocksageservice.activity.requests.GetSavedQueriesRequest;
import stocksageservice.activity.results.GetRecentQueriesResult;
import stocksageservice.activity.results.GetSavedQueriesResult;
import stocksageservice.converters.ModelConverter;
import stocksageservice.dynamodb.QueryDao;
import stocksageservice.dynamodb.models.Query;
import stocksageservice.models.QueryModel;

import javax.inject.Inject;
import java.util.List;

public class GetSavedQueriesActivity {

    private final Logger log = LogManager.getLogger();
    private final QueryDao queryDao;

    @Inject
    public GetSavedQueriesActivity(QueryDao queryDao) {
        this.queryDao = queryDao;
    }

    public GetSavedQueriesResult handleRequest(GetSavedQueriesRequest getSavedQueriesRequest) {
        log.info("Received GetSaveQueriesRequest {}", getSavedQueriesRequest);

        List<Query> recentQueries = queryDao.getSavedQueriesByUsername(getSavedQueriesRequest.getUsername());
        log.info("Username sent to queryDao");
        List<QueryModel> queryModelList = new ModelConverter().toQueryModelList(recentQueries);
        log.info("response converted to a queryModel");

        return GetSavedQueriesResult.builder()
                .withQueryModelList(queryModelList)
                .build();

    }
}
