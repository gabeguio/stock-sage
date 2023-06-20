package stocksageservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.RequestHandler;
import stocksageservice.activity.requests.GetRecentQueriesRequest;
import stocksageservice.activity.results.GetRecentQueriesResult;


public class GetRecentQueriesLambda
    extends LambdaActivityRunner<GetRecentQueriesRequest, GetRecentQueriesResult>
    implements RequestHandler<LambdaRequest<GetRecentQueriesRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(LambdaRequest<GetRecentQueriesRequest> input, Context context) {
        return super.runActivity(
                () -> input.fromPath(path ->
                        GetRecentQueriesRequest.builder()
                                .withUsername(path.get("username"))
                                .build()),
                (request, serviceComponent) ->
                        serviceComponent.provideGetRecentQueriesActivity().handleRequest(request)
        );
    }
}
