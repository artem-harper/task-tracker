package com.artem.taskapi.exception;

public class EmailAlreadyExistException extends RuntimeException {

    public EmailAlreadyExistException(){
        super("This email is already taken");
    }
}
