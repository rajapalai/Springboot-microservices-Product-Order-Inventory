package com.productMicroservice.service;

import com.productMicroservice.DAO.ProductDao;
import com.productMicroservice.DTO.InventoryDTO;
import com.productMicroservice.DTO.ProductRequestDTO;
import com.productMicroservice.DTO.ProductResponseDTO;
import com.productMicroservice.clientConfigServices.ApiClient;
import com.productMicroservice.exception.ProductResourceNotFoundException;
import com.productMicroservice.exception.ProductServiceBusinessException;
import com.productMicroservice.mapperUtil.ProductMapper;
import com.productMicroservice.model.Product;
import com.productMicroservice.serviceImpl.ProductInterface;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class ProductService implements ProductInterface {

    private ProductDao productDao;
    private ApiClient apiClient;

    @Override
    public ProductResponseDTO createNewProduct(ProductRequestDTO productRequestDTO) {
        ProductResponseDTO productResponseDTO;

        try {
            log.info("ProductService:createNewProduct execution started.");
            Product product = ProductMapper.convertToEntity(productRequestDTO);
            log.debug("ProductService:createNewProduct request parameters {}", ProductMapper.jsonAsString(productRequestDTO));

            Product saveProduct = productDao.save(product);
            productResponseDTO = ProductMapper.convertToDto(saveProduct);

            log.debug("ProductService:createNewProduct received response from Database {}", ProductMapper.jsonAsString(productRequestDTO));
        } catch (Exception e) {
            log.error("Exception occurred while persisting new product to database , Exception message {}", e.getMessage());
            throw new ProductServiceBusinessException("Exception occurred while create a new product");
        }
        log.info("ProductService:createNewProduct execution ended.");
        return productResponseDTO;
    }

    @Override
    public List<ProductResponseDTO> getListOfProducts() {
        List<ProductResponseDTO> productResponseDTO;
        try {
            log.info("ProductService:getListOfProducts execution started.");
            List<Product> productList = productDao.findAll();

            if (!productList.isEmpty()) {
                productResponseDTO = productList.stream()
                        .map(ProductMapper::convertToDto)
                        .collect(Collectors.toList());
            } else {
                productResponseDTO = Collections.emptyList();
            }
            log.debug("ProductService:getProducts retrieving products from database  {}", ProductMapper.jsonAsString(productResponseDTO));
        } catch (Exception e) {
            log.error("Exception occurred while retrieving products from database , Exception message {}", e.getMessage());
            throw new ProductServiceBusinessException("getListOfProducts service method failed...");
        }
        return productResponseDTO;
    }

    @Override
    public ProductResponseDTO getProductById(String productId) {
        ProductResponseDTO productResponseDTO;

        try {
            log.info("ProductService:getProductById execution started.");
            Product product = productDao.findById(productId)
                    .orElseThrow(() -> new ProductResourceNotFoundException("Product", "productId", productId));
            productResponseDTO = ProductMapper.convertToDto(product);

            InventoryDTO inventoryDTO = apiClient.getInventoryByProductID(productId);
            productResponseDTO.setInventory(inventoryDTO);
            log.info("{} ", inventoryDTO);
            log.debug("ProductService:getProductById retrieving product from database for id {} {}", productId, ProductMapper.jsonAsString(productResponseDTO));

        } catch (Exception ex) {
            log.error("Exception occurred while retrieving product {} from database , Exception message {}", productId, ex.getMessage());
            throw new ProductServiceBusinessException("Exception occurred while fetch product from Database " + productId);
        }
        log.info("ProductService:getProductById execution ended.");
        return productResponseDTO;
    }

    @Override
    public List<ProductResponseDTO> getProductByName(String productName) {
        List<ProductResponseDTO> productResponseDTO;

        try {
            log.info("ProductService:getProductByName execution started.");
            List<Product> product = productDao.findByName(productName);
//                    .thr (() -> new ProductResourceNotFoundException("Product", "productId", productName));
            productResponseDTO = product.stream()
                    .map(ProductMapper::convertToDto)
                    .collect(Collectors.toList());

            log.debug("ProductService:getProductByName retrieving product from database for id {} {}", productName, ProductMapper.jsonAsString(productResponseDTO));

        } catch (Exception ex) {
            log.error("Exception occurred while retrieving product {} from database , Exception message {}", productName, ex.getMessage());
            throw new ProductServiceBusinessException("Exception occurred while fetch product from Database " + productName);
        }
        log.info("ProductService:getProductByName execution ended.");
        return productResponseDTO;
    }

    @Override
    public void removeProduct(String productId) {

    }

    @Override
    public ProductResponseDTO updateProductInformation(String productId, ProductRequestDTO productRequestDTO) {
        ProductResponseDTO productResponseDTO;
        Product existingProduct = null;
        try {
            log.info("ProductService:updateProductInformation execution started.");
            existingProduct = productDao.findById(productId)
                    .orElseThrow(() -> new ProductResourceNotFoundException("Product", "productId", productId));

            existingProduct.setProductName(productRequestDTO.getProductName());
            existingProduct.setProductDescription(productRequestDTO.getProductDescription());
            existingProduct.setProductAmount(productRequestDTO.getProductAmount());
            existingProduct.setProductType(productRequestDTO.getProductType());
            existingProduct.setProductSupplierName(productRequestDTO.getProductSupplierName());

            Product updatedProduct = productDao.save(existingProduct);
            productResponseDTO = ProductMapper.convertToDto(updatedProduct);
            log.debug("ProductService:updateProductInformation updating product from database for id {} {}", productId, ProductMapper.jsonAsString(productResponseDTO));
        } catch (Exception e) {
            log.error("Exception occurred while updating product {} from database , Exception message {}", productId, e.getMessage());
            throw new ProductServiceBusinessException("Exception occurred while update product from Database with id" + productId);
        }
        log.info("ProductService:updateProductInformation execution ended.");
        return productResponseDTO;
    }

    @Override
    public Map<String, List<ProductResponseDTO>> getListOfProductsWithTypes() {
        try {
            log.info("ProductService:getListOfProductsWithTypes execution started.");

            Map<String, List<ProductResponseDTO>> productsMap =
                    productDao.findAll().stream()
                            .map(ProductMapper::convertToDto)
                            .filter(productResponseDTO -> productResponseDTO.getProductType() != null)
                            .collect(Collectors.groupingBy(ProductResponseDTO::getProductType));

            log.info("ProductService:getListOfProductsWithTypes execution ended.");
            return productsMap;

        } catch (Exception ex) {
            log.error("Exception occurred while retrieving product grouping by type from database , Exception message {}", ex.getMessage());
            throw new ProductServiceBusinessException("Exception occurred while fetch product from Database ");
        }
    }


}
