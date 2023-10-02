package com.major.backend.services;


import com.major.backend.request.Cart;
import com.major.backend.exception.OrderAlreadyExistException;
import com.major.backend.exception.OrderDoesNotExistException;
import com.major.backend.models.OrderEntity;

import java.util.List;

public interface OrderService {

	String createOrder(Cart order, String email) throws OrderAlreadyExistException;
	OrderEntity getOrderByName(String orderName) throws OrderDoesNotExistException;
	String updateOrderDetails(String orderName, Cart order) throws OrderDoesNotExistException;
	List<OrderEntity> getOrders();
}
