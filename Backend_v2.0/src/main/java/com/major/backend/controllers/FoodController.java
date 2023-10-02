package com.major.backend.controllers;

import com.major.backend.exception.FoodAlreadyExistException;
import com.major.backend.exception.FoodDoesNotExistException;
import com.major.backend.models.CategoryEntity;
import com.major.backend.models.FoodEntity;
import com.major.backend.services.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/foods")
@CrossOrigin(origins = "http://localhost:3000/")
public class FoodController {
	
	@Autowired
	FoodService foodService;

	@GetMapping(path = "/getFoods")
	public ResponseEntity<?> getFoods() {
		List<FoodEntity> foods = foodService.getFoods();
		return ResponseEntity.status(HttpStatus.OK).body(foods);
	}

	@GetMapping(path="/getFood/{foodName}")
	public ResponseEntity<?> getFood(@PathVariable String foodName) throws FoodDoesNotExistException {
		try {
			FoodEntity food = foodService.getFoodByName(foodName);
			return ResponseEntity.status(HttpStatus.OK).body(food);
		}
		catch (FoodDoesNotExistException e){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Food does Not Exist");
		}
	}

	@GetMapping(path="/getFoodCategory/{foodCategory}")
	public ResponseEntity<?> getFoodByCategory(@PathVariable String foodCategory) throws FoodDoesNotExistException {
		try {
			List<FoodEntity> food = foodService.getFoodByCategory(foodCategory);
			return ResponseEntity.status(HttpStatus.OK).body(food);
		}
		catch (FoodDoesNotExistException e){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Food does Not Exist");
		}
	}

	@GetMapping(path="/getFoodCategory")
	public ResponseEntity<?> getFoodCategory() {
		List<CategoryEntity> categories = foodService.getCategoryName();
		return ResponseEntity.status(HttpStatus.OK).body(categories);

	}

	@PostMapping(path="/createFood")
	public ResponseEntity<?> createFood(@RequestBody FoodEntity foodEntity) throws FoodAlreadyExistException {
		try {
			String createFood = foodService.createFood(foodEntity);
			return ResponseEntity.status(HttpStatus.OK).body(createFood);
		}
		catch (FoodAlreadyExistException e){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Food Already Exists");
		}
	}

	@PutMapping(path="/updateFood/{foodName}")
	public ResponseEntity<?> updateFood(@PathVariable String foodName, @RequestBody FoodEntity foodDetails) throws FoodDoesNotExistException {
		try{
			String updatedFood = foodService.updateFoodDetails(foodName, foodDetails);
			return ResponseEntity.status(HttpStatus.OK).body(updatedFood);
		}
		catch (FoodDoesNotExistException e){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Food does not Exists");
		}
	}

}
