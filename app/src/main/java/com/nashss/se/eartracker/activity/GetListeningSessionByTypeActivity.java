package com.nashss.se.eartracker.activity;

import com.nashss.se.eartracker.activity.request.GetListeningSessionByTypeRequest;
import com.nashss.se.eartracker.activity.result.GetListeningSessionByTypeResult;
import com.nashss.se.eartracker.converters.ModelConverter;
import com.nashss.se.eartracker.dynamodb.ListeningSessionDao;
import com.nashss.se.eartracker.dynamodb.models.ListeningSession;
import com.nashss.se.eartracker.models.ListeningSessionModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.List;

public class GetListeningSessionByTypeActivity {
    private final Logger log = LogManager.getLogger();
    private final ListeningSessionDao listeningSessionDao;

    @Inject
    public GetListeningSessionByTypeActivity(ListeningSessionDao listeningSessionDao) {
        this.listeningSessionDao = listeningSessionDao;
    }

    public GetListeningSessionByTypeResult handleRequest(final GetListeningSessionByTypeRequest getListeningSessionByTypeRequest) {
        log.info("Received GetListeningSessionByDateRequest {}", getListeningSessionByTypeRequest);

        List<ListeningSession> listeningSessions = listeningSessionDao.searchListeningSessionByListeningType(getListeningSessionByTypeRequest.getEmail(), getListeningSessionByTypeRequest.getListeningType());

        List<ListeningSessionModel> listeningSessionModels = new ModelConverter().toListeningSessionModelList(listeningSessions);
        return GetListeningSessionByTypeResult.builder()
                .withListeningSession(listeningSessionModels)
                .build();
    }

}
