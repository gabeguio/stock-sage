package stocksageservice.test.helper;

import stocksageservice.dynamodb.models.Query;

public final class QueryTestHelper {
    private QueryTestHelper() {
    }

    public static Query generateQuery() {
        Query query = new Query();
        query.setUsername("fake@email.com");
        query.setQueryId("2023-06-05T00:00:01");
        query.setDateRequested("2023-06-05"); // May want to be a date.now(), later in testing
        query.setFromDate("2022-10-01");
        query.setToDate("2023-05-01");
        query.setFrequency("MONTHLY");
        query.setSymbol("NKE");
        query.setSaved("FALSE");

        return query;
    }

}