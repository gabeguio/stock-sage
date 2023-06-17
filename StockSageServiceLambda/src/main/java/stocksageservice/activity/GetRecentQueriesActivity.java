package stocksageservice.activity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import stocksageservice.activity.requests.GetRecentQueriesRequest;
import stocksageservice.activity.results.GetRecentQueriesResult;
import stocksageservice.converters.ModelConverter;
import stocksageservice.dynamodb.QueryDao;
import stocksageservice.dynamodb.models.Query;
import stocksageservice.models.QueryModel;

import javax.inject.Inject;
import java.util.List;

public class GetRecentQueriesActivity {

    private final Logger log = LogManager.getLogger();
    private final QueryDao queryDao;

    @Inject
    public GetRecentQueriesActivity(QueryDao queryDao) {
        this.queryDao = queryDao;
    }

    public GetRecentQueriesResult handleRequest(GetRecentQueriesRequest getRecentQueriesRequest) {
        log.info("Received GetRecentQueriesResult {}", getRecentQueriesRequest);

        List<Query> recentQueries = queryDao.getRecentQueriesForUsername(getRecentQueriesRequest.getUsername());
        List<QueryModel> queryModelList = new ModelConverter().toQueryModelList(recentQueries);

        return GetRecentQueriesResult.builder()
                .withQueryModelList(queryModelList)
                .build();

    }
}
