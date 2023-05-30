package stocksageservice.dynamodb.models;

import com.amazonaws.services.dynamodbv2.datamodeling.*;

import java.util.Objects;

/**
 * Represents a record in the playlists table.
 */
@DynamoDBTable(tableName = "queries")
public class Query {
    private String username;
    private String queryId;

    @DynamoDBHashKey(attributeName = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @DynamoDBRangeKey(attributeName = "queryId")
    public String getQueryId() {
        return queryId;
    }

    public void setQueryId(String queryId) {
        this.queryId = queryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Query query = (Query) o;
        return Objects.equals(username, query.username) && Objects.equals(queryId, query.queryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, queryId);
    }
}
