package com.artem.taskapi.exception;

public class UserNotExistException extends RuntimeException{

    public UserNotExistException(){
        super("User not exist");
    }
}
