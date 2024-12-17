package com.kamiloses.postservice.exception;

public class PostDatabaseFetchException extends RuntimeException {

    public PostDatabaseFetchException() {
        super("There was some problem with saving/fetching posts from database");
    }
}
