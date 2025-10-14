package com.artem.taskapi.exception;

public class EmailAlreadyExistException extends RuntimeException {

    public EmailAlreadyExistException(){
        super("Email already exist");
    }
}
