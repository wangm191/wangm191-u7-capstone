package com.nashss.se.eartracker.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.nashss.se.eartracker.lambda.LambdaRequest;

public class GetListeningSession implements RequestHandler<LambdaRequest<String>, LambdaResponse> {

    @Override
    public LambdaResponse handleRequest(LambdaRequest<String> input, Context context) {
        return LambdaResponse.success();
    }
}
