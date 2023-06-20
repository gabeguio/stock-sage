package stocksageservice.test.helper;

import stocksageservice.dynamodb.models.Query;

public final class QueryTestHelper {
    private QueryTestHelper() {
    }

    public static Query generateQuery() {
        Query query = new Query();
        query.setUsername("fake@email.com");
        query.setQueryId("2023-06-05 00:00:01");
        query.setDateRequested("2023-06-05");
        query.setStartDate("2022-10-01");
        query.setEndDate("2023-05-01");
        query.setFrequency("TIME_SERIES_WEEKLY");
        query.setSymbol("NKE");
        query.setSaved("FALSE");
        query.setContent("2023-06-05 0:00:01-TIME_SERIES_WEEKLY-NKE");
        query.setContent(" ");

        return query;
    }

}
