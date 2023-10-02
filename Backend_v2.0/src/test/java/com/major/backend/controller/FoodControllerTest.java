package com.major.backend.controller;

import com.major.backend.controllers.FoodController;
import com.major.backend.exception.FoodAlreadyExistException;
import com.major.backend.exception.FoodDoesNotExistException;
import com.major.backend.models.CategoryEntity;
import com.major.backend.models.FoodEntity;
import com.major.backend.services.FoodServiceImpl;
import org.junit.Before;
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

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
public class FoodControllerTest {

    @Mock
    FoodServiceImpl foodService;

    @InjectMocks
    FoodController foodController;

    private List<FoodEntity> foods;

    @Before
    public void setUp() {
        FoodEntity food1 = new FoodEntity();
        food1.setFoodId("101");food1.setFoodName("Pizza");food1.setFoodPrice(20.0);food1.setFoodCategory("Starter");
        FoodEntity food2 = new FoodEntity();
        food1.setFoodId("102");food1.setFoodName("Burger");food1.setFoodPrice(30.0);food1.setFoodCategory("Starter");
        List<FoodEntity> foods = List.of(food1, food2);
    }

    @Test
    public void testGetFoods() {
        when(foodService.getFoods()).thenReturn(foods);
        ResponseEntity<?> response = foodController.getFoods();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(foods, response.getBody());
    }

    @Test
    public void testGetFoodSuccess() throws Exception {
        FoodEntity mockFood = new FoodEntity();mockFood.setFoodName("Pizza");
        when(foodService.getFoodByName("Pizza")).thenReturn(mockFood);
        ResponseEntity<?> response = foodController.getFood("Pizza");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockFood, response.getBody());
        verify(foodService, times(1)).getFoodByName("Pizza");
    }

    @Test
    public void testGetFoodFailure() throws Exception {
        when(foodService.getFoodByName("Burger")).thenThrow(new FoodDoesNotExistException("Burger"));
        ResponseEntity<?> response = foodController.getFood("Burger");
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Food does Not Exist", response.getBody());
        verify(foodService, times(1)).getFoodByName("Burger");
    }

    @Test
    public void testGetFoodByCategorySuccess() throws Exception {
        FoodEntity food1 = new FoodEntity();food1.setFoodName("Pizza");
        FoodEntity food2 = new FoodEntity();food1.setFoodName("Pasta");
        List<FoodEntity> mockFoods = Arrays.asList(food1, food2);
        when(foodService.getFoodByCategory("Italian")).thenReturn(mockFoods);
        ResponseEntity<?> response = foodController.getFoodByCategory("Italian");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockFoods, response.getBody());
        verify(foodService, times(1)).getFoodByCategory("Italian");
    }

    @Test
    public void testGetFoodByCategoryFailure() throws Exception {
        when(foodService.getFoodByCategory("Mexican")).thenThrow(new FoodDoesNotExistException("Mexican"));
        ResponseEntity<?> response = foodController.getFoodByCategory("Mexican");
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Food does Not Exist", response.getBody());
        verify(foodService, times(1)).getFoodByCategory("Mexican");
    }

    @Test
    public void testGetFoodCategorySuccess() throws Exception {
        CategoryEntity category1 = new CategoryEntity();category1.setCategory("Italian");
        CategoryEntity category2 = new CategoryEntity();category2.setCategory("Mexican");
        List<CategoryEntity> mockCategories = Arrays.asList(category1, category2);
        when(foodService.getCategoryName()).thenReturn(mockCategories);
        ResponseEntity<?> response = foodController.getFoodCategory();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockCategories, response.getBody());
        verify(foodService, times(1)).getCategoryName();
    }

    @Test
    public void testCreateFoodSuccess() throws Exception {
        FoodEntity foodEntity = new FoodEntity();foodEntity.setFoodName("Pizza");foodEntity.setFoodCategory("Italian");foodEntity.setFoodPrice(10.0);
        when(foodService.createFood(foodEntity)).thenReturn("Food created successfully");
        ResponseEntity<?> response = foodController.createFood(foodEntity);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Food created successfully", response.getBody());
        verify(foodService, times(1)).createFood(foodEntity);
    }

    @Test
    public void testCreateFoodAlreadyExists() throws Exception {
        FoodEntity foodEntity = new FoodEntity();foodEntity.setFoodName("Pizza");foodEntity.setFoodCategory("Italian");foodEntity.setFoodPrice(10.0);
        when(foodService.createFood(foodEntity)).thenThrow(new FoodAlreadyExistException("Food already exists"));
        ResponseEntity<?> response = foodController.createFood(foodEntity);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Food Already Exists", response.getBody());
        verify(foodService, times(1)).createFood(foodEntity);
    }

    @Test
    public void testUpdateFoodSuccess() throws Exception {
        String foodName = "Pizza";
        FoodEntity foodDetails = new FoodEntity();foodDetails.setFoodName("Pizza");foodDetails.setFoodCategory("Italian");foodDetails.setFoodPrice(12.0);
        when(foodService.updateFoodDetails(foodName, foodDetails)).thenReturn("Food updated successfully");
        ResponseEntity<?> response = foodController.updateFood(foodName, foodDetails);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Food updated successfully", response.getBody());
        verify(foodService, times(1)).updateFoodDetails(foodName, foodDetails);
    }

    @Test
    public void testUpdateFoodDoesNotExist() throws Exception {
        String foodName = "Pizza";
        FoodEntity foodDetails = new FoodEntity();foodDetails.setFoodName("Pizza");foodDetails.setFoodCategory("Italian");foodDetails.setFoodPrice(12.0);
        when(foodService.updateFoodDetails(foodName, foodDetails)).thenThrow(new FoodDoesNotExistException("Food does not exist"));
        ResponseEntity<?> response = foodController.updateFood(foodName, foodDetails);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Food does not Exists", response.getBody());
        verify(foodService, times(1)).updateFoodDetails(foodName, foodDetails);
    }


}
