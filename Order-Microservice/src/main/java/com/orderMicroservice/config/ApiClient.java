package com.orderMicroservice.config;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Configuration
@FeignClient(value = "PRODUCT-SERVICE", url = "http://localhost:9001")
public interface ApiClient {


//    @GetMapping("/product/v1/app/external/id/{productId}")
//     ProductDTO externalMethod(@PathVariable (value = "productId") String productId);
//

}