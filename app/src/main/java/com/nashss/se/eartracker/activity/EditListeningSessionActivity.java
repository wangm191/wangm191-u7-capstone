package com.nashss.se.eartracker.activity;

import com.amazonaws.http.timers.client.ClientExecutionAbortTaskImpl;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.nashss.se.eartracker.activity.request.EditListeningSessionRequest;
import com.nashss.se.eartracker.activity.result.EditListeningSessionResult;
import com.nashss.se.eartracker.calculator.TimeElapsedCalculator;
import com.nashss.se.eartracker.converters.ModelConverter;
import com.nashss.se.eartracker.dynamodb.ListeningSessionDao;
import com.nashss.se.eartracker.dynamodb.models.ListeningSession;
import com.nashss.se.eartracker.exceptions.InvalidAttributeValueException;
import com.nashss.se.eartracker.utils.ListeningSessionAndTypeServiceUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

public class EditListeningSessionActivity {
    private final Logger log = LogManager.getLogger();
    private final ListeningSessionDao listeningSessionDao;

    @Inject
    public EditListeningSessionActivity(ListeningSessionDao listeningSessionDao) {
        //super(EditListeningSessionRequest.class);
        this.listeningSessionDao = listeningSessionDao;
    }

    public EditListeningSessionResult handleRequest(final EditListeningSessionRequest editListeningSessionRequest) {
        log.info("Recieved EditListeningSessionRequest{}", editListeningSessionRequest);

        if (!ListeningSessionAndTypeServiceUtils.isValidEmail(editListeningSessionRequest.getEmail())) {
            throw new InvalidAttributeValueException("email is unacceptable, please try again"); }

        if (!ListeningSessionAndTypeServiceUtils.isValidString(editListeningSessionRequest.getListeningType())) {
            throw new InvalidAttributeValueException("listeningType is unacceptable, please try again"); }

        TimeElapsedCalculator timeElapsedCalculator = new TimeElapsedCalculator();
        ListeningSession listeningSession = listeningSessionDao.getListeningSession(editListeningSessionRequest.getEmail(), editListeningSessionRequest.getStartSession());

        if (!listeningSession.getEmail().equals(editListeningSessionRequest.getEmail())){
            throw new SecurityException("You must own the listeningSession to update it");
        }

        if (editListeningSessionRequest.getNewStartSession() == editListeningSessionRequest.getStartSession() ||
                editListeningSessionRequest.getNewStartSession() == null) {
            listeningSession.setStartSession(editListeningSessionRequest.getStartSession());
        }
        else {
            listeningSession.setStartSession(editListeningSessionRequest.getNewStartSession());
        }

        listeningSession.setEndSession(editListeningSessionRequest.getEndSession());
        listeningSession.setListeningType(editListeningSessionRequest.getListeningType());
        listeningSession.setTimeElapsed(timeElapsedCalculator.handleRequest(listeningSession.getStartSession(), listeningSession.getEndSession()));
        listeningSession.setNotes(editListeningSessionRequest.getNotes());

        listeningSessionDao.saveListeningSession(listeningSession);

        return EditListeningSessionResult.builder()
                .withListeningSession(new ModelConverter().toListeningSessionModel(listeningSession))
                .build();
    }

}
