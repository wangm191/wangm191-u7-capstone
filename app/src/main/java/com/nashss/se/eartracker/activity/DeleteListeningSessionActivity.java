package com.nashss.se.eartracker.activity;

import com.nashss.se.eartracker.activity.request.DeleteListeningSessionRequest;
import com.nashss.se.eartracker.activity.result.DeleteListeningSessionResult;
import com.nashss.se.eartracker.converters.ModelConverter;
import com.nashss.se.eartracker.dynamodb.ListeningSessionDao;
import com.nashss.se.eartracker.dynamodb.models.ListeningSession;
import com.nashss.se.eartracker.exceptions.ListeningSessionNotFoundException;
import com.nashss.se.eartracker.models.ListeningSessionModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

public class DeleteListeningSessionActivity {
    private final Logger logger = LogManager.getLogger();
    private final ListeningSessionDao listeningSessionDao;

    @Inject
    public DeleteListeningSessionActivity(ListeningSessionDao listeningSessionDao) {
        this.listeningSessionDao = listeningSessionDao;
    }

    public DeleteListeningSessionResult handleRequest (final DeleteListeningSessionRequest deleteListeningSessionRequest){
        logger.error("Received DeleteListeningSessionRequest {}", deleteListeningSessionRequest);

        if (!deleteListeningSessionRequest.validRequestDelete()){
        }

        ListeningSession listeningSession = listeningSessionDao.getListeningSession(deleteListeningSessionRequest.getEmail(), deleteListeningSessionRequest.getStartSession());
        
        if (!listeningSession.getEmail().equals(deleteListeningSessionRequest.getEmail()) || 
        !listeningSession.getStartSession().equals(deleteListeningSessionRequest.getStartSession()) ||
        !listeningSession.getListeningType().equals(listeningSession.getListeningType())) {
            throw new ListeningSessionNotFoundException("Listening session not found, request is invalid");
        }
        
        listeningSession.setEmail(deleteListeningSessionRequest.getEmail());
        listeningSession.setStartSession(deleteListeningSessionRequest.getStartSession());
        listeningSession.setListeningType(deleteListeningSessionRequest.getListeningType());

        listeningSessionDao.deleteListeningSession(listeningSession);

        ListeningSessionModel listeningSessionModel = new ModelConverter().toListeningSessionModel(listeningSession);
        return DeleteListeningSessionResult.builder()
                .withListeningSession(listeningSessionModel)
                .build();
    }
}

