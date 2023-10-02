package com.major.backend.service;

import com.major.backend.exception.OrderDoesNotExistException;
import com.major.backend.models.FoodEntity;
import com.major.backend.models.OrderEntity;
import com.major.backend.models.UserEntity;
import com.major.backend.repositories.FoodRepository;
import com.major.backend.repositories.OrderRepository;
import com.major.backend.repositories.UserRepository;
import com.major.backend.request.Cart;
import com.major.backend.services.OrderServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mongodb.assertions.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class OrderServiceTest {

    @Mock
    OrderRepository orderRepository;

    @Mock
    FoodRepository foodRepository;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    OrderServiceImpl orderService;

    @Before
    public void setUp() {
        orderRepository = mock(OrderRepository.class);
        userRepository = mock(UserRepository.class);
        foodRepository = mock(FoodRepository.class);
    }

    @Test
    public void testCreateOrder() throws Exception {
        Cart cart = new Cart();
        Map<String, Integer> orderList = new HashMap<>();
        orderList.put("Pizza", 2);
        orderList.put("Burger", 1);
        cart.setOrderList(orderList);
        String email = "john.doe@example.com";

        UserEntity userEntity = new UserEntity();
        userEntity.setName("John Doe");
        when(userRepository.findByEmail(email)).thenReturn(userEntity);

        FoodEntity pizzaEntity = new FoodEntity();
        pizzaEntity.setFoodName("Pizza");
        pizzaEntity.setFoodPrice(10.0);
        when(foodRepository.findByFoodName("Pizza")).thenReturn(pizzaEntity);

        FoodEntity burgerEntity = new FoodEntity();
        burgerEntity.setFoodName("Burger");
        burgerEntity.setFoodPrice(5.0);
        when(foodRepository.findByFoodName("Burger")).thenReturn(burgerEntity);

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderId("#123");
        when(orderRepository.save(orderEntity)).thenReturn(orderEntity);
        OrderServiceImpl orderService = new OrderServiceImpl(orderRepository, userRepository, foodRepository);
        String result = orderService.createOrder(cart, email);

        assertNotNull(result);
        assertEquals("Order Created", result);
    }

//    @Test
//    public void testUpdateOrderDetails() throws OrderDoesNotExistException, OrderAlreadyExistException {
//        Cart cart = new Cart();
//        Map<String, Integer> orderList = new HashMap<>();
//        orderList.put("Pizza", 2);
//        orderList.put("Burger", 1);
//        cart.setOrderList(orderList);
//        String email = "john.doe@example.com";
//
//        UserEntity userEntity = new UserEntity();
//        userEntity.setName("John Doe");
//        when(userRepository.findByEmail(email)).thenReturn(userEntity);
//
//        FoodEntity pizzaEntity = new FoodEntity();
//        pizzaEntity.setFoodName("Pizza");
//        pizzaEntity.setFoodPrice(10.0);
//        when(foodRepository.findByFoodName("Pizza")).thenReturn(pizzaEntity);
//
//        FoodEntity burgerEntity = new FoodEntity();
//        burgerEntity.setFoodName("Burger");
//        burgerEntity.setFoodPrice(5.0);
//        when(foodRepository.findByFoodName("Burger")).thenReturn(burgerEntity);
//
//        OrderEntity orderEntity = new OrderEntity();
//        orderEntity.setOrderId("#123");
//        when(orderRepository.save(orderEntity)).thenReturn(orderEntity);
//        OrderServiceImpl orderService = new OrderServiceImpl(orderRepository, userRepository, foodRepository);
//        orderService.createOrder(cart, email);
//        String result = orderService.updateOrderDetails(userEntity.getName() ,cart);
//
//        assertNotNull(result);
//        assertEquals("Order Created", result);
//    }

    @Test
    public void testGetOrderByName() throws OrderDoesNotExistException {

        OrderEntity result = new OrderEntity();
        result.setOrderId("101");result.setCost(20.0);result.setOrderName("Aryan");
        when(orderRepository.findByOrderName("Aryan")).thenReturn(result);
        OrderServiceImpl orderService = new OrderServiceImpl(orderRepository, userRepository, foodRepository);
        OrderEntity orderEntity = orderService.getOrderByName("Aryan");

        assertEquals("Aryan", orderEntity.getOrderName());
        assertEquals(20.0, result.getCost());
    }

    @Test
    public void testGetOrders() {
        OrderEntity ord1 = new OrderEntity();
        ord1.setOrderId("101");ord1.setCost(20.0);ord1.setOrderName("Aryan");
        OrderEntity ord2 = new OrderEntity();
        ord2.setOrderId("102");ord2.setCost(220.0);ord2.setOrderName("Yogesh");
        List<OrderEntity> orders = List.of(ord1, ord2);

        when(orderRepository.findAll()).thenReturn(orders);
        OrderServiceImpl orderService = new OrderServiceImpl(orderRepository, userRepository, foodRepository);
        List<OrderEntity> result = orderService.getOrders();
        assertEquals(2, result.size());
        assertEquals(result, orders);

    }


}
