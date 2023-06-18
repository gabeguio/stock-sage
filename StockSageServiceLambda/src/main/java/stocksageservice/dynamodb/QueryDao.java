package stocksageservice.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import stocksageservice.dynamodb.models.Query;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import javax.inject.Inject;
import java.util.*;

public class QueryDao {
    private final DynamoDBMapper dynamoDbMapper;

    private final Logger log = LogManager.getLogger();

    @Inject
    public QueryDao(DynamoDBMapper dynamoDbMapper) {
        this.dynamoDbMapper = dynamoDbMapper;
    }

    public Query getQuery(String username, String queryId) {
        Query query = this.dynamoDbMapper.load(Query.class, username, queryId);

        return query;
    }

    public Query saveQuery(Query query) {
        this.dynamoDbMapper.save(query);
        return query;
    }

    public List<Query> getRecentQueriesForUsername(String username) {

        Map<String, AttributeValue> valueMap = new HashMap<>();
        valueMap.put(":username", new AttributeValue().withS(username));

        DynamoDBQueryExpression<Query> queryExpression = new DynamoDBQueryExpression<Query>()
                .withKeyConditionExpression("username = :username")
                .withExpressionAttributeValues(valueMap);

        PaginatedQueryList<Query> queryListFromDynamo = dynamoDbMapper.query(Query.class, queryExpression);

        if(queryListFromDynamo == null) {
            throw new RuntimeException("queries not found for requested username");
        }

        // Convert Paginated Query List, because it does not support iterators.
        List<Query> queryList = new ArrayList<>(queryListFromDynamo);

        // Sort list in descending order based on queryId
        queryList.sort(Comparator.comparing(Query::getQueryId, Comparator.reverseOrder()));

        // Return the 10 youngest items based on queryId
        return queryList.subList(0, 10);
    }
}
