package stocksageservice.activity;

import stocksageservice.activity.requests.GetQueryRequest;
import stocksageservice.activity.results.GetQueryResult;
import stocksageservice.converters.ModelConverter;
import stocksageservice.dynamodb.QueryDao;
import stocksageservice.dynamodb.models.Query;
import stocksageservice.models.QueryModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

public class GetQueryActivity {
    private final Logger log = LogManager.getLogger();
    private final QueryDao queryDao;

    @Inject
    public GetQueryActivity(QueryDao queryDao) {
        this.queryDao = queryDao;
    }

    public GetQueryResult handleRequest(final GetQueryRequest getQueryRequest) {
        log.info("Received GetQueryRequest {}", getQueryRequest);
        String username = getQueryRequest.getUsername();
        String queryId = getQueryRequest.getQueryId();
        Query query = queryDao.getQuery(username, queryId);
        QueryModel queryModel = new ModelConverter().toQueryModel(query);

        return GetQueryResult.builder()
                .withQuery(queryModel)
                .build();
    }
}

