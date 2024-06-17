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
        implements RequestHandler<LambdaRequest<GetListeningSessionByDateRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(LambdaRequest<GetListeningSessionByDateRequest> input, Context context) {
        log.info("handleRequest");
        return super.runActivity(
                () -> input.fromPathAndQuery((path, query) ->
                        GetListeningSessionByDateRequest.builder()
                                .withEmail(path.get("email"))
                                .withStartSession(LocalDateTime.parse(query.get("startSession")))
                                .build()),
                (request, serviceComponent) ->
                        serviceComponent.provideGetListeningSessionByDateActivity().handleRequest(request)
        );
    }
}
