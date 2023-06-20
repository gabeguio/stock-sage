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

import static stocksageservice.dynamodb.models.Query.SAVED_INDEX;

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

    public List<Query> getRecentQueriesByUsername(String username) {

        Map<String, AttributeValue> valueMap = new HashMap<>();
        valueMap.put(":username", new AttributeValue().withS(username));

        DynamoDBQueryExpression<Query> queryExpression = new DynamoDBQueryExpression<Query>()
                .withKeyConditionExpression("username = :username")
                .withExpressionAttributeValues(valueMap);

        PaginatedQueryList<Query> queryListFromDynamo = dynamoDbMapper.query(Query.class, queryExpression);

        if (queryListFromDynamo.size() == 0) {
            return new ArrayList<>();
        }

        // Convert Paginated Query List, because it does not support iterators.
        List<Query> queryList = new ArrayList<>(queryListFromDynamo);

        // Sort list in descending order based on queryId
        queryList.sort(Comparator.comparing(Query::getQueryId, Comparator.reverseOrder()));

        // if the list is less than 25 return the sorted queryList
        if (queryListFromDynamo.size() < 25) {
            return queryList;
        }

        // Create list that will store the 25 recent requests
        List<Query> recentQueries = new ArrayList<>();

        for (int i = 0; i < 24; i++) {
            recentQueries.add(queryList.get(i));
        }

        // Return the 25 youngest items based on queryId
        return recentQueries;
    }

    public List<Query> getSavedQueriesByUsername(String username) {

        Map<String, AttributeValue> valueMap = new HashMap<>();
        valueMap.put(":username", new AttributeValue().withS(username));
        valueMap.put(":saved", new AttributeValue().withS("TRUE"));

        DynamoDBQueryExpression<Query> queryExpression = new DynamoDBQueryExpression<Query>()
                .withIndexName(SAVED_INDEX)
                .withConsistentRead(false)
                .withKeyConditionExpression("username = :username and saved = :saved")
                .withExpressionAttributeValues(valueMap);

        PaginatedQueryList<Query> queryList = dynamoDbMapper.query(Query.class, queryExpression);

        if(queryList == null) {
            throw new RuntimeException("queries not found for requested username");
        }

        return queryList;
    }
}
