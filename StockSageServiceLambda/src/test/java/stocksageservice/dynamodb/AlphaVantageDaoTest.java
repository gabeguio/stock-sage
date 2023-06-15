package stocksageservice.dynamodb;

import org.junit.jupiter.api.Test;
import stocksageservice.alphavantageservice.GetStocksRequest;
import stocksageservice.alphavantageservice.AlphaVantageDao;
import stocksageservice.alphavantageservice.StockModel;
import stocksageservice.test.helper.GetStocksRequestHelper;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AlphaVantageDaoTest {

    private AlphaVantageDao alphaVantageDao = new AlphaVantageDao();

    private GetStocksRequest getStocksRequest = GetStocksRequestHelper.generateWeeklyStocksRequest();

    @Test
    public void getDatesInRange_withStartAndEndDate_returnsTheDateRangesInDescendingOrder() {
        //Given
        String startDate = getStocksRequest.getStartDate();
        String endDate = getStocksRequest.getEndDate();
        String function = getStocksRequest.getFunction();
        String symbol = getStocksRequest.getSymbol();


        //When
        List<StockModel> response = alphaVantageDao.getDatesInRange(startDate, endDate, function, symbol);

        //Then
        assertNotNull(response);
    }

    @Test
    public void getDatesInRange_withWeeklyOffsetStartAndEndDate_returnsTheDateRangesInDescendingOrder() {
        //Given
        String startDate = "2023-04-29";
        String endDate = "2023-06-01";
        String function = getStocksRequest.getFunction();
        String symbol = getStocksRequest.getSymbol();



        //When
        List<StockModel> response = alphaVantageDao.getDatesInRange(startDate, endDate, function, symbol);

        //Then
        assertNotNull(response);
    }
}
