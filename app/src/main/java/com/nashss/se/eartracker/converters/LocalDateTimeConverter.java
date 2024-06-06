package com.nashss.se.eartracker.converters;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeConverter implements DynamoDBTypeConverter<String, LocalDateTime> {
    /** Returns String verision of given date and time.
     * @param dateTime the LocalDateTime to convert
     * @return a converted String
     */
    @Override
    public String convert(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    /** Returns a LocalDateTime object in ISO-8601 format.
     * @param dateTimeString the string given in proper format (yyyy-mm-dd-h-m-s)
     * @return a LocalDateTime that matches the string
     */
    @Override
    public LocalDateTime unconvert(String dateTimeString) {
        return LocalDateTime.parse(dateTimeString, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

}
