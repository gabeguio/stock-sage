package stocksageservice.alphavantageservice.activity.request;

public class GetStockRequest {

    private final String API_KEY = "YOUR_API_KEY";
    private final String API_BASE_URL = "https://www.alphavantage.co/query";

    private String getApiUrl(String symbol, String function) {
        String apiKeyParam = "apikey=" + API_KEY;
        String functionParam = "function=" + function;
        String symbolParam = "symbol=" + symbol;

        return API_BASE_URL + "?" + apiKeyParam + "&" + symbolParam + "&" + functionParam;
    }



}
