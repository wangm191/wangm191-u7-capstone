package com.nashss.se.eartracker.activity.result;

import com.nashss.se.eartracker.models.ListeningSessionModel;

public class DeleteListeningSessionResult {

    private final ListeningSessionModel listeningSession;

    public DeleteListeningSessionResult(ListeningSessionModel listeningSession) {
        this.listeningSession = listeningSession;
    }

    public ListeningSessionModel getListeningSession(){
        return listeningSession;
    }

    @Override
    public String toString() {
        return "The listeningSession: " + listeningSession;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private ListeningSessionModel listeningSessionModel;

        public Builder withListeningSession(ListeningSessionModel listeningSessionModel) {
            this.listeningSessionModel = listeningSessionModel;
            return this;
        }

        public DeleteListeningSessionResult build() {
            return new DeleteListeningSessionResult(listeningSessionModel);
        }
    }
}
