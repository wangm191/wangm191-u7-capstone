package com.nashss.se.eartracker.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.nashss.se.eartracker.activity.request.GetListeningSessionByDateRequest;
import com.nashss.se.eartracker.activity.result.GetListeningSessionByDateResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;

public class GetListeningSessionByDateLambda
        extends LambdaActivityRunner<GetListeningSessionByDateRequest, GetListeningSessionByDateResult>
        implements RequestHandler<AuthenticatedLambdaRequest<GetListeningSessionByDateRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<GetListeningSessionByDateRequest> input, Context context) {
        log.info("handleRequest");
        GetListeningSessionByDateRequest emailRequest = input.fromUserClaims
                (claims -> GetListeningSessionByDateRequest.builder()
                                .withEmail(claims.get("email"))
                                .build());
        
        return super.runActivity(
                () -> input.fromPathAndQuery((path, query) ->
                        GetListeningSessionByDateRequest.builder()
                                .withEmail(emailRequest.getEmail())
                                .withStartSession(LocalDateTime.parse(path.get("startSession")))
                                .build()),
                (request, serviceComponent) ->
                        serviceComponent.provideGetListeningSessionByDateActivity().handleRequest(request)
        );
    }
}
