package stocksageservice.activity;

import stocksageservice.activity.requests.SaveQueryRequest;
import stocksageservice.activity.results.SaveQueryResult;
import stocksageservice.converters.ModelConverter;
import stocksageservice.dynamodb.QueryDao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import stocksageservice.dynamodb.models.Query;
import stocksageservice.models.QueryModel;

import javax.inject.Inject;

public class SaveQueryActivity {

    private final Logger log = LogManager.getLogger();
    private final QueryDao queryDao;

    @Inject
    public SaveQueryActivity(QueryDao queryDao) {
        this.queryDao = queryDao;
    }

    public SaveQueryResult handleRequest(SaveQueryRequest saveQueryRequest) {
        log.info("Received SaveQueryRequest {}", saveQueryRequest);

        Query query = queryDao.getQuery(saveQueryRequest.getUsername(), saveQueryRequest.getQueryId());

        query.setSaved("TRUE");
        query.setTitle(query.getQueryId());
        query.setContent("Notes: Capture your stock price observations and insights here.");
        queryDao.saveQuery(query);
        QueryModel queryModel = new ModelConverter().toQueryModel(query);

        return SaveQueryResult.builder()
                .withQueryModel(queryModel)
                .build();
    }
}
