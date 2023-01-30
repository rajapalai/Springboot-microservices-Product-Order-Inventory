package com.productMicroservice.controller;

import com.productMicroservice.DTO.APIResponseDTO;
import com.productMicroservice.DTO.ProductRequestDTO;
import com.productMicroservice.DTO.ProductResponseDTO;
import com.productMicroservice.exception.ProductResourceNotFoundException;
import com.productMicroservice.mapperUtil.ProductMapper;
import com.productMicroservice.service.ProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/product/v1/app")
@AllArgsConstructor
public class ProductController {

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);
    public static final String SUCCESS = "Success";

    private ProductService productService;

    /**
     * BUILDER DESIGN PATTERN
     */

    @PostMapping
    public ResponseEntity<APIResponseDTO> addNewProduct(@RequestBody @Valid ProductRequestDTO productRequestDTO) {

        ProductResponseDTO productResponseDTO = productService.createNewProduct(productRequestDTO);
        APIResponseDTO<ProductResponseDTO> apiResponseDTO = null;

        try {
            log.info("ProductController::addNewProduct execution started.");
            apiResponseDTO = APIResponseDTO
                    .<ProductResponseDTO>builder()
                    .statusCode(SUCCESS)
                    .result(productResponseDTO)
                    .build();
            log.info("ProductController::addNewProduct request body {}", ProductMapper.jsonAsString(productRequestDTO));
        } catch (Exception e) {
            log.error("ProductController::Product not created : {}", apiResponseDTO.getResult());
            throw new ProductResourceNotFoundException("Product", productRequestDTO, HttpStatus.BAD_REQUEST);
        }
        log.info("ProductController::addNewProduct execution ended.");
        return new ResponseEntity<>(apiResponseDTO, HttpStatus.CREATED);

        /**
         * COMPLEX CODE OLD APPROACH
         *
         APIResponseDTO<ProductResponseDTO> productResponseDTO = new APIResponseDTO<>();
         try {
         log.info("ProductController:addNewProduct execution started.");
         ProductResponseDTO newProduct = productService.createNewProduct(productRequestDTO);
         productResponseDTO.setStatusCode(SUCCESS);
         productResponseDTO.setResult(newProduct);
         log.info("ProductController:addNewProduct request body {}", ProductMapper.jsonAsString(productRequestDTO));
         } catch (Exception exception) {

         log.error("Product not created : {}", productResponseDTO.getResult());
         throw new ProductResourceNotFoundException("Product", productRequestDTO, HttpStatus.BAD_REQUEST);
         }
         log.info("ProductController:addNewProduct execution ended.");
         return new ResponseEntity<>(productResponseDTO, HttpStatus.CREATED);
         */
    }

    @GetMapping
    public ResponseEntity<APIResponseDTO> getProducts() {

        List<ProductResponseDTO> productServerResponse = productService.getListOfProducts();
        APIResponseDTO<List<ProductResponseDTO>> apiResponseDTO = null;

        try {
            log.info("ProductController::getProducts execution started.");
            apiResponseDTO = APIResponseDTO
                    .<List<ProductResponseDTO>>builder()
                    .statusCode(SUCCESS)
                    .result(productServerResponse)
                    .build();
            log.info("ProductController::getProducts response {}", ProductMapper.jsonAsString(apiResponseDTO));
        } catch (Exception e) {
            log.error("ProductController::Products not found : {}", apiResponseDTO.getResult());
            throw new ProductResourceNotFoundException("Products", HttpStatus.NOT_FOUND);
        }
        log.info("ProductController::getProducts execution ended.");
        return new ResponseEntity<>(apiResponseDTO, HttpStatus.OK);
    }

    @GetMapping("/id/{productId}")
    public ResponseEntity<?> getProductById(@PathVariable (value = "productId") String productId) {

            ProductResponseDTO productResponseDTO = productService.getProductById(productId);
            APIResponseDTO<ProductResponseDTO> responseDTO;

        try {
            log.info("ProductController::getProduct by id  {}", productId);
            responseDTO = APIResponseDTO
                    .<ProductResponseDTO>builder()
                    .statusCode(SUCCESS)
                    .result(productResponseDTO)
                    .build();

            log.info("ProductController::getProduct by id  {} response {}", productId, ProductMapper
                    .jsonAsString(productResponseDTO));

            log.info("ProductController::getProduct by id execution ended.");
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        } catch (Exception e) {
            log.error("ProductController::product not found : {}", productId);
            throw new ProductResourceNotFoundException("Product", "productId", productId);
        }
    }

    @GetMapping("/name/{productName}")
    public ResponseEntity<APIResponseDTO> getProductsByName (@PathVariable(value = "productName") String productName) {

            List<ProductResponseDTO> productResponseDTO = productService.getProductByName(productName);
            APIResponseDTO<List<ProductResponseDTO>> apiResponseDTO=null;

        try {
            log.info("ProductController::getProduct by id  {}", productName);
            apiResponseDTO = APIResponseDTO
                    .<List<ProductResponseDTO>>builder()
                    .statusCode(SUCCESS)
                    .result(productResponseDTO)
                    .build();

            log.info("ProductController::getProduct by id  {} response {}", productName, ProductMapper
                    .jsonAsString(productResponseDTO));

            log.info("ProductController::getProduct by id execution ended.");

        } catch (Exception e) {
            log.error("ProductController::product not found : {}", productName);
            throw new ProductResourceNotFoundException("Product", "productName", productName);
        }
        return new ResponseEntity<>(apiResponseDTO,HttpStatus.OK);
    }

    @GetMapping("/types")
    public ResponseEntity<APIResponseDTO> getProductsGroupByType() {

        Map<String, List<ProductResponseDTO>> products = productService.getListOfProductsWithTypes();
        APIResponseDTO<Map<String, List<ProductResponseDTO>>> responseDTO = null;

        try {
            log.info("ProductController::getProductsGroupByType execution started.");
            responseDTO = APIResponseDTO
                    .<Map<String, List<ProductResponseDTO>>>builder()
                    .statusCode(SUCCESS)
                    .result(products)
                    .build();

            log.info("ProductController::getProductsGroupByType by types  {}", ProductMapper.jsonAsString(responseDTO));
        } catch (Exception e) {
            log.error("ProductController::Products not found : {}", responseDTO.getResult());
            throw new ProductResourceNotFoundException("Products", HttpStatus.NOT_FOUND);
        }
        log.info("ProductController::getProductsGroupByType execution ended.");
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PutMapping("/update/{productId}")
    public ResponseEntity<APIResponseDTO> updateProductById (@PathVariable(value = "productId") String productId, @RequestBody ProductRequestDTO productRequestDTO) {

        APIResponseDTO responseDTO = null;

        try {
            log.info("ProductController::updateProductById execution started.");
            ProductResponseDTO updateProductRequest = productService.updateProductInformation(productId, productRequestDTO);

            responseDTO = APIResponseDTO
                    .<ProductResponseDTO>builder()
                    .statusCode(SUCCESS)
                    .result(updateProductRequest)
                    .build();
            log.info("ProductController::updateProductById by id  {} response {}", productId, ProductMapper
                    .jsonAsString(updateProductRequest));
        }catch (Exception e) {
            log.error("ProductController::product not found : {}", productId);
            throw new ProductResourceNotFoundException("Product", "productId", productId);
        }
        log.info("ProductController::updateProductById execution ended.");
        return new ResponseEntity<>(responseDTO,HttpStatus.CREATED);
    }

    @GetMapping("/external/id/{productId}")
    public ResponseEntity<ProductResponseDTO> externalMethod(@PathVariable (value = "productId") String productId) {

        try {
            log.info("ProductController::getProduct by id  {}", productId);
            ProductResponseDTO productResponseDTO = productService.getProductById(productId);

            log.info("ProductController::getProduct by id  {} response {}", productId, ProductMapper
                    .jsonAsString(productResponseDTO));

            log.info("ProductController::getProduct by id execution ended.");
            return new ResponseEntity<>(productResponseDTO, HttpStatus.OK);
        } catch (Exception e) {
            log.error("ProductController::product not found : {}", productId);
            throw new ProductResourceNotFoundException("Product", "productId", productId);
        }
    }
}
