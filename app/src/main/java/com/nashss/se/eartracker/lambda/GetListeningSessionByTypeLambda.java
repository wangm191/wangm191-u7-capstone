package com.nashss.se.eartracker.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.nashss.se.eartracker.activity.request.GetListeningSessionByTypeRequest;
import com.nashss.se.eartracker.activity.result.GetListeningSessionByTypeResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetListeningSessionByTypeLambda
        extends LambdaActivityRunner<GetListeningSessionByTypeRequest, GetListeningSessionByTypeResult>
        implements RequestHandler<AuthenticatedLambdaRequest<GetListeningSessionByTypeRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<GetListeningSessionByTypeRequest> input, Context context) {
        log.info("handleRequest");
        GetListeningSessionByTypeRequest emailRequest = input.fromUserClaims
                (claims -> GetListeningSessionByTypeRequest.builder()
                        .withEmail(claims.get("email"))
                        .build());

        return super.runActivity(
                () -> input.fromPathAndQuery((path, query) ->
                        GetListeningSessionByTypeRequest.builder()
                                .withEmail(emailRequest.getEmail())
                                .withListeningType(path.get("listeningType"))
                                .build()),
                (request, serviceComponent) ->
                        serviceComponent.provideGetListeningSessionByTypeActivity().handleRequest(request)
        );
    }
}
