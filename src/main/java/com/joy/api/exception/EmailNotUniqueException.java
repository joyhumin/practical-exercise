package com.joy.api.exception;

public class EmailNotUniqueException extends RuntimeException{
    public EmailNotUniqueException(String email) {
        super("This email address " + email + " exist, please use another one");
    }
}
