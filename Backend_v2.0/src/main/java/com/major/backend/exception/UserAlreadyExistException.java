package com.major.backend.exception;

public class UserAlreadyExistException extends Exception{
    public UserAlreadyExistException(String email){
        super("User with Email ID: " + email + " already exist");
    }
}
