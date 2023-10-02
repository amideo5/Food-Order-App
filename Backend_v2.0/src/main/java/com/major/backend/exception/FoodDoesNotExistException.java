package com.major.backend.exception;

public class FoodDoesNotExistException extends Exception{

    public FoodDoesNotExistException(String foodName){
        super("Food does not exist");
    }

}
