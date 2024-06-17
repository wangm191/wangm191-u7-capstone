package com.nashss.se.eartracker.activity.request;

import java.time.LocalDateTime;

public class GetListeningSessionByDateRequest {
    private final String email;
    private final LocalDateTime startSession;

    private GetListeningSessionByDateRequest(String email, LocalDateTime startSession){
        this.email = email;
        this.startSession = startSession;
    }

    public String getEmail() {
        return email;
    }
    public LocalDateTime getStartSession() {
        return startSession;
    }

    @Override
    public String toString(){
        return "GetListeningSessionByDateRequest{" +
                "email='" + email + '\'' +
                "startSession='" + startSession + '\'' +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String email;
        private LocalDateTime startSession;

        public Builder withEmail(String email){
            this.email = email;
            return this;
        }
        public Builder withStartSession(LocalDateTime startSession){
            this.startSession = startSession;
            return this;
        }
        public GetListeningSessionByDateRequest build() { return new GetListeningSessionByDateRequest(email, startSession); }
    }
}
