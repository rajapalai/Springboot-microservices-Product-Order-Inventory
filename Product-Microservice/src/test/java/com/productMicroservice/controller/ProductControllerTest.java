package com.productMicroservice.controller;

import com.productMicroservice.DTO.InventoryDTO;
import com.productMicroservice.DTO.ProductRequestDTO;
import com.productMicroservice.DTO.ProductResponseDTO;
import com.productMicroservice.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService mockProductService;

    @Test
    void testAddNewProduct() throws Exception {
        // Setup
        // Configure ProductService.createNewProduct(...).
        final ProductResponseDTO productResponseDTO = new ProductResponseDTO("productId", "productName",
                "productDescription", new BigDecimal("0.00"), "productType", "productSupplierName",
                "productSupplierCode", new InventoryDTO("inventoryId", "productId", 0, false));
        when(mockProductService.createNewProduct(
                new ProductRequestDTO("productName", "productDescription", new BigDecimal("0.00"), "productType",
                        "productSupplierName"))).thenReturn(productResponseDTO);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/product/v1/app")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testGetProducts() throws Exception {
        // Setup
        // Configure ProductService.getListOfProducts(...).
        final List<ProductResponseDTO> productResponseDTOS = List.of(
                new ProductResponseDTO("productId", "productName", "productDescription", new BigDecimal("0.00"),
                        "productType", "productSupplierName", "productSupplierCode",
                        new InventoryDTO("inventoryId", "productId", 0, false)));
        when(mockProductService.getListOfProducts()).thenReturn(productResponseDTOS);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/product/v1/app")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testGetProducts_ProductServiceReturnsNoItems() throws Exception {
        // Setup
        when(mockProductService.getListOfProducts()).thenReturn(Collections.emptyList());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/product/v1/app")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testGetProductById() throws Exception {
        // Setup
        // Configure ProductService.getProductById(...).
        final ProductResponseDTO productResponseDTO = new ProductResponseDTO("productId", "productName",
                "productDescription", new BigDecimal("0.00"), "productType", "productSupplierName",
                "productSupplierCode", new InventoryDTO("inventoryId", "productId", 0, false));
        when(mockProductService.getProductById("productId")).thenReturn(productResponseDTO);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/product/v1/app/id/{productId}", "productId")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testGetProductsByName() throws Exception {
        // Setup
        // Configure ProductService.getProductByName(...).
        final List<ProductResponseDTO> productResponseDTOS = List.of(
                new ProductResponseDTO("productId", "productName", "productDescription", new BigDecimal("0.00"),
                        "productType", "productSupplierName", "productSupplierCode",
                        new InventoryDTO("inventoryId", "productId", 0, false)));
        when(mockProductService.getProductByName("productId")).thenReturn(productResponseDTOS);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/product/v1/app/name/{productName}", "productId")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testGetProductsByName_ProductServiceReturnsNoItems() throws Exception {
        // Setup
        when(mockProductService.getProductByName("productId")).thenReturn(Collections.emptyList());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/product/v1/app/name/{productName}", "productId")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testGetProductsGroupByType() throws Exception {
        // Setup
        // Configure ProductService.getListOfProductsWithTypes(...).
        final Map<String, List<ProductResponseDTO>> stringListMap = Map.ofEntries(Map.entry("value",
                List.of(new ProductResponseDTO("productId", "productName", "productDescription", new BigDecimal("0.00"),
                        "productType", "productSupplierName", "productSupplierCode",
                        new InventoryDTO("inventoryId", "productId", 0, false)))));
        when(mockProductService.getListOfProductsWithTypes()).thenReturn(stringListMap);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/product/v1/app/types")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testUpdateProductById() throws Exception {
        // Setup
        // Configure ProductService.updateProductInformation(...).
        final ProductResponseDTO productResponseDTO = new ProductResponseDTO("productId", "productName",
                "productDescription", new BigDecimal("0.00"), "productType", "productSupplierName",
                "productSupplierCode", new InventoryDTO("inventoryId", "productId", 0, false));
        when(mockProductService.updateProductInformation("productId",
                new ProductRequestDTO("productName", "productDescription", new BigDecimal("0.00"), "productType",
                        "productSupplierName"))).thenReturn(productResponseDTO);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(put("/product/v1/app/update/{productId}", "productId")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testExternalMethod() throws Exception {
        // Setup
        // Configure ProductService.getProductById(...).
        final ProductResponseDTO productResponseDTO = new ProductResponseDTO("productId", "productName",
                "productDescription", new BigDecimal("0.00"), "productType", "productSupplierName",
                "productSupplierCode", new InventoryDTO("inventoryId", "productId", 0, false));
        when(mockProductService.getProductById("productId")).thenReturn(productResponseDTO);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(
                        get("/product/v1/app/external/id/{productId}", "productId")
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }
}
