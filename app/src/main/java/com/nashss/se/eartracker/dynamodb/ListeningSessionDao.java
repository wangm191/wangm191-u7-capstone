package com.nashss.se.eartracker.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.nashss.se.eartracker.dynamodb.models.ListeningSession;
import com.nashss.se.eartracker.exceptions.ListeningSessionNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.nashss.se.eartracker.dynamodb.models.ListeningSession.EMAIL_LISTENING_TYPE_INDEX;

/**
 * Accesses data for the listeningSession using {@link ListeningSession} to represent the model in DynamoDB
 */
public class ListeningSessionDao {
    private final Logger log = LogManager.getLogger();
    private final DynamoDBMapper dynamoDbMapper;

    /**
     * Instantiates a TargetDao Object
     *
     * @param dynamoDbMapper the {@link DynamoDBMapper} used to interact with the target table
     */
    @Inject
    public ListeningSessionDao(DynamoDBMapper dynamoDbMapper){
        this.dynamoDbMapper = dynamoDbMapper;
    }

    /**
     *  Performs a search (via a "query") of the listeningSessions table for the ListeningSession that match the given criteria
     *
     *  The criteria are the matching users email and date time of the startSession
     *  Both "email" and "listeningType" has keys are searched.
     *       "email" is the hash key of the table
     *       "startSession" is a hash key of the GSI

     * @param email the String email of user
     * @param startSession the LocalDateTime of the specific listening session
     * @return a list of {@link ListeningSession} matching the specified email and startSession date time.
     */
    public List<ListeningSession> searchListeningSessionByDate(String email, LocalDateTime startSession) {
        if (email == null){
            throw new ListeningSessionNotFoundException("error: Missing email, cannot find Listening Session. ");
        }
        if (startSession == null){
            throw new ListeningSessionNotFoundException("error: Missing startSession date and time, cannot find Listening Session. ");
        }

        Map<String, AttributeValue> valueMap = new HashMap<>();
        valueMap.put(":email", new AttributeValue(email));
        valueMap.put(":startSession", new AttributeValue(String.valueOf(startSession)));


        DynamoDBQueryExpression<ListeningSession> queryExpression = new DynamoDBQueryExpression<ListeningSession>()
                .withKeyConditionExpression("email = :email AND startSession = :startSession")
                .withExpressionAttributeValues(valueMap);

        return dynamoDbMapper.query(ListeningSession.class, queryExpression);
    }

    /** Retrieves the given listeningSession.
     *
     * @param email The partition key of the ListeningSession object.
     * @param startSession The GSI range key of the ListeningSession object.
     * @return The ListeningSession object that was retrieved
     */
    public ListeningSession getListeningSession(String email, LocalDateTime startSession) {
        ListeningSession listeningSession = this.dynamoDbMapper.load(ListeningSession.class, email, startSession);

        if (listeningSession == null) {
            throw new ListeningSessionNotFoundException("No such listening session was found, please try again");
        }
        return listeningSession;
    }

    /**
     * Saves (creates or updates) the given playlist.
     *
     * @param listeningSession The ListeningSession object to save
     * @return The ListeningSession object that was saved
     */
    public ListeningSession saveListeningSession(ListeningSession listeningSession){
        this.dynamoDbMapper.save(listeningSession);
        return listeningSession;
    }

    /**
     * Deletes the given playlist.
     *
     * @param listeningSession The ListeningSession object to delete
     * @return The ListeningSession object that was deleted
     */
    public ListeningSession deleteListeningSession(ListeningSession listeningSession) {
        this.dynamoDbMapper.delete(listeningSession);
        return listeningSession;
    }

    /**
     * Perform a search (via a "query") of the listeningSessions table for the ListeningSession that match the given criteria
     *
     * The criteria are the matching user email and listeningType String
     * Both "email" and "listeningType" has keys are searched.
     *      "email" is the hash key of the table
     *      "listeningType" is a hash key of the GSI
     *
     * @param email is the String email of user that needs to be queried.
     * @param listeningType is the String listeningType that needs to be queried.
     * @return a List of {@Link ListeningSessions} that match the search after queried .
     */
    public List<ListeningSession> searchListeningSessionByListeningType(String email, String listeningType) {
        if (email == null){
            throw new ListeningSessionNotFoundException("error: Missing email, cannot find Listening Session. ");
        }
        if (listeningType == null){
            throw new ListeningSessionNotFoundException("error: Missing listeningType String, cannot find Listening Session. ");
        }

        Map<String, AttributeValue> valueMap = new HashMap<>();
        valueMap.put("email", new AttributeValue(email));
        valueMap.put(":listeningType", new AttributeValue(listeningType));


        DynamoDBQueryExpression<ListeningSession> queryExpression = new DynamoDBQueryExpression<ListeningSession>()
                .withIndexName(EMAIL_LISTENING_TYPE_INDEX)
                .withConsistentRead(false)
                .withKeyConditionExpression("email = :email AND listeningType = :listeningType")
                .withExpressionAttributeValues(valueMap);

        return dynamoDbMapper.query(ListeningSession.class, queryExpression);
    }

}
