package com.major.backend.repositories;


import com.major.backend.models.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<UserEntity, Long> {

	UserEntity findByEmail(String email);

}
