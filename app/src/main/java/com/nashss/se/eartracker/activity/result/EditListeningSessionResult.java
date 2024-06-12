package com.nashss.se.eartracker.activity.result;

import com.nashss.se.eartracker.models.ListeningSessionModel;

public class EditListeningSessionResult {
    private final ListeningSessionModel listeningSession;

    private EditListeningSessionResult (ListeningSessionModel listeningSession) {
        this.listeningSession = listeningSession;
    }

    public ListeningSessionModel getListeningSession() {
        return listeningSession;
    }

    @Override
    public String toString() {
        return "EditListeningSessionResult{" +
                "listeningSession=" + listeningSession +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private ListeningSessionModel listeningSession;

        public Builder withListeningSession(ListeningSessionModel listeningSession) {
            this.listeningSession = listeningSession;
            return this;
        }

        public EditListeningSessionResult build() {
            return new EditListeningSessionResult(listeningSession);
        }
    }
}
