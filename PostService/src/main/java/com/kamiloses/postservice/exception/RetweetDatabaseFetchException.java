package com.kamiloses.postservice.exception;

public class RetweetDatabaseFetchException extends RuntimeException{

    public RetweetDatabaseFetchException() {
        super("There was some problem with saving/fetching retweet from database");
    }
}
