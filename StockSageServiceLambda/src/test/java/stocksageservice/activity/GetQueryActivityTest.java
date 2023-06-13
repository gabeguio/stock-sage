package stocksageservice.activity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import stocksageservice.activity.requests.GetQueryRequest;
import stocksageservice.activity.results.GetQueryResult;
import stocksageservice.dynamodb.QueryDao;
import stocksageservice.dynamodb.models.Query;
import stocksageservice.test.helper.QueryTestHelper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class GetQueryActivityTest {

    //When the activity class is called by a response, then a result should return
    //Mock the response and inputs for all internal methods

    @Mock
    private QueryDao queryDao;
    private GetQueryActivity getQueryActivity;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        getQueryActivity = new GetQueryActivity(queryDao);
    }

    @Test
    public void handleRequest_getQuery_returnQueryModelInResult() {
        //Given
        Query query = QueryTestHelper.generateQuery();
        String validUsername = query.getUsername();
        String validQueryId = query.getQueryId();

        when(queryDao.getQuery(validUsername, validQueryId)).thenReturn(query);

        GetQueryRequest request = GetQueryRequest.builder()
                .withUsername(validUsername)
                .withQueryId(validQueryId)
                .build();

        //When
        GetQueryResult result = getQueryActivity.handleRequest(request);

        //Then
        assertEquals(query.getUsername(), result.getQuery().getUsername());
        assertEquals(query.getQueryId(), result.getQuery().getQueryId());
        assertEquals(query.getDateRequested(), result.getQuery().getDateRequested());
        assertEquals(query.getStartDate(), result.getQuery().getStartDate());
        assertEquals(query.getEndDate(), result.getQuery().getStartDate());
        assertEquals(query.getFrequency(), result.getQuery().getFrequency());
        assertEquals(query.getSymbol(), result.getQuery().getSymbol());
        assertEquals(query.getSaved(), result.getQuery().getSaved());
    }

}
