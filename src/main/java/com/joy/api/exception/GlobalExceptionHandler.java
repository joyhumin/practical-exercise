package com.joy.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Centralised point for exception.
 * Code referenced via: https://medium.com/@jovannypcg/understanding-springs-controlleradvice-cd96a364033f
 */

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({UserNotFoundException.class})
    public final ResponseEntity<ApiError> handleException(Exception ex){

        if (ex instanceof UserNotFoundException) {
            UserNotFoundException notFounded = (UserNotFoundException) ex;
            return handleUserNotFoundException(notFounded, HttpStatus.NOT_FOUND);
        } else {
//            TODO: fine-tune the error handling for different errors
            return handleExceptionalInternal(ex, HttpStatus.BAD_REQUEST);
        }
    }


    protected ResponseEntity<ApiError> handleUserNotFoundException(UserNotFoundException ex, HttpStatus status){
        return handleExceptionalInternal(ex, status);
    }

    protected ResponseEntity<ApiError> handleExceptionalInternal(Exception ex, HttpStatus status){
        ApiError error = new ApiError(status.toString(), ex.getMessage());
        return new ResponseEntity<>(error, status);
    }
}
