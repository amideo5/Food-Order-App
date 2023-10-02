package com.major.backend.exception;

public class FoodAlreadyExistException extends Exception{

    public FoodAlreadyExistException(String foodName){ super("Food with Name: " + foodName + " already exist"); }
}
