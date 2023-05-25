package stocksageservice.lambda;

import stocksageservice.activity.requests.GetQueryRequest;
import stocksageservice.activity.results.GetQueryResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetQueryLambda
        extends LambdaActivityRunner<GetQueryRequest, GetQueryResult>
        implements RequestHandler<LambdaRequest<GetQueryRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(LambdaRequest<GetQueryRequest> input, Context context) {
        log.info("handleRequest");
        return super.runActivity(
                () -> input.fromPath(path ->
                        GetQueryRequest.builder()
                                .withUsername(path.get("username"))
                                .withQueryId(path.get("queryId"))
                                .build()),
                (request, serviceComponent) ->
                        serviceComponent.provideGetQueryActivity().handleRequest(request)
        );
    }
}