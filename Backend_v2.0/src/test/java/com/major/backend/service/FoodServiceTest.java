package com.major.backend.service;

import com.major.backend.exception.FoodAlreadyExistException;
import com.major.backend.exception.FoodDoesNotExistException;
import com.major.backend.models.CategoryEntity;
import com.major.backend.models.FoodEntity;
import com.major.backend.repositories.CategoryRepository;
import com.major.backend.repositories.FoodRepository;
import com.major.backend.services.FoodServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class FoodServiceTest {

    @Mock
    FoodRepository foodRepository;

    @Mock
    CategoryRepository categoryRepository;

    @InjectMocks
    FoodServiceImpl foodService;

    @Test
    public void testGetFoods() {
        FoodEntity food1 = new FoodEntity();
        food1.setFoodId("101");food1.setFoodName("Pizza");food1.setFoodPrice(20.0);food1.setFoodCategory("Starter");
        FoodEntity food2 = new FoodEntity();
        food1.setFoodId("102");food1.setFoodName("Burger");food1.setFoodPrice(30.0);food1.setFoodCategory("Starter");
        List<FoodEntity> foods = List.of(food2, food1);
        when(foodRepository.findAll()).thenReturn(foods);


        List<FoodEntity> result = foodService.getFoods();
        assertEquals(2, result.size());
        assertEquals(result, foods);
    }

    @Test
    public void testGetCategoryName() {
        CategoryEntity cat1 = new CategoryEntity();
        cat1.setCategoryId("101");cat1.setCategory("Fruits");
        CategoryEntity cat2 = new CategoryEntity();
        cat2.setCategoryId("102");cat2.setCategory("Vegetables");
        List<CategoryEntity> categories = List.of(cat1, cat2);
        when(categoryRepository.findAll()).thenReturn(categories);

        List<CategoryEntity> result = foodService.getCategoryName();
        assertEquals(2, result.size());
        assertEquals(result, categories);
    }

    @Test
    public void testUpdateFoodDetails() throws FoodDoesNotExistException {
        FoodEntity food1 = new FoodEntity();
        food1.setFoodId("101");food1.setFoodName("Pizza");food1.setFoodPrice(20.0);food1.setFoodCategory("Starter");
        when(foodRepository.findByFoodName("Pizza")).thenReturn(food1);

        FoodEntity updatedFoodEntity = new FoodEntity();
        updatedFoodEntity.setFoodCategory("Italian");
        updatedFoodEntity.setFoodPrice(10.0);
        String result = foodService.updateFoodDetails("Pizza", updatedFoodEntity);

        assertEquals("Food Updated", result);

        assertThrows(FoodDoesNotExistException.class, () -> foodService.updateFoodDetails("Burger", updatedFoodEntity));
    }

    @Test
    public void testGetFoodByCategory() throws FoodDoesNotExistException {
        FoodEntity food1 = new FoodEntity();
        food1.setFoodId("101");food1.setFoodName("Pizza");food1.setFoodPrice(20.0);food1.setFoodCategory("Italian");
        FoodEntity food2 = new FoodEntity();
        food2.setFoodId("102");food2.setFoodName("Burger");food2.setFoodPrice(30.0);food2.setFoodCategory("Japanese");
        List<FoodEntity> foods = List.of(food1, food2);
        when(foodRepository.findByFoodCategory("Italian")).thenReturn(foods);
        List<FoodEntity> result = foodService.getFoodByCategory("Italian");

        assertEquals(2, result.size());
        assertEquals("Pizza", result.get(0).getFoodName());
        assertEquals("Italian", result.get(0).getFoodCategory());
        assertEquals(20.0, result.get(0).getFoodPrice());
        assertEquals("Burger", result.get(1).getFoodName());
        assertEquals("Japanese", result.get(1).getFoodCategory());
        assertEquals(30.0, result.get(1).getFoodPrice());
    }

    @Test
    public void testGetFoodByName() throws FoodDoesNotExistException {
        FoodEntity food1 = new FoodEntity();
        food1.setFoodId("101");food1.setFoodName("Pizza");food1.setFoodPrice(10.0);food1.setFoodCategory("Italian");
        when(foodRepository.findByFoodName("Pizza")).thenReturn(food1);
        FoodEntity result = foodService.getFoodByName("Pizza");

        assertEquals("Pizza", result.getFoodName());
        assertEquals("Italian", result.getFoodCategory());
        assertEquals(10.0, result.getFoodPrice());

        assertThrows(FoodDoesNotExistException.class, () -> foodService.getFoodByName("Burger"));
    }

    @Test
    public void testCreateFood() throws FoodAlreadyExistException {
        when(foodRepository.findByFoodName("Pizza")).thenReturn(null);

        when(categoryRepository.findByCategory("Italian")).thenReturn(null);

        FoodEntity food1 = new FoodEntity();
        food1.setFoodId("101");food1.setFoodName("Pizza");food1.setFoodPrice(20.0);food1.setFoodCategory("Italian");
        String result = foodService.createFood(food1);

        assertEquals("Food Created", result);

        verify(foodRepository, times(1)).save(any());
        verify(categoryRepository, times(1)).save(any());
    }

    @Test
    public void testCreateFoodAlreadyExistException() {
        FoodEntity existingFoodEntity = new FoodEntity();
        existingFoodEntity.setFoodId("101");existingFoodEntity.setFoodName("Pizza");existingFoodEntity.setFoodPrice(20.0);existingFoodEntity.setFoodCategory("Italian");
        when(foodRepository.findByFoodName("Pizza")).thenReturn(existingFoodEntity);

        FoodEntity foodEntity = new FoodEntity();
        foodEntity.setFoodId("101");foodEntity.setFoodName("Pizza");foodEntity.setFoodPrice(20.0);foodEntity.setFoodCategory("Mexican");

        assertThrows(FoodAlreadyExistException.class, () -> foodService.createFood(foodEntity));

        verify(foodRepository, times(0)).save(any());
    }

}

