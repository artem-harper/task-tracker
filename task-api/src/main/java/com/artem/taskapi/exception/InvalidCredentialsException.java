package com.artem.taskapi.exception;

public class InvalidCredentialsException extends RuntimeException{

    public InvalidCredentialsException(){
        super("Invalid Username or password");
    }
}
