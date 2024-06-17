package com.nashss.se.eartracker.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.nashss.se.eartracker.dynamodb.models.ListeningSession;
import com.nashss.se.eartracker.exceptions.ListeningSessionNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
@SuppressWarnings("unchecked")
public class ListeningSessionDaoTest {
    @Mock
    private DynamoDBMapper dynamoDbMapper;

    @Mock
    private PaginatedQueryList<ListeningSession> listeningSessions;

    @InjectMocks
    private ListeningSessionDao listeningSessionDao;

    @BeforeEach
    public void setup() {
        initMocks(this);
        listeningSessionDao = new ListeningSessionDao(dynamoDbMapper);
    }

    @Test
    public void searchListeningSessionByDate_withValidEmailAndStartSession_returnListeningSessions() {
        // GIVEN
        String validEmail = "validEmail@email.com";
        LocalDateTime startSession = LocalDateTime.of(2024, 6, 4, 12, 30, 30);

        LocalDateTime startSessionBegin = startSession.withHour(0).withMinute(0).withSecond(0);
        LocalDateTime startSessionEnd = startSession.withHour(23).withMinute(59).withSecond(59);

        when(dynamoDbMapper.query(eq(ListeningSession.class), any(DynamoDBQueryExpression.class))).thenReturn(listeningSessions);
        ArgumentCaptor<DynamoDBQueryExpression<ListeningSession>> captor = ArgumentCaptor.forClass(DynamoDBQueryExpression.class);

        // WHEN
        List<ListeningSession> result = listeningSessionDao.searchListeningSessionByDate(validEmail, startSessionBegin, startSessionEnd);

        // THEN
        assertEquals(listeningSessions, result, "Expected list of listeningSessions to be equal to what dynamoDb returned");
        verify(dynamoDbMapper).query(eq(ListeningSession.class), captor.capture());
    }

    @Test
    public void searchListeningSessionByDate_attributesAreNull_throwsListeningSessionNotFoundException(){
        // WHEN + THEN
        assertThrows(ListeningSessionNotFoundException.class, () -> listeningSessionDao.searchListeningSessionByDate(null, null, null));
    }

    @Test
    public void getListeningSession_withValidEmailAndStartSession_callsMapperWithPartitionKey() {
        // GIVEN
        String email = "validEmail@email.com";
        LocalDateTime startSession = LocalDateTime.of(2024, 6, 4, 12, 30, 30);
        when(dynamoDbMapper.load(ListeningSession.class, email, startSession)).thenReturn(new ListeningSession());

        // WHEN
        ListeningSession listeningSession = listeningSessionDao.getListeningSession(email, startSession);

        // THEN
        assertNotNull(listeningSession);
        verify(dynamoDbMapper).load(ListeningSession.class, email, startSession);
    }

    @Test
    public void getListeningSession_emailNotFound_throwsListeningSessionNotFoundException() {
        // GIVEN
        String fakeEmail = "NotReal";
        LocalDateTime startSession = LocalDateTime.of(2024, 6, 4, 12, 30, 30);

        when(dynamoDbMapper.load(ListeningSession.class, fakeEmail, startSession)).thenReturn(null);

        // WHEN + THEN
        assertThrows(ListeningSessionNotFoundException.class, () -> listeningSessionDao.getListeningSession(fakeEmail, startSession));
    }

    @Test
    public void saveTracker_callsMapperWithListeningSession() {
        // GIVEN
        ListeningSession listeningSession = new ListeningSession();

        // WHEN
        ListeningSession result = listeningSessionDao.saveListeningSession(listeningSession);

        // THEN
        verify(dynamoDbMapper).save(listeningSession);
        assertEquals(listeningSession, result);
    }

    @Test
    public void searchListeningSessionByListeningType_withValidEmailAndListeningType_returnsCorrectListeningSessionList() {
        // GIVEN
        String validEmail = "validEmail@email.com";
        String listeningType = "Spotify";

        ListeningSession listeningSession = new ListeningSession();
        listeningSession.setEmail(validEmail);
        listeningSession.setListeningType(listeningType);

        listeningSessions.add(listeningSession);

        when(dynamoDbMapper.query(eq(ListeningSession.class), any(DynamoDBQueryExpression.class))).thenReturn(listeningSessions);
        ArgumentCaptor<DynamoDBQueryExpression<ListeningSession>> captor = ArgumentCaptor.forClass(DynamoDBQueryExpression.class);

        // WHEN
        List<ListeningSession> result = listeningSessionDao.searchListeningSessionByListeningType(validEmail, listeningType);

        // THEN
        assertEquals(listeningSessions, result, "Expected list of tracker jobs to be equal to what dynamoDb returned");
        verify(dynamoDbMapper).query(eq(ListeningSession.class), captor.capture());
    }

    @Test
    public void searchListeningSessionByListeningType_nullAttributes_ThrowsInvalidAttributeException(){
        // GIVEN
        when(dynamoDbMapper.query(eq(ListeningSession.class), any(DynamoDBQueryExpression.class))).thenReturn(listeningSessions);

        // WHEN + THEN
        assertThrows(ListeningSessionNotFoundException.class, () -> listeningSessionDao.searchListeningSessionByListeningType(null, null),
                "Expected to throw Invalid Attribute Value Exception");
    }
}
