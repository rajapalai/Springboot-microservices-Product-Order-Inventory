package com.orderMicroservice.DAO;

import com.orderMicroservice.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface OrderDAO extends MongoRepository<Order,String> {
    Order findByOrderNumber(String orderNumber);
}
