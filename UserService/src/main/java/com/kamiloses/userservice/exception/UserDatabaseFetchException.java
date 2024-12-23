package com.kamiloses.userservice.exception;

public class UserDatabaseFetchException extends RuntimeException {


    public UserDatabaseFetchException() {
        super("There was some problem with saving/fetching user from database");
    }
}
