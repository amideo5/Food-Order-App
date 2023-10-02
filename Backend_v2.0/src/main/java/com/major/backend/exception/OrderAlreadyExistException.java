package com.major.backend.exception;

public class OrderAlreadyExistException extends Exception {

    public OrderAlreadyExistException(String orderName){
        super("Order with Name: " + orderName + " already exist");
    }
}
