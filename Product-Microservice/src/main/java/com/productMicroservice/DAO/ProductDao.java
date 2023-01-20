package com.productMicroservice.DAO;

import com.productMicroservice.DTO.ProductRequestDTO;
import com.productMicroservice.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface ProductDao extends MongoRepository<Product, String> {

    @Query("{'product-name' : ?0}")
    List<Product> findByName(String productName);
}
