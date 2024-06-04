package com.nashss.se.eartracker.models;

import com.nashss.se.eartracker.dynamodb.ListeningSessionDao;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class ListeningSessionModel {
    private final String email;
    private final LocalDateTime startSession;
    private final LocalDateTime endSession;
    private final String listeningType;
    private final String timeElapsed; // maybe not double? need to return endSession - startSession
    private final String notes;

    private ListeningSessionModel(String email, LocalDateTime startSession, LocalDateTime endSession,
                                  String listeningType, String timeElapsed, String notes){
        this.email = email;
        this.startSession = startSession;
        this.endSession = endSession;
        this.listeningType = listeningType;
        this.timeElapsed = timeElapsed;
        this.notes = notes;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getStartSession() {
        return startSession;
    }
    public LocalDateTime getEndSession() {
        return endSession;
    }
    public String getListeningType() {
        return listeningType;
    }
    public String getTimeElapsed() {
        return timeElapsed;
    }
    public String getNotes() {
        return notes;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        ListeningSessionModel that = (ListeningSessionModel) object;
        return Objects.equals(getEmail(), that.getEmail()) &&
                Objects.equals(getStartSession(), that.getStartSession()) &&
                Objects.equals(getEndSession(), that.getEndSession()) &&
                Objects.equals(getListeningType(), that.getListeningType()) &&
                Objects.equals(getTimeElapsed(), that.getTimeElapsed()) &&
                Objects.equals(getNotes(), that.getNotes());
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, startSession, endSession, listeningType, endSession, notes);
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String email;
        private LocalDateTime startSession;
        private LocalDateTime endSession;
        private String listeningType;
        private String timeElapsed;
        private String notes;

        public Builder withEmail(String email) {
            this.email = email;
            return this;
        }
        public Builder withStartSession(LocalDateTime startSession) {
            this.startSession = startSession;
            return this;
        }
        public Builder withEndSession(LocalDateTime endSession) {
            this.endSession = endSession;
            return this;
        }
        public Builder withListeningType(String listeningType) {
            this.listeningType = listeningType;
            return this;
        }
        public Builder withTimeElapsed(String timeElapsed) {
            this.timeElapsed = timeElapsed;
            return this;
        }
        public Builder withNotes(String notes) {
            this.notes = notes;
            return this;
        }

        public ListeningSessionModel build() { return new ListeningSessionModel(email, startSession, endSession, listeningType, timeElapsed, notes);}
    }
}
