package com.major.backend.services;

import com.major.backend.exception.FoodAlreadyExistException;
import com.major.backend.exception.FoodDoesNotExistException;
import com.major.backend.models.CategoryEntity;
import com.major.backend.models.FoodEntity;
import com.major.backend.repositories.CategoryRepository;
import com.major.backend.repositories.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.random.RandomGenerator;

@Service
public class FoodServiceImpl implements FoodService{

    @Autowired
    FoodRepository foodRepository;

    @Autowired
    CategoryRepository categoryRepository;

    public FoodServiceImpl(FoodRepository foodRepository, CategoryRepository categoryRepository) {
        this.foodRepository = foodRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public String createFood(FoodEntity food) throws FoodAlreadyExistException {
        if(foodRepository.findByFoodName(food.getFoodName()) != null){
            throw new FoodAlreadyExistException(food.getFoodName());
        }
        CategoryEntity categoryEntity = new CategoryEntity();
        FoodEntity foodEntity = new FoodEntity();
        foodEntity.setFoodId("#" + RandomGenerator.getDefault().nextInt(999999999));
        foodEntity.setFoodCategory(food.getFoodCategory());
        foodEntity.setFoodName(food.getFoodName());
        foodEntity.setFoodPrice(food.getFoodPrice());
        if(categoryRepository.findByCategory(food.getFoodCategory()) == null) {
            categoryEntity.setCategory(food.getFoodCategory());
            categoryEntity.setCategoryId("#" + RandomGenerator.getDefault().nextInt(999999999));
            categoryRepository.save(categoryEntity);    }
        foodRepository.save(foodEntity);


        return "Food Created";
    }

    @Override
    public FoodEntity getFoodByName(String foodName) throws FoodDoesNotExistException {
        if (foodRepository.findByFoodName(foodName) == null) {
           throw new FoodDoesNotExistException(foodName);
        }
        return foodRepository.findByFoodName(foodName);
    }

    @Override
    public List<FoodEntity> getFoodByCategory(String foodCategory) throws FoodDoesNotExistException {
        if (foodRepository.findByFoodCategory(foodCategory) == null) {
            throw new FoodDoesNotExistException(foodCategory);
        }
        return foodRepository.findByFoodCategory(foodCategory);
    }

    @Override
    public String updateFoodDetails(String foodName, FoodEntity foodDetails) throws FoodDoesNotExistException{
        if(foodRepository.findByFoodName(foodName) == null){
            throw new FoodDoesNotExistException(foodName);
        }
        CategoryEntity categoryEntity = new CategoryEntity();
        FoodEntity foodEntity = foodRepository.findByFoodName(foodName);
        foodEntity.setFoodCategory(foodDetails.getFoodCategory());
        foodEntity.setFoodName(foodDetails.getFoodName());
        foodEntity.setFoodPrice(foodDetails.getFoodPrice());
        if(categoryRepository.findByCategory(foodDetails.getFoodCategory()) == null) {
            categoryEntity.setCategory(foodDetails.getFoodCategory());
            categoryRepository.save(categoryEntity);    }
        foodRepository.save(foodEntity);

        return "Food Updated";
    }

    @Override
    public List<FoodEntity> getFoods() {
        return foodRepository.findAll();
    }

    @Override
    public List<CategoryEntity> getCategoryName() {

        List<CategoryEntity> list1= categoryRepository.findAll();

        return list1;
    }

}
