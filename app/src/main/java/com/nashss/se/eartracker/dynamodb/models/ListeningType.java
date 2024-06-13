package com.nashss.se.eartracker.dynamodb.models;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.Objects;

@DynamoDBTable(tableName = "listeningTypes")
public class ListeningType {
    private String email;
    private String listeningType;
    private String notes;


    @DynamoDBHashKey(attributeName = "email")
    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    @DynamoDBRangeKey(attributeName = "listeningType")
    public String getListeningType() { return listeningType; }

    public void setListeningType(String listeningType){ this.listeningType = listeningType; }

    @DynamoDBAttribute(attributeName = "notes")
    public String getNotes() { return notes; }

    public void setNotes(String notes) { this.notes = notes; }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        ListeningSession listeningSession = (ListeningSession) object;
        return email.equals(listeningSession.getEmail()) &&
                listeningType.equals(listeningSession.getListeningType()) &&
                notes.equals(listeningSession.getNotes());
    }

    @Override
    public int hashCode() { return Objects.hash(email, listeningType, notes); }
}
