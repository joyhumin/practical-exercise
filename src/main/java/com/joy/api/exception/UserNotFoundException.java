package com.joy.api.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String email){
        super("Cannot find the user with email " + email);
    }
}
