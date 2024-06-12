package com.nashss.se.eartracker.lambda;

import com.nashss.se.eartracker.activity.request.EditListeningSessionRequest;
import com.nashss.se.eartracker.activity.result.EditListeningSessionResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class EditListeningSessionLambda
        extends LambdaActivityRunner<EditListeningSessionRequest, EditListeningSessionResult>
        implements RequestHandler<AuthenticatedLambdaRequest<EditListeningSessionRequest>, LambdaResponse> {
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<EditListeningSessionRequest> input, Context context) {
        return super.runActivity(
                () -> {
                    EditListeningSessionRequest unauthenticatedRequest = input.fromBody(EditListeningSessionRequest.class);
                    return input.fromUserClaims(claims ->

                            EditListeningSessionRequest.builder()
                                    .withEmail(claims.get("email"))
                                    .withStartSession(unauthenticatedRequest.getStartSession())
                                    .withNewStartSession(unauthenticatedRequest.getNewStartSession())
                                    .withEndSession(unauthenticatedRequest.getEndSession())
                                    .withListeningType(unauthenticatedRequest.getListeningType())
                                    .withNotes(unauthenticatedRequest.getNotes())
                                    .build());
                },
                (request, serviceComponent) ->
                        serviceComponent.provideEditListeningSessionActivity().handleRequest(request)
        );
    }
}

