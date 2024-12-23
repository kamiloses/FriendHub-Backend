package com.kamiloses.friendservice.exception;

public class FriendsDatabaseFetchException extends RuntimeException {

    public FriendsDatabaseFetchException() {
        super("There was some problem with saving/fetching friend from database");
    }
}
