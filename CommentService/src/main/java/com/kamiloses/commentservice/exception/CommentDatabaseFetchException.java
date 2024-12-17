package com.kamiloses.commentservice.exception;

public class CommentDatabaseFetchException extends RuntimeException {
    public CommentDatabaseFetchException(String message) {
        super(message);
    }
}
