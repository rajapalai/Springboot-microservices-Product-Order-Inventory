package com.inventoryMicroservice.DAO;

import com.inventoryMicroservice.model.Inventory;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface InventoryDAO extends MongoRepository<Inventory, String> {

    Inventory findByProductId (String productId);
//    Inventory findBycheckStock (String productId);
}
