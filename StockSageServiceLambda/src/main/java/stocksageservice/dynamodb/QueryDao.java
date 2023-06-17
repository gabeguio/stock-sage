package stocksageservice.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import stocksageservice.dynamodb.models.Query;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import stocksageservice.utils.EpochTimeDateConversion;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        PaginatedQueryList<Query> queryList = dynamoDbMapper.query(Query.class, queryExpression);

        if(queryList == null) {
            throw new RuntimeException("queries not found for requested username");
        }

        int limit = 10;

        List<Query> recentQueryList = new ArrayList<>();

        for (int i = 0; i <= limit; i++) {
            recentQueryList.add(queryList.get(i));
        }

        return recentQueryList;
    }
}
