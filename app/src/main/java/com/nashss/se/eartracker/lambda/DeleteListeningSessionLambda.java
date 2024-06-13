package com.nashss.se.eartracker.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.nashss.se.eartracker.activity.request.DeleteListeningSessionRequest;
import com.nashss.se.eartracker.activity.result.DeleteListeningSessionResult;

public class DeleteListeningSessionLambda
        extends LambdaActivityRunner<DeleteListeningSessionRequest, DeleteListeningSessionResult>
        implements RequestHandler<AuthenticatedLambdaRequest<DeleteListeningSessionRequest>, LambdaResponse> {
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<DeleteListeningSessionRequest> input, Context context) {
        return super.runActivity(
                () -> {
                    DeleteListeningSessionRequest unauthenticatedRequest = input.fromBody(DeleteListeningSessionRequest.class);
                    return input.fromUserClaims(claims ->
                            DeleteListeningSessionRequest.builder()
                                    .withEmail(claims.get("email"))
                                    .withStartSession(unauthenticatedRequest.getStartSession())
                                    .withListeningType(unauthenticatedRequest.getListeningType())
                                    .build());
                },
                (request, component) ->
                        component.provideDeleteListeningSessionActivity().handleRequest(request)
        );
    }
}
