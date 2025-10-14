package com.artem.taskapi.controllers.handler;

import com.artem.taskapi.exception.EmailAlreadyExistException;
import com.artem.taskapi.exception.UserNotExistException;
import com.artem.taskapi.util.ApiErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailAlreadyExistException.class)
    public ResponseEntity<ApiErrorMessage> handleEmailAlreadyExistException(EmailAlreadyExistException ex){
        log.warn("Error message: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiErrorMessage(ex.getMessage()));
    }

    @ExceptionHandler(UserNotExistException.class)
    public ResponseEntity<ApiErrorMessage> handleUserNotExistException(UserNotExistException ex){
        log.warn("Error message: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiErrorMessage(ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorMessage> handleUserNotExistException(Exception ex){
        log.warn("Error message: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiErrorMessage(ex.getMessage()));
    }
}
