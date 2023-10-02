package com.major.backend.controller;

import com.major.backend.controllers.OrderController;
import com.major.backend.exception.OrderAlreadyExistException;
import com.major.backend.exception.OrderDoesNotExistException;
import com.major.backend.models.OrderEntity;
import com.major.backend.request.Cart;
import com.major.backend.services.OrderService;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
public class OrderControllerTest {

    @InjectMocks
    OrderController orderController;

    @Mock
    OrderService orderService;

    @Test
    public void testGetOrders() {
        List<OrderEntity> orders = new ArrayList<>();
        OrderEntity order1 = new OrderEntity();
        order1.setOrderId("1L");
        order1.setOrderName("John");
        orders.add(order1);
        OrderEntity order2 = new OrderEntity();
        order2.setOrderId("2L");
        order2.setOrderName("Jane");
        orders.add(order2);
        when(orderService.getOrders()).thenReturn(orders);
        ResponseEntity<?> response = orderController.getOrders();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(orders, response.getBody());
    }

    @Test
    public void testGetOrder() throws OrderDoesNotExistException {
        String orderName = "Order 1";
        OrderEntity order = new OrderEntity();
        order.setOrderId("1L");
        order.setOrderName(orderName);
        when(orderService.getOrderByName(orderName)).thenReturn(order);
        ResponseEntity<?> response = orderController.getOrder(orderName);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(order, response.getBody());
    }

    @Test
    public void testGetOrder_OrderDoesNotExistException() throws OrderDoesNotExistException {
        String orderName = "Order 1";
        when(orderService.getOrderByName(orderName)).thenThrow(new OrderDoesNotExistException("Order does not exist"));
        ResponseEntity<?> response = orderController.getOrder(orderName);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Order does not exist", response.getBody());
    }

    @Test
    public void testCreateOrder() throws OrderAlreadyExistException {
        String email = "test@test.com";
        Cart cart = new Cart();
        when(orderService.createOrder(cart, email)).thenReturn("Order created successfully");
        ResponseEntity<?> response = orderController.createOrder(cart, email);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Order created successfully", response.getBody());
    }

    @Test
    public void testCreateOrder_OrderAlreadyExistException() throws OrderAlreadyExistException {
        String email = "test@test.com";
        Cart cart = new Cart();
        when(orderService.createOrder(cart, email)).thenThrow(new OrderAlreadyExistException("Order already exists"));
        ResponseEntity<?> response = orderController.createOrder(cart, email);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

    }

    @Test
    public void updateOrderTest() throws OrderDoesNotExistException {
        Cart cart = new Cart();

        when(orderService.updateOrderDetails(any(String.class), any(Cart.class)))
                .thenReturn("Order updated successfully");

        ResponseEntity<?> response = orderController.updateOrder("Test Order", cart);

        assert(response.getStatusCode().equals(HttpStatus.OK));
        assert(response.getBody().equals("Order updated successfully"));
    }
}
