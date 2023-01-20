package com.productMicroservice.mapperUtil;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.productMicroservice.DTO.ProductRequestDTO;
import com.productMicroservice.DTO.ProductResponseDTO;
import com.productMicroservice.model.Product;

import java.util.UUID;

public class ProductMapper {

    public static Product convertToEntity (ProductRequestDTO productRequestDTO) {
        Product product =new Product();
        product.setProductName(productRequestDTO.getProductName());
        product.setProductDescription(productRequestDTO.getProductDescription());
        product.setProductAmount(productRequestDTO.getProductAmount());
        product.setProductType(productRequestDTO.getProductType());
        product.setProductSupplierName(productRequestDTO.getProductSupplierName());
        product.setProductSupplierCode(UUID.randomUUID().toString().split("-")[0]);
        return product;
    }

    public static ProductResponseDTO convertToDto (Product product) {
        ProductResponseDTO productResponseDTO = new ProductResponseDTO();
        productResponseDTO.setProductId(product.getProductId());
        productResponseDTO.setProductName(product.getProductName());
        productResponseDTO.setProductDescription(product.getProductDescription());
        productResponseDTO.setProductAmount(product.getProductAmount());
        productResponseDTO.setProductType(product.getProductType());
        productResponseDTO.setProductSupplierName(product.getProductSupplierName());
        productResponseDTO.setProductSupplierCode(product.getProductSupplierCode());
        return productResponseDTO;
    }

    public static String jsonAsString (Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
