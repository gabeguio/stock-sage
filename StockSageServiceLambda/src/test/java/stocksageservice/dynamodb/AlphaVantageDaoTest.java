package stocksageservice.dynamodb;

import org.junit.jupiter.api.Test;
import stocksageservice.alphavantageservice.GetStocksRequest;
import stocksageservice.alphavantageservice.AlphaVantageDao;
import stocksageservice.test.helper.GetStocksRequestHelper;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AlphaVantageDaoTest {

    private AlphaVantageDao alphaVantageDao = new AlphaVantageDao();

    private GetStocksRequest getStocksRequest = GetStocksRequestHelper.generateWeeklyStocksRequest();

    @Test
    public void getDatesInRange_withStartAndEndDate_returnsTheDateRangesInDescendingOrder() {
        //Given
        String fromDate = getStocksRequest.getStartDate();
        String toDate = getStocksRequest.getEndDate();
        String symbol = getStocksRequest.getSymbol();
        String function = getStocksRequest.getFunction();

        //When
        Map<String, Object> response = alphaVantageDao.getDatesInRange(fromDate, toDate, symbol, function);

        //Then
        System.out.println(response.keySet());
        assertNotNull(response);
    }

    @Test
    public void getDatesInRange_withWeeklyOffsetStartAndEndDate_returnsTheDateRangesInDescendingOrder() {
        //Given
        String fromDate = "2023-04-29";
        String toDate = "2023-06-01";
        String symbol = getStocksRequest.getSymbol();
        String function = getStocksRequest.getFunction();


        //When
        Map<String, Object> response = alphaVantageDao.getDatesInRange(fromDate, toDate, symbol, function);

        //Then
        System.out.println(response.keySet());
        assertNotNull(response);
    }
}
