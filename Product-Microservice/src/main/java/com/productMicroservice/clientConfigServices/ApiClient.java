package com.productMicroservice.clientConfigServices;

import com.productMicroservice.DTO.InventoryDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.HashSet;

@FeignClient(value = "INVENTORY-SERVICE", url = "http://localhost:9002")
public interface ApiClient {


    @GetMapping(value = "/inventory/v1/app/external/{productID}")
    InventoryDTO getInventoryByProductID(@PathVariable("productID") String productId);


}

