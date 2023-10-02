package com.major.backend.request;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Cart {

    private Map<String, Integer> orderList = new HashMap<>();


    public Cart() {
    }

    public Cart(Map<String, Integer> itemList) {
        this.orderList = itemList;

    }
    public Map<String, Integer> getOrderList() {
        return orderList;
    }

    public void setOrderList(Map<String, Integer> orderList) {
        this.orderList = orderList;
    }


    @Override
    public String toString() {
        return "Cart{" +
                ", orderList=" + orderList +
                '}';
    }
}