package stocksageservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import stocksageservice.activity.requests.SaveQueryRequest;
import stocksageservice.activity.results.SaveQueryResult;

public class SaveQueryLambda
        extends LambdaActivityRunner<SaveQueryRequest, SaveQueryResult>
        implements RequestHandler<AuthenticatedLambdaRequest<SaveQueryRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<SaveQueryRequest> input, Context context) {
        return super.runActivity(
                () -> {
                    SaveQueryRequest unauthenticatedRequest = input.fromBody(SaveQueryRequest.class);
                    return input.fromUserClaims(claims ->
                            SaveQueryRequest.builder()
                                    .withUsername(unauthenticatedRequest.getUsername())
                                    .withQueryId(unauthenticatedRequest.getQueryId())
                                    .withTitle(unauthenticatedRequest.getTitle())
                                    .withContent(unauthenticatedRequest.getContent())
                                    .build());
                },
                (request, serviceComponent) ->
                        serviceComponent.provideSaveQueryActivity().handleRequest(request)
        );
    }
}