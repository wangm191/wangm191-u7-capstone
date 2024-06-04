package com.nashss.se.eartracker.activity.result;

import com.nashss.se.eartracker.models.ListeningSessionModel;

public class AddListeningSessionResult {
    private final ListeningSessionModel listeningSessionModel;

    private AddListeningSessionResult(ListeningSessionModel listeningSessionModel) {
        this.listeningSessionModel = listeningSessionModel;
    }

    private ListeningSessionModel getListeningSessionModel() {
        return listeningSessionModel;
    }

    @Override
    public String toString() {
        return "AddListeningSessionResult{" +
                "listeningSessionModel=" + listeningSessionModel +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private ListeningSessionModel listeningSessionModel;

        public Builder withListeningSessionModel(ListeningSessionModel listeningSessionModel) {
            this.listeningSessionModel = listeningSessionModel;
            return this;
        }

        public AddListeningSessionResult build() {
            return new AddListeningSessionResult(listeningSessionModel);
        }

    }
}
