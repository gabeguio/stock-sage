package stocksageservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import stocksageservice.activity.requests.SaveQueryRequest;
import stocksageservice.activity.results.SaveQueryResult;

public class SaveQueryLambda
        extends LambdaActivityRunner<SaveQueryRequest, SaveQueryResult>
        implements RequestHandler<LambdaRequest<SaveQueryRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(LambdaRequest<SaveQueryRequest> input, Context context) {
        log.info("handleRequest");
        return super.runActivity(
                () -> input.fromPath(path ->
                        SaveQueryRequest.builder()
                                .withUsername(path.get("username"))
                                .withQueryId(path.get("queryId"))
                                .build()),
                (request, serviceComponent) ->
                        serviceComponent.provideSaveQueryActivity().handleRequest(request)
        );
    }
}