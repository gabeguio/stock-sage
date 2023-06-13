package stocksageservice.alphaadvantageservice;

import org.junit.jupiter.api.Test;
import stocksageservice.alphavantageservice.service.AlphaVantageServiceClient;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNull;

public class AlphaAdvantageServiceClientTest {

    AlphaVantageServiceClient serviceClient = new AlphaVantageServiceClient();

    @Test
    void getStockPrices_succesfulCall_noInputs() {
        //Given

        //When
        Map<String, Object> response = serviceClient.getStocksFromClient();

        //Then
        assertNull(response);
    }

}
