package com.joy.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;

/**
 * Centralised point for exception.
 * Code referenced via: https://medium.com/@jovannypcg/understanding-springs-controlleradvice-cd96a364033f
 */

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({UserNotFoundException.class, EmailNotUniqueException.class, ConstraintViolationException.class})
    public final ResponseEntity<ApiError> handleException(Exception ex){


        if (ex instanceof UserNotFoundException) {
            UserNotFoundException notFounded = (UserNotFoundException) ex;
            return handleUserNotFoundException(notFounded, HttpStatus.NOT_FOUND);
        } else if(ex instanceof ConstraintViolationException){
            ConstraintViolationException violation = (ConstraintViolationException) ex;
            return handleExceptionalInternal(violation, HttpStatus.BAD_REQUEST);
        }
        else if (ex instanceof EmailNotUniqueException){
            EmailNotUniqueException notUnique = (EmailNotUniqueException) ex;
            return handleEmailNotUniqueException(notUnique, HttpStatus.BAD_REQUEST);
        } else {
            return handleExceptionalInternal(ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    protected ResponseEntity<ApiError> handleEmailNotUniqueException(EmailNotUniqueException ex, HttpStatus status){
        return handleExceptionalInternal(ex, status);
    }


    protected ResponseEntity<ApiError> handleUserNotFoundException(UserNotFoundException ex, HttpStatus status){
        return handleExceptionalInternal(ex, status);
    }

    protected ResponseEntity<ApiError> handleExceptionalInternal(Exception ex, HttpStatus status){
        ApiError error = new ApiError(status.toString(), ex.getMessage());
        return new ResponseEntity<>(error, status);
    }
}
