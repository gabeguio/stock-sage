package stocksageservice.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import stocksageservice.dynamodb.models.Query;
import stocksageservice.test.helper.QueryTestHelper;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class QueryDaoTest {

    //When any method inside the QueryDao is called, then return the correct input based on the correct output.
    //Mock all the method arguments and internal methods for each method in QueryDao

    @Mock
    private DynamoDBMapper dynamoDBMapper;

    private QueryDao queryDao;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        queryDao = new QueryDao(dynamoDBMapper);
    }

    @Test
    public void getQuery_withUsernameAndQueryId_callsMapperWithPartitionAndSortKey() {
        //Given
        Query query = QueryTestHelper.generateQuery();
        String validUsername = query.getUsername();
        String validQueryId = query.getQueryId();

        when(queryDao.getQuery(validUsername, validQueryId)).thenReturn(query);

        //When
        Query queryResponse = queryDao.getQuery(validUsername, validQueryId);

        //Then
        assertNotNull(queryResponse);
        verify(dynamoDBMapper).load(Query.class, validUsername, validQueryId);
    }
}
