package com.major.backend.exception;

public class OrderDoesNotExistException extends Exception{

    public OrderDoesNotExistException(String orderName){
        super("Order does not exist");
    }
}
