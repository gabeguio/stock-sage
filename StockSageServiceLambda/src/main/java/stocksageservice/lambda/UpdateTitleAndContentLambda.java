package stocksageservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import stocksageservice.activity.requests.UpdateTitleAndContentRequest;
import stocksageservice.activity.results.UpdateTitleAndContentResult;

public class UpdateTitleAndContentLambda
        extends LambdaActivityRunner<UpdateTitleAndContentRequest, UpdateTitleAndContentResult>
        implements RequestHandler<AuthenticatedLambdaRequest<UpdateTitleAndContentRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<UpdateTitleAndContentRequest> input, Context context) {
        return super.runActivity(
                () -> {
                    UpdateTitleAndContentRequest unauthenticatedRequest = input.fromBody(UpdateTitleAndContentRequest.class);
                    return input.fromUserClaims(claims ->
                            UpdateTitleAndContentRequest.builder()
                                    .withUsername(unauthenticatedRequest.getUsername())
                                    .withQueryId(unauthenticatedRequest.getQueryId())
                                    .withTitle(unauthenticatedRequest.getTitle())
                                    .withContent(unauthenticatedRequest.getContent())
                                    .build());
                },
                (request, serviceComponent) ->
                        serviceComponent.provideUpdateTitleAndContentActivity().handleRequest(request)
        );
    }
}