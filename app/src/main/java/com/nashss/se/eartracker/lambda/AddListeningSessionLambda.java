package com.nashss.se.eartracker.lambda;

import com.nashss.se.eartracker.activity.request.AddListeningSessionRequest;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.nashss.se.eartracker.activity.result.AddListeningSessionResult;

public class AddListeningSessionLambda
        extends LambdaActivityRunner<AddListeningSessionRequest, AddListeningSessionResult>
        implements RequestHandler<AuthenticatedLambdaRequest<AddListeningSessionRequest>, LambdaResponse> {
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<AddListeningSessionRequest> input, Context context) {
        return super.runActivity(
                () -> {
                    AddListeningSessionRequest unauthenticatedRequest = input.fromBody(AddListeningSessionRequest.class);
                    return input.fromUserClaims(claims ->
                            AddListeningSessionRequest.builder()
                                    .withEmail(claims.get("email"))
                                    .withStartSession(unauthenticatedRequest.getStartSession())
                                    .withEndSession(unauthenticatedRequest.getEndSession())
                                    .withListeningType(unauthenticatedRequest.getListeningType())
                                    .withNotes(unauthenticatedRequest.getNotes())
                                    .build());
                },
                (request, serviceComponent) ->
                        serviceComponent.provideAddListeningSessionActivity().handleRequest(request)
        );
    }
}

