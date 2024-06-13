package com.nashss.se.eartracker.models;

import java.util.Objects;

public class ListeningTypeModel {
    private final String email;
    private final String listeningType;
    private final String notes;

    private ListeningTypeModel(String email, String listeningType, String notes) {
        this.email = email;
        this.listeningType = listeningType;
        this.notes = notes;
    }

    public String getEmail() { return  email; }
    public String getListeningType() { return listeningType; }
    public String getNotes() { return notes; }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        ListeningTypeModel that = (ListeningTypeModel) object;
        return Objects.equals(getEmail(), that.getEmail()) &&
                Objects.equals(getListeningType(), that.getListeningType()) &&
                Objects.equals(getNotes(), that.getNotes());
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, listeningType, notes);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String email;
        private String listeningType;
        private String notes;

        public Builder withEmail(String email) {
            this.email = email;
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

        public ListeningTypeModel build() { return new ListeningTypeModel(email, listeningType, notes);}
    }
}
