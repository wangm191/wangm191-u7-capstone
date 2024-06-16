package com.nashss.se.eartracker.activity;

import com.nashss.se.eartracker.activity.request.GetListeningSessionByDateRequest;
import com.nashss.se.eartracker.activity.result.GetListeningSessionByDateResult;
import com.nashss.se.eartracker.converters.ModelConverter;
import com.nashss.se.eartracker.dynamodb.ListeningSessionDao;
import com.nashss.se.eartracker.dynamodb.models.ListeningSession;
import com.nashss.se.eartracker.models.ListeningSessionModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.List;

public class GetListeningSessionByDateActivity {
    private final Logger log = LogManager.getLogger();
    private final ListeningSessionDao listeningSessionDao;

    @Inject
    public GetListeningSessionByDateActivity(ListeningSessionDao listeningSessionDao) {
        this.listeningSessionDao = listeningSessionDao;
    }

    public GetListeningSessionByDateResult handleRequest(final GetListeningSessionByDateRequest getListeningSessionByDateRequest) {
        log.info("Received GetListeningSessionByDateRequest {}", getListeningSessionByDateRequest);

        List<ListeningSession> listeningSessions = listeningSessionDao.searchListeningSessionByDate
                (getListeningSessionByDateRequest.getEmail(), getListeningSessionByDateRequest.getStartSession());

        List<ListeningSessionModel> listeningSessionModels = new ModelConverter().toListeningSessionModelList(listeningSessions);
        return GetListeningSessionByDateResult.builder()
                .withListeningSession(listeningSessionModels)
                .build();
    }

}
