package com.artem.taskapi.exception;

public class TaskNotFoundException extends RuntimeException {

    public TaskNotFoundException(){
        super("Task not found");
    }
}
