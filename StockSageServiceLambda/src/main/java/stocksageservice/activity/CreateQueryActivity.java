package stocksageservice.activity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import stocksageservice.activity.requests.CreateQueryRequest;
import stocksageservice.activity.results.CreateQueryResult;
import stocksageservice.converters.ModelConverter;
import stocksageservice.dynamodb.QueryDao;
import stocksageservice.dynamodb.models.Query;
import stocksageservice.models.QueryModel;

import javax.inject.Inject;

public class CreateQueryActivity {
    private final Logger log = LogManager.getLogger();
    private final QueryDao queryDao;

    @Inject
    public CreateQueryActivity(QueryDao queryDao) {
        this.queryDao = queryDao;
    }

    public CreateQueryResult handleRequest(final CreateQueryRequest createQueryRequest) {
        log.info("Received CreateQueryRequest {}", createQueryRequest);

        Query newQuery = new Query();
        newQuery.setUsername(createQueryRequest.getUsername());
        newQuery.setQueryId(createQueryRequest.getQueryId());
        newQuery.setDateRequested(createQueryRequest.getDateRequested());
        newQuery.setFromDate(createQueryRequest.getFromDate());
        newQuery.setToDate(createQueryRequest.getToDate());
        newQuery.setFrequency(createQueryRequest.getFrequency());
        newQuery.setSymbol(createQueryRequest.getSymbol());
        newQuery.setSaved(createQueryRequest.getSaved());

        queryDao.saveQuery(newQuery);

        QueryModel queryModel = new ModelConverter().toQueryModel(newQuery);
        return CreateQueryResult.builder().
                withQueryModel(queryModel)
                .build();
    }
}
