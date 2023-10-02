package com.major.backend.response;

import java.util.HashMap;
import java.util.Map;

public class DispatchItenary {
    private Map<String, Integer> orderItems = new HashMap<>();

    public DispatchItenary() {
    }
    public DispatchItenary(Map<String, Integer> dispatchMap) {
        this.orderItems = dispatchMap;
    }

    public Map<String, Integer> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Map<String, Integer> orderItems) {
        this.orderItems = orderItems;
    }
}
