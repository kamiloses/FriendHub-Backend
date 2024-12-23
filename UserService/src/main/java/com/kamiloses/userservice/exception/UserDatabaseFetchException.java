package com.kamiloses.userservice.exception;

public class UserDatabaseFetchException extends RuntimeException {


    public UserDatabaseFetchException(String message) {
        super("There was some problem with saving/fetching user from database");
    }
}
