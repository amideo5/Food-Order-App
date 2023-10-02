package com.major.backend.repositories;

import com.major.backend.models.CategoryEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends MongoRepository<CategoryEntity, String> {

    CategoryEntity findByCategory(String category);

}
