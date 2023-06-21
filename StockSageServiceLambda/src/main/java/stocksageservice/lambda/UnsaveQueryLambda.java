package stocksageservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import stocksageservice.activity.requests.UnsaveQueryRequest;
import stocksageservice.activity.results.UnsaveQueryResult;

public class UnsaveQueryLambda
        extends LambdaActivityRunner<UnsaveQueryRequest, UnsaveQueryResult>
        implements RequestHandler<AuthenticatedLambdaRequest<UnsaveQueryRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<UnsaveQueryRequest> input, Context context) {
        return super.runActivity(
                () -> {
                    UnsaveQueryRequest unauthenticatedRequest = input.fromBody(UnsaveQueryRequest.class);
                    return input.fromUserClaims(claims ->
                            UnsaveQueryRequest.builder()
                                    .withUsername(unauthenticatedRequest.getUsername())
                                    .withQueryId(unauthenticatedRequest.getQueryId())
                                    .build());
                },
                (request, serviceComponent) ->
                        serviceComponent.provideUnsaveQueryActivity().handleRequest(request)
        );
    }
}