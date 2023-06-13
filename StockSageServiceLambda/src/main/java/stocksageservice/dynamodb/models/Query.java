package stocksageservice.dynamodb.models;

import com.amazonaws.services.dynamodbv2.datamodeling.*;

import java.util.Objects;

/**
 * Represents a record in the queries table.
 */
@DynamoDBTable(tableName = "queries")
public class Query {
    private String username;
    private String queryId;
    private String dateRequested;
    private String startDate;
    private String endDate;
    private String frequency;
    private String symbol;
    private String saved;

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

    @DynamoDBAttribute(attributeName = "dateRequested")
    public String getDateRequested() {
        return dateRequested;
    }

    public void setDateRequested(String dateRequested) {
        this.dateRequested = dateRequested;
    }

    @DynamoDBAttribute(attributeName = "startDate")
    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    @DynamoDBAttribute(attributeName = "endDate")
    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @DynamoDBAttribute(attributeName = "frequency")
    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    @DynamoDBAttribute(attributeName = "symbol")
    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @DynamoDBAttribute(attributeName = "saved")
    public String getSaved() {
        return saved;
    }

    public void setSaved(String saved) {
        this.saved = saved;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Query query = (Query) o;
        return Objects.equals(username, query.username) &&
                Objects.equals(queryId, query.queryId) &&
                Objects.equals(dateRequested, query.dateRequested) &&
                Objects.equals(startDate, query.startDate) &&
                Objects.equals(endDate, query.endDate) &&
                Objects.equals(frequency, query.frequency) &&
                Objects.equals(symbol, query.symbol) &&
                Objects.equals(saved, query.saved);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, queryId, dateRequested, startDate, endDate, frequency, symbol, saved);
    }
}
