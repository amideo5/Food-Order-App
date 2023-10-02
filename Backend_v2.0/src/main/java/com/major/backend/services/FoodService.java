package com.major.backend.services;


import com.major.backend.exception.FoodAlreadyExistException;
import com.major.backend.exception.FoodDoesNotExistException;
import com.major.backend.models.CategoryEntity;
import com.major.backend.models.FoodEntity;

import java.util.List;

public interface FoodService {

	String createFood(FoodEntity food) throws FoodAlreadyExistException;
	FoodEntity getFoodByName(String foodName) throws FoodDoesNotExistException;
	List<FoodEntity> getFoodByCategory(String foodCategory) throws FoodDoesNotExistException;
	String updateFoodDetails(String foodName, FoodEntity foodDetails) throws FoodDoesNotExistException;
	List<FoodEntity> getFoods();
	List<CategoryEntity> getCategoryName() ;
}
