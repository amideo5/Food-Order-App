package com.major.backend.controllers;

import com.major.backend.request.Cart;
import com.major.backend.exception.OrderAlreadyExistException;
import com.major.backend.exception.OrderDoesNotExistException;
import com.major.backend.models.OrderEntity;
import com.major.backend.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@CrossOrigin(origins = "http://localhost:3000/")
public class OrderController {
	
	@Autowired
	OrderService orderService;

	@GetMapping(path = "/getOrders")
	public ResponseEntity<?> getOrders() {
		List<OrderEntity> orders = orderService.getOrders();
		return ResponseEntity.status(HttpStatus.OK).body(orders);
	}

	@GetMapping(path="/getOrder/{name}")
	public ResponseEntity<?> getOrder(@PathVariable String name) throws OrderDoesNotExistException {
		try{
			OrderEntity order = orderService.getOrderByName(name);
			return ResponseEntity.status(HttpStatus.OK).body(order);
		}
		catch (OrderDoesNotExistException e){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@PostMapping(path="/createOrder/{email}")
	public ResponseEntity<?> createOrder(@RequestBody Cart order,@PathVariable String email) throws OrderAlreadyExistException {
		try {
			String createOrder = orderService.createOrder(order, email);
			return ResponseEntity.status(HttpStatus.OK).body(createOrder);
		}
		catch (OrderAlreadyExistException e){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@PutMapping(path="/updateOrder/{orderName}")
	public ResponseEntity<?> updateOrder(@PathVariable String orderName, @RequestBody Cart order) throws OrderDoesNotExistException {
		try{
			String updateOrder = orderService.updateOrderDetails(orderName, order);
			return ResponseEntity.status(HttpStatus.OK).body(updateOrder);
		}
		catch (OrderDoesNotExistException e){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}

	}

}
