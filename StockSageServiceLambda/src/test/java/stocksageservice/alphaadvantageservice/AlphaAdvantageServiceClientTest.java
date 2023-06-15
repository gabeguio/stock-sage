package stocksageservice.alphaadvantageservice;

import com.fasterxml.jackson.databind.JsonNode;
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
    void getStockPrices_returnTimeSeriesFromPayload() {
        //Given
        GetStocksRequest stockRequest = GetStocksRequestHelper.generateWeeklyStocksRequest();
        String symbol = stockRequest.getSymbol();
        String function = stockRequest.getFunction();

        //When
        Map<String, JsonNode> response = serviceClient.getTimeSeriesFromPayload(function, symbol);

        //Then
        System.out.println(response);
        assertNotNull(response);
    }

}
