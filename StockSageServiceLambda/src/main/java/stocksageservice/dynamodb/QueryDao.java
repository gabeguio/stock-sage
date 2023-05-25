package stocksageservice.dynamodb;

import stocksageservice.dynamodb.models.Query;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
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
}
