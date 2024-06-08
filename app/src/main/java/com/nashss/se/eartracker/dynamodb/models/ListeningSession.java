package com.nashss.se.eartracker.dynamodb.models;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.nashss.se.eartracker.converters.LocalDateTimeConverter;

import java.time.LocalDateTime;
import java.util.Objects;

@DynamoDBTable(tableName =  "listeningSessions")
public class ListeningSession {
    private String email;
    private LocalDateTime startSession;
    private LocalDateTime endSession;
    private String listeningType;
    private String timeElapsed;
    private String notes;

    public static final String EMAIL_START_SESSION_INDEX = "EmailAndStartSessionIndex";
    public static final String EMAIL_LISTENING_TYPE_INDEX = "EmailAndListeningTypeIndex";


    @DynamoDBHashKey(attributeName = "email")
    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email;}

    @DynamoDBIndexHashKey(globalSecondaryIndexName = EMAIL_START_SESSION_INDEX, attributeName = "startSession")
    @DynamoDBTypeConverted(converter = LocalDateTimeConverter.class)
    public LocalDateTime getStartSession() { return startSession; }

    public void setStartSession(LocalDateTime startSession) { this.startSession = startSession;}

    @DynamoDBAttribute(attributeName = "endSession")
    @DynamoDBTypeConverted(converter = LocalDateTimeConverter.class)
    public LocalDateTime getEndSession() { return endSession; }

    public void setEndSession(LocalDateTime endSession) { this.endSession = endSession; }

    @DynamoDBIndexHashKey(globalSecondaryIndexName = EMAIL_LISTENING_TYPE_INDEX, attributeName = "listeningType")
    public String getListeningType() { return listeningType; }

    public void setListeningType(String listeningType) { this.listeningType = listeningType; }

    @DynamoDBAttribute(attributeName = "timeElapsed")
    public String getTimeElapsed() { return timeElapsed; }

    public void setTimeElapsed(String timeElapsed) { this.timeElapsed = timeElapsed;}

    @DynamoDBAttribute(attributeName = "notes")
    public String getNotes() { return notes; }

    public void setNotes(String notes) { this.notes = notes; }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        ListeningSession listeningSession = (ListeningSession) object;
        return email.equals(listeningSession.email) &&
                startSession.equals(listeningSession.startSession) &&
                endSession.equals(listeningSession.endSession) &&
                listeningType.equals(listeningSession.listeningType) &&
                timeElapsed.equals(listeningSession.timeElapsed) &&
                notes.equals(listeningSession.notes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, startSession, endSession, listeningType, timeElapsed, notes);
    }

}
