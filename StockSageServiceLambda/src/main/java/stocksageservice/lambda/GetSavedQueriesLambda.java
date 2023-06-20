package stocksageservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import stocksageservice.activity.requests.GetSavedQueriesRequest;
import stocksageservice.activity.results.GetSavedQueriesResult;

public class GetSavedQueriesLambda
        extends LambdaActivityRunner<GetSavedQueriesRequest, GetSavedQueriesResult>
        implements RequestHandler<LambdaRequest<GetSavedQueriesRequest>, LambdaResponse> {

    @Override
    public LambdaResponse handleRequest(LambdaRequest<GetSavedQueriesRequest> input, Context context) {
        return super.runActivity(
                () -> input.fromPath(path ->
                        GetSavedQueriesRequest.builder()
                                .withUsername(path.get("username"))
                                .build()),
                (request, serviceComponent) ->
                        serviceComponent.provideGetSavedQueriesActivity().handleRequest(request)
        );
    }
}