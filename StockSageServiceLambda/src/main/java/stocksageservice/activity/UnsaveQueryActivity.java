package stocksageservice.activity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import stocksageservice.activity.requests.UnsaveQueryRequest;
import stocksageservice.activity.results.SaveQueryResult;
import stocksageservice.activity.results.UnsaveQueryResult;
import stocksageservice.converters.ModelConverter;
import stocksageservice.dynamodb.QueryDao;
import stocksageservice.dynamodb.models.Query;
import stocksageservice.models.QueryModel;

import javax.inject.Inject;

public class UnsaveQueryActivity {
    private final Logger log = LogManager.getLogger();
    private final QueryDao queryDao;

    @Inject
    public UnsaveQueryActivity(QueryDao queryDao) {
        this.queryDao = queryDao;
    }

    public UnsaveQueryResult handleRequest(UnsaveQueryRequest unsaveQueryRequest) {
        log.info("Received UnsaveQueryRequest {}", unsaveQueryRequest);

        Query query = queryDao.getQuery(unsaveQueryRequest.getUsername(), unsaveQueryRequest.getQueryId());

        query.setSaved("FALSE");
        query.setTitle("saveQueryRequest.getTitle()");
        query.setTitle(query.getQueryId() + "-" +
                query.getFrequency() + "-" +
                query.getSymbol());

        query.setContent(" ");
        queryDao.saveQuery(query);
        QueryModel queryModel = new ModelConverter().toQueryModel(query);

        return UnsaveQueryResult.builder()
                .withQueryModel(queryModel)
                .build();
    }
}
