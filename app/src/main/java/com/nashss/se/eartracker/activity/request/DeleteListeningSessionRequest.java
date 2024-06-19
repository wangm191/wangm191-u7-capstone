package com.nashss.se.eartracker.activity.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.nashss.se.eartracker.exceptions.ListeningSessionNotFoundException;

import java.time.LocalDateTime;

@JsonDeserialize(builder = DeleteListeningSessionRequest.Builder.class)
public class DeleteListeningSessionRequest {
    private final String email;
    private final LocalDateTime startSession;
    private final String listeningType;

    private DeleteListeningSessionRequest(String email, LocalDateTime startSession, String listeningType) {
        this.email = email;
        this.startSession = startSession;
        this.listeningType = listeningType;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getStartSession() { return startSession; }

    public String getListeningType() {
        return listeningType;
    }

    public boolean validRequestDelete() {
        if (startSession == null || listeningType == null) {
            throw new ListeningSessionNotFoundException("Invalid start session or listening type, cannot be found");
        } else {
            return true;
        }
    }

    @Override
    public String toString() {
        return "DeleteListeningSessionRequest{" +
                "email='" + email + '\'' +
                ", startSession='" + startSession + '\'' +
                ", listeningType=" + listeningType +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {
        private String email;
        private LocalDateTime startSession;
        private String listeningType;

        public Builder withEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder withStartSession(LocalDateTime startSession) {
            this.startSession = startSession;
            return this;
        }

        public Builder withListeningType(String listeningType) {
            this.listeningType = listeningType;
            return this;
        }

        public DeleteListeningSessionRequest build() {
            return new DeleteListeningSessionRequest(email, startSession, listeningType);
        }
    }
}
