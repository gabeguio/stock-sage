package stocksageservice.dynamodb;

import org.junit.jupiter.api.Test;
import stocksageservice.alphavantageservice.datahandler.AlphaVantageDao;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AlphaVantageDaoTest {

    private AlphaVantageDao alphaVantageDao = new AlphaVantageDao();

    @Test
    public void getDatesInRange_withStartAndEndDate_returnsTheDateRangesInDescendingOrder() {
        //Given
        String startDate = "2021-08-31";
        String endDate = "2022-11-30";

        //When
        List<String> response = alphaVantageDao.getDatesInRange(startDate, endDate);

        //Then
        assertNotNull(response);
    }

    @Test
    public void getDatesInRange_withOffsetStartAndEndDate_returnsTheDateRangesInDescendingOrder() {
        //Given
        String startDate = "2021-08-31";
        String endDate = "2022-11-30";


        //When
        List<String> response = alphaVantageDao.getDatesInRange(startDate, endDate);

        //Then
        assertNotNull(response);
    }
}
