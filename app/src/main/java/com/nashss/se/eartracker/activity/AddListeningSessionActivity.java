package com.nashss.se.eartracker.activity;

import com.nashss.se.eartracker.activity.request.AddListeningSessionRequest;
import com.nashss.se.eartracker.activity.result.AddListeningSessionResult;
import com.nashss.se.eartracker.calculator.TimeElapsedCalculator;
import com.nashss.se.eartracker.converters.ModelConverter;
import com.nashss.se.eartracker.dynamodb.ListeningSessionDao;
import com.nashss.se.eartracker.dynamodb.models.ListeningSession;
import com.nashss.se.eartracker.exceptions.InvalidAttributeValueException;
import com.nashss.se.eartracker.models.ListeningSessionModel;
import com.nashss.se.eartracker.utils.ListeningSessionAndTypeServiceUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;


public class AddListeningSessionActivity {

    private final Logger log = LogManager.getLogger();
    private final ListeningSessionDao listeningSessionDao;

    @Inject
    public AddListeningSessionActivity(ListeningSessionDao listeningSessionDao) {
        this.listeningSessionDao = listeningSessionDao;
    }

    public AddListeningSessionResult handleRequest(final AddListeningSessionRequest addListeningSessionRequest){
    log.info("Recieved AddListeningSessionRequest {}", addListeningSessionRequest);

    if (!ListeningSessionAndTypeServiceUtils.isValidEmail(addListeningSessionRequest.getEmail())) {
        throw new InvalidAttributeValueException("email is unacceptable, please try again"); }

    if (!ListeningSessionAndTypeServiceUtils.isValidString(addListeningSessionRequest.getListeningType())) {
        throw new InvalidAttributeValueException("listeningType is unacceptable, please try again"); }

    String validNotes = "";
    if (addListeningSessionRequest.getNotes() != null) {
        validNotes = addListeningSessionRequest.getNotes();
    }

    TimeElapsedCalculator timeElapsedCalculator = new TimeElapsedCalculator();
    ListeningSession listeningSession = new ListeningSession();

    listeningSession.setEmail(addListeningSessionRequest.getEmail());
    listeningSession.setStartSession(addListeningSessionRequest.getStartSession());
    listeningSession.setEndSession(addListeningSessionRequest.getEndSession());
    listeningSession.setListeningType(addListeningSessionRequest.getListeningType());
    listeningSession.setTimeElapsed(timeElapsedCalculator.handleRequest(addListeningSessionRequest.getStartSession(), addListeningSessionRequest.getEndSession()));
    listeningSession.setNotes(validNotes);

    listeningSessionDao.saveListeningSession(listeningSession);

    ListeningSessionModel listeningSessionModel = new ModelConverter().toListeningSessionModel(listeningSession);
    return AddListeningSessionResult.builder()
            .withListeningSessionModel(listeningSessionModel)
            .build();
    }
}
