package com.major.backend.services;

import com.major.backend.request.Cart;
import com.major.backend.response.DispatchItenary;
import com.major.backend.exception.OrderAlreadyExistException;
import com.major.backend.exception.OrderDoesNotExistException;
import com.major.backend.models.FoodEntity;
import com.major.backend.models.OrderEntity;
import com.major.backend.models.UserEntity;
import com.major.backend.repositories.FoodRepository;
import com.major.backend.repositories.OrderRepository;
import com.major.backend.repositories.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    FoodRepository foodRepository;

    @Autowired
    UserRepository userRepository;

    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository, FoodRepository foodRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.foodRepository = foodRepository;
    }

    public String createBillNo(){
        return "#"+"150"+ RandomStringUtils.randomAlphanumeric(7);
    }

    @Override
    public String createOrder(Cart order, String email) throws OrderAlreadyExistException {
        UserEntity userEntity = userRepository.findByEmail(email);
        String nameO = userEntity.getName();
        if (orderRepository.findByOrderName(nameO) != null){
            throw new OrderAlreadyExistException(nameO);
        }
        OrderEntity orderEntity = new OrderEntity();
        DispatchItenary dispatchItenary = new DispatchItenary();
        Map<String ,Integer> orderDetails = new HashMap<>();
        Map<String, Integer> map = order.getOrderList();
        Double costPI = 0.0;
        ArrayList<Double> costs = new ArrayList<Double>();
        for (Map.Entry mapElement : map.entrySet()) {

            FoodEntity foodEntity = foodRepository.findByFoodName((String) mapElement.getKey());
            Double costP = foodEntity.getFoodPrice();
            costPI = costP * (int) mapElement.getValue();
            costs.add(costPI);
            String orderId = "#" + orderEntity.getOrderId();
            orderDetails.put(orderId, (int) mapElement.getValue());
        }
        Double cost  = 0.0;
        for (Double i : costs)
            cost += i;

        String billNo = createBillNo();
        orderEntity.setOrderId(billNo);
        Map<String, Integer> dispatchMap = order.getOrderList();
        dispatchItenary.setOrderItems(dispatchMap);
        orderEntity.setItems(dispatchItenary);
        orderEntity.setCost(cost);

        UserEntity user = userRepository.findByEmail(email);
        orderEntity.setOrderName(user.getName());
        orderRepository.save(orderEntity);
        return "Order Created";
    }

    @Override
    public String updateOrderDetails(String orderName, Cart order) throws OrderDoesNotExistException {
        OrderEntity orderEntity = orderRepository.findByOrderName(orderName);
        if(orderEntity == null){
            throw new OrderDoesNotExistException(orderName);
        }
        DispatchItenary dispatchItenary = new DispatchItenary();
        Map<String ,Integer> orderDetails = new HashMap<>();
        Map<String, Integer> map = order.getOrderList();
        Double costPI = 0.0;
        ArrayList<Double> costs = new ArrayList<Double>();
        for (Map.Entry mapElement : map.entrySet()) {

            FoodEntity foodEntity = foodRepository.findByFoodName((String) mapElement.getKey());
            Double costP = foodEntity.getFoodPrice();
            costPI = costP * (int) mapElement.getValue();
            costs.add(costPI);
            String orderId = "#" + orderEntity.getOrderId();
            orderDetails.put(orderId, (int) mapElement.getValue());
        }
        Double cost  = 0.0;
        for (Double i : costs)
            cost += i;

        Map<String, Integer> dispatchMap = order.getOrderList();
        dispatchItenary.setOrderItems(dispatchMap);
        orderEntity.setItems(dispatchItenary);
        orderEntity.setCost(cost);

        orderRepository.save(orderEntity);
        return "Order Updated";
    }

    @Override
    public OrderEntity getOrderByName(String orderName) throws OrderDoesNotExistException {
        if(orderRepository.findByOrderName(orderName) == null){
            throw new OrderDoesNotExistException(orderName);
        }
        return orderRepository.findByOrderName(orderName);
    }



    @Override
    public List<OrderEntity> getOrders() {
        return orderRepository.findAll();
    }



}
