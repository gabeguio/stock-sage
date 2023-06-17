package stocksageservice.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import stocksageservice.dynamodb.models.Query;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryDao {
    private final DynamoDBMapper dynamoDbMapper;

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

    public List<Query> getRecentTicketsForUsername(String username) {
        Map<String, AttributeValue> valueMap = new HashMap<>();
        valueMap.put(":username", new AttributeValue().withS(username));

        DynamoDBQueryExpression<Query> queryExpression = new DynamoDBQueryExpression<Query>()
                .withKeyConditionExpression("username = :username")
                .withLimit(10)
                .withScanIndexForward(false)
                .withExpressionAttributeValues(valueMap);

        PaginatedQueryList<Query> queryList = dynamoDbMapper.query(Query.class, queryExpression);

        if(queryList == null) {
            throw new RuntimeException("tickets not found for requested username");
        }

        return queryList;
    }
}
