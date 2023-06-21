package stocksageservice.activity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import stocksageservice.activity.requests.UpdateTitleAndContentRequest;
import stocksageservice.activity.results.UpdateTitleAndContentResult;
import stocksageservice.converters.ModelConverter;
import stocksageservice.dynamodb.QueryDao;
import stocksageservice.dynamodb.models.Query;
import stocksageservice.models.QueryModel;

import javax.inject.Inject;

public class UpdateTitleAndContentActivity {

    private final Logger log = LogManager.getLogger();
    private final QueryDao queryDao;

    @Inject
    public UpdateTitleAndContentActivity(QueryDao queryDao) {
        this.queryDao = queryDao;
    }

    public UpdateTitleAndContentResult handleRequest(UpdateTitleAndContentRequest UpdateTitleAndContentRequest) {
        log.info("Received UpdateTitleAndContentRequest {}", UpdateTitleAndContentRequest);

        Query query = queryDao.getQuery(UpdateTitleAndContentRequest.getUsername(), UpdateTitleAndContentRequest.getQueryId());
        
        query.setTitle(UpdateTitleAndContentRequest.getTitle());
        query.setContent(UpdateTitleAndContentRequest.getContent());
        queryDao.saveQuery(query);
        QueryModel queryModel = new ModelConverter().toQueryModel(query);

        return UpdateTitleAndContentResult.builder()
                .withQueryModel(queryModel)
                .build();
    }
}
