package com.kamiloses.messageservice.exception;

public class MessageDatabaseFetchException extends RuntimeException {


    public MessageDatabaseFetchException() {
        super("There was some problem with fetching/saving messages from the database");
    }
}
