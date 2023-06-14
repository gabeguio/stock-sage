package stocksageservice.alphaadvantageservice;

import org.junit.jupiter.api.Test;
import stocksageservice.alphavantageservice.GetStocksRequest;
import stocksageservice.alphavantageservice.AlphaVantageServiceClient;
import stocksageservice.alphavantageservice.pojo.Stock;
import stocksageservice.test.helper.GetStocksRequestHelper;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class AlphaAdvantageServiceClientTest {

    AlphaVantageServiceClient serviceClient = new AlphaVantageServiceClient();



    @Test
    void getStockPrices_returnEntirePayload() {
        //Given
        GetStocksRequest stockRequest = GetStocksRequestHelper.generateWeeklyStocksRequest();
        String apiUrl = "https://www.alphavantage.co/query?function=TIME_SERIES_WEEKLY&symbol=IBM&apikey=YOUR_API_KEY";

        //When
        Map<String, Object> response = serviceClient.getEntirePayload(apiUrl);

        //Then
        System.out.println(response);
        assertNotNull(response);
    }

    @Test
    void getStockPrices_returnTimeSeriesFromPayload() {
        //Given
        GetStocksRequest stockRequest = GetStocksRequestHelper.generateWeeklyStocksRequest();
        String symbol = stockRequest.getSymbol();
        String function = stockRequest.getFunction();

        //When
        Map<String, Stock> response = serviceClient.getTimeSeriesFromPayload(symbol, function);

        //Then
        System.out.println(response);
        assertNotNull(response);
    }

}
