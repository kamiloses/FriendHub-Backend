package com.kamiloses.commentservice.exception;

public class CommentDatabaseFetchException extends RuntimeException {
    public CommentDatabaseFetchException() {
        super("There was some problem with saving/fetching comments from database");
    }
}
