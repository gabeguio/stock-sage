package stocksageservice.converters;

import org.junit.jupiter.api.Test;
import stocksageservice.dynamodb.models.Query;
import stocksageservice.models.QueryModel;
import stocksageservice.test.helper.QueryTestHelper;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ModelConverterTest {

    //When a model converter is called is should return the thing it's converting too
    //Mock the inputs of the converter and all internal methods.

    private ModelConverter modelConverter = new ModelConverter();

    @Test
    void toQueryModel_convertsQuery() {
        Query query = QueryTestHelper.generateQuery();

        QueryModel queryModel = modelConverter.toQueryModel(query);

        assertEquals(query.getUsername(), queryModel.getUsername());
        assertEquals(query.getQueryId(), queryModel.getQueryId());
        assertEquals(query.getDateRequested(), queryModel.getDateRequested());
        assertEquals(query.getFromDate(), queryModel.getFromDate());
        assertEquals(query.getToDate(), queryModel.getToDate());
        assertEquals(query.getFrequency(), queryModel.getFrequency());
        assertEquals(query.getSymbol(), queryModel.getSymbol());
        assertEquals(query.getSaved(), queryModel.getSaved());
    }
}
