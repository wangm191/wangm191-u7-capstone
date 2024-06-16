package com.nashss.se.eartracker.activity.result;

import com.nashss.se.eartracker.dynamodb.models.ListeningSession;
import com.nashss.se.eartracker.models.ListeningSessionModel;

import java.util.List;

public class GetListeningSessionByDateResult {
    private final List<ListeningSessionModel> listeningSessions;

    private GetListeningSessionByDateResult(List<ListeningSessionModel> listeningSessions) {
        this.listeningSessions = listeningSessions;
    }

    public List<ListeningSessionModel> getListeningSessions(){
        return listeningSessions;
    }

    @Override
    public String toString(){
        return "GetListeningSessionByDateResult{" +
                "listeningSession='" + listeningSessions +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder{
        private List<ListeningSessionModel> listeningSessions;

        public Builder withListeningSession(List<ListeningSessionModel> listeningSessions){
            this.listeningSessions = listeningSessions;
            return this;
        }

        public GetListeningSessionByDateResult build() { return new GetListeningSessionByDateResult(listeningSessions);}
    }
}
