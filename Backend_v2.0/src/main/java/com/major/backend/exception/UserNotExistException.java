package com.major.backend.exception;

public class UserNotExistException extends Exception{

    public UserNotExistException(String email){
        super("User does not exist");
    }
}
