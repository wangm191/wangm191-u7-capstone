package com.nashss.se.eartracker.activity.request;

import java.time.LocalDateTime;

public class GetListeningSessionByTypeRequest {
    private final String email;
    private final String listeningType;

    private GetListeningSessionByTypeRequest(String email, String listeningType){
        this.email = email;
        this.listeningType = listeningType;
    }

    public String getEmail() {
        return email;
    }
    public String getListeningType() {
        return listeningType;
    }

    @Override
    public String toString(){
        return "GetListeningSessionByDateRequest{" +
                "email='" + email + '\'' +
                "listeningType='" + listeningType + '\'' +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String email;
        private String listeningType;

        public Builder withEmail(String email){
            this.email = email;
            return this;
        }
        public Builder withListeningType(String listeningType){
            this.listeningType = listeningType;
            return this;
        }
        public GetListeningSessionByTypeRequest build() { return new GetListeningSessionByTypeRequest(email, listeningType); }
    }
}
