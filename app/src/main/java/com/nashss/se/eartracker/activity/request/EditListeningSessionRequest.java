package com.nashss.se.eartracker.activity.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.time.LocalDateTime;

@JsonDeserialize(builder = EditListeningSessionRequest.Builder.class)
public class EditListeningSessionRequest {
    private final String email;
    private final LocalDateTime startSession;
    private final LocalDateTime newStartSession;
    private final LocalDateTime endSession;
    private final String listeningType;
    private final String notes;

    private EditListeningSessionRequest(String email, LocalDateTime newStartSession, LocalDateTime startSession, LocalDateTime endSession, String listeningType, String notes){
        this.email = email;
        this.startSession = startSession;
        this.newStartSession = newStartSession;
        this.endSession = endSession;
        this.listeningType = listeningType;
        this.notes = notes;
    }

    public String getEmail(){ return email; }
    public LocalDateTime getStartSession(){ return startSession; }
    public LocalDateTime getNewStartSession() { return newStartSession; }
    public LocalDateTime getEndSession(){ return endSession; }
    public String getListeningType(){ return listeningType; }
    public String getNotes(){ return notes; }

    @Override
    public String toString(){
        return "EditListeningSessionRequest{" +
                "email'=" + email + '\'' +
                "startSession='" + startSession + '\'' +
                "newStartSession='" + newStartSession + '\''+
                ", endSession='" + endSession + '\'' +
                ", listeningType='" + listeningType + '\'' +
                ", notes='" + notes + '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {
        private String email;
        private LocalDateTime startSession;
        private LocalDateTime newStartSession;
        private LocalDateTime endSession;
        private String listeningType;
        private String notes;

        public Builder withEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder withStartSession(LocalDateTime startSession) {
            this.startSession = startSession;
            return this;
        }
        public Builder withNewStartSession(LocalDateTime newStartSession) {
            this.newStartSession = newStartSession;
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
        public Builder withNotes(String notes) {
            this.notes = notes;
            return this;
        }

        public EditListeningSessionRequest build() {
            return new EditListeningSessionRequest(email, startSession, newStartSession, endSession, listeningType, notes);
        }
    }

}
