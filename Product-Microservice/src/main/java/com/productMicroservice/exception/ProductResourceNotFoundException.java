package com.productMicroservice.exception;

import com.productMicroservice.DTO.ProductRequestDTO;
import org.springframework.http.HttpStatus;

public class ProductResourceNotFoundException extends RuntimeException{

    private String resourceName;
    private String fieldName;
    private String fieldValue;

    private ProductRequestDTO productRequestDTO;
    private HttpStatus status;

    public ProductResourceNotFoundException(String resourceName, String fieldName, String fieldValue) {
        super(String.format("%s not found with %s : '%s'",resourceName,fieldName,fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public ProductResourceNotFoundException(String resourceName, ProductRequestDTO productRequestDTO, HttpStatus status) {
        super(String.format("%s data could not be onboarded with %s : '%s'",resourceName,productRequestDTO,status));
        this.resourceName = resourceName;
        this.productRequestDTO = productRequestDTO;
        this.status = status;
    }

    public ProductResourceNotFoundException(String resourceName, HttpStatus status) {
        super(String.format("%s are not available %s : '%s'",resourceName,status));
        this.resourceName = resourceName;
        this.status = status;
    }
}
