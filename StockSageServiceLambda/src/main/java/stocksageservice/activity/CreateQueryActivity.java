package stocksageservice.activity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import stocksageservice.activity.requests.CreateQueryRequest;
import stocksageservice.activity.results.CreateQueryResult;
import stocksageservice.alphavantageservice.GetStocksActivity;
import stocksageservice.alphavantageservice.GetStocksRequest;
import stocksageservice.alphavantageservice.StockModel;
import stocksageservice.dynamodb.QueryDao;
import stocksageservice.dynamodb.models.Query;
import stocksageservice.utils.EpochTimeDateConversion;

import javax.inject.Inject;
import java.util.Collections;
import java.util.Comparator;
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
        String symbol = createQueryRequest.getSymbol();

        long currentTimeInEpoch = System.currentTimeMillis();
        String epochToDateTime = EpochTimeDateConversion.convertEpochToDateTime(currentTimeInEpoch);
        String epochToDate = EpochTimeDateConversion.convertEpochToDate(currentTimeInEpoch);

        Query newQuery = new Query();
        newQuery.setUsername(createQueryRequest.getUsername());
        newQuery.setQueryId(epochToDateTime);
        newQuery.setDateRequested(epochToDate);
        newQuery.setStartDate(startDate);
        newQuery.setEndDate(endDate);
        newQuery.setFrequency(function);
        newQuery.setSymbol(symbol);
        newQuery.setSaved("FALSE");
        newQuery.setTitle(epochToDateTime + "-" + function + "-" + symbol);
        newQuery.setContent(" ");

        queryDao.saveQuery(newQuery);

        GetStocksActivity getStocksActivity = new GetStocksActivity();

        GetStocksRequest getStocksRequest = new GetStocksRequest(startDate, endDate, function, symbol);
        List<StockModel> stockList = getStocksActivity.handlRequest(getStocksRequest);

        stockList.sort(Comparator.comparing(StockModel::getDate).reversed());

        return CreateQueryResult.builder().
                withStockModels(stockList)
                .build();
    }
}
