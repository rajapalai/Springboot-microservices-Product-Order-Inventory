package com.productMicroservice.serviceImpl;

import com.productMicroservice.DTO.ProductRequestDTO;
import com.productMicroservice.DTO.ProductResponseDTO;

import java.util.List;
import java.util.Map;

public interface ProductInterface {

    public ProductResponseDTO createNewProduct (ProductRequestDTO productRequestDTO);
    public List<ProductResponseDTO> getListOfProducts();
    public ProductResponseDTO getProductById (String productId);
    public List<ProductResponseDTO> getProductByName (String productName);
    public void removeProduct (String productId);
    public ProductResponseDTO updateProductInformation(String productId, ProductRequestDTO productRequestDTO);
    public Map<String, List<ProductResponseDTO>> getListOfProductsWithTypes();
}
