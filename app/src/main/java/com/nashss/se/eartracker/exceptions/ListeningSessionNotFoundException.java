package com.nashss.se.eartracker.exceptions;

/**
 * Exception to throw when a given playlist ID is not found in the database.
 */
public class ListeningSessionNotFoundException extends RuntimeException {


    /**
     * Exception with no message or cause.
     */
    public ListeningSessionNotFoundException() {
        super();
    }

    /**
     * Exception with a message, but no cause.
     * @param message A descriptive message for this exception.
     */
    public ListeningSessionNotFoundException(String message) {
        super(message);
    }

    /**
     * Exception with no message, but with a cause.
     * @param cause The original throwable resulting in this exception.
     */
    public ListeningSessionNotFoundException(Throwable cause) {
        super(cause);
    }

    /**
     * Exception with message and cause.
     * @param message A descriptive message for this exception.
     * @param cause The original throwable resulting in this exception.
     */
    public ListeningSessionNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

