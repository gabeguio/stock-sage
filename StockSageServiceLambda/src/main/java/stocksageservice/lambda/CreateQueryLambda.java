package stocksageservice.lambda;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import stocksageservice.activity.requests.CreateQueryRequest;
import stocksageservice.activity.results.CreateQueryResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class CreateQueryLambda
        extends LambdaActivityRunner<CreateQueryRequest, CreateQueryResult>
        implements RequestHandler<AuthenticatedLambdaRequest<CreateQueryRequest>, LambdaResponse> {
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<CreateQueryRequest> input, Context context) {
        return super.runActivity(
                () -> {
                    CreateQueryRequest unauthenticatedRequest = input.fromBody(CreateQueryRequest.class);
                    return input.fromUserClaims(claims ->
                            CreateQueryRequest.builder()
                                    .withUsername(unauthenticatedRequest.getUsername())
                                    .withStartDate(unauthenticatedRequest.getStartDate())
                                    .withEndDate(unauthenticatedRequest.getEndDate())
                                    .withFrequency(unauthenticatedRequest.getFrequency())
                                    .withSymbol(unauthenticatedRequest.getSymbol())
                                    .build());
                },
                (request, serviceComponent) ->
                        serviceComponent.provideCreateQueryActivity().handleRequest(request)
        );
    }
}
