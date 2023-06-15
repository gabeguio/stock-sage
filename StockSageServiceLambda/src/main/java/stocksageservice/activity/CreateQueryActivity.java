package stocksageservice.activity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import stocksageservice.activity.requests.CreateQueryRequest;
import stocksageservice.activity.results.CreateQueryResult;
import stocksageservice.alphavantageservice.AlphaVantageDao;
import stocksageservice.alphavantageservice.GetStocksActivity;
import stocksageservice.alphavantageservice.GetStocksRequest;
import stocksageservice.alphavantageservice.StockModel;
import stocksageservice.converters.ModelConverter;
import stocksageservice.dynamodb.QueryDao;
import stocksageservice.dynamodb.models.Query;
import stocksageservice.models.QueryModel;

import javax.inject.Inject;
import java.util.List;

public class CreateQueryActivity {
    private final Logger log = LogManager.getLogger();
    private final QueryDao queryDao;

    @Inject
    public CreateQueryActivity(QueryDao queryDao) {
        this.queryDao = queryDao;
    }

    public CreateQueryResult handleRequest(final CreateQueryRequest createQueryRequest) {
        log.info("Received CreateQueryRequest {}", createQueryRequest);

        String startDate = createQueryRequest.getStartDate();
        String endDate = createQueryRequest.getEndDate();
        String function = createQueryRequest.getFrequency();
        String symbol = createQueryRequest.getStartDate();

        Query newQuery = new Query();
        newQuery.setUsername(createQueryRequest.getUsername());
        newQuery.setQueryId(createQueryRequest.getQueryId());
        newQuery.setDateRequested(createQueryRequest.getDateRequested());
        newQuery.setStartDate(startDate);
        newQuery.setEndDate(endDate);
        newQuery.setFrequency(function);
        newQuery.setSymbol(symbol);
        newQuery.setSaved(createQueryRequest.getSaved());

        queryDao.saveQuery(newQuery);

        GetStocksActivity getStocksActivity = new GetStocksActivity();
        GetStocksRequest getStocksRequest = new GetStocksRequest(startDate, endDate, function, symbol);
        List<StockModel> stockModels = getStocksActivity.handlRequest(getStocksRequest);

        return CreateQueryResult.builder().
                withStockModels(stockModels)
                .build();
    }
}
