package com.major.backend.repositories;



import com.major.backend.models.FoodEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodRepository extends MongoRepository<FoodEntity, Long> {

	FoodEntity findByFoodName(String foodName);

	List<FoodEntity> findByFoodCategory(String foodCategory);


}
