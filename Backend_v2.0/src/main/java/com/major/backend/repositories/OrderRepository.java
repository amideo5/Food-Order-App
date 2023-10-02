package com.major.backend.repositories;


import com.major.backend.models.OrderEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends MongoRepository<OrderEntity, Long> {

	OrderEntity findByOrderName(String orderName);

}
