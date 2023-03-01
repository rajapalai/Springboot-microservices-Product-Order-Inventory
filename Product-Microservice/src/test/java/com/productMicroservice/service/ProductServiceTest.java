package com.productMicroservice.service;

import com.productMicroservice.DAO.ProductDao;
import com.productMicroservice.DTO.InventoryDTO;
import com.productMicroservice.DTO.ProductRequestDTO;
import com.productMicroservice.DTO.ProductResponseDTO;
import com.productMicroservice.clientConfigServices.ApiClient;
import com.productMicroservice.exception.ProductServiceBusinessException;
import com.productMicroservice.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.OptimisticLockingFailureException;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductDao mockProductDao;
    @Mock
    private ApiClient mockApiClient;

    private ProductService productServiceUnderTest;

    @BeforeEach
    void setUp() {
        productServiceUnderTest = new ProductService(mockProductDao, mockApiClient);
    }

    @Test
    void testCreateNewProduct() {
        // Setup
        final ProductRequestDTO productRequestDTO = new ProductRequestDTO("productName", "productDescription",
                new BigDecimal("0.00"), "productType", "productSupplierName");
        final ProductResponseDTO expectedResult = new ProductResponseDTO("productId", "productName",
                "productDescription", new BigDecimal("0.00"), "productType", "productSupplierName",
                "productSupplierCode", new InventoryDTO("inventoryId", "productId", 0, false));

        // Configure ProductDao.save(...).
        final Product product = new Product("productId", "productName", "productDescription", new BigDecimal("0.00"),
                "productType", "productSupplierName", "productSupplierCode");
        when(mockProductDao.save(
                new Product("productId", "productName", "productDescription", new BigDecimal("0.00"), "productType",
                        "productSupplierName", "productSupplierCode"))).thenReturn(product);

        // Run the test
        final ProductResponseDTO result = productServiceUnderTest.createNewProduct(productRequestDTO);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testCreateNewProduct_ProductDaoThrowsOptimisticLockingFailureException() {
        // Setup
        final ProductRequestDTO productRequestDTO = new ProductRequestDTO("productName", "productDescription",
                new BigDecimal("0.00"), "productType", "productSupplierName");
        when(mockProductDao.save(
                new Product("productId", "productName", "productDescription", new BigDecimal("0.00"), "productType",
                        "productSupplierName", "productSupplierCode")))
                .thenThrow(OptimisticLockingFailureException.class);

        // Run the test
        assertThatThrownBy(() -> productServiceUnderTest.createNewProduct(productRequestDTO))
                .isInstanceOf(ProductServiceBusinessException.class);
    }

    @Test
    void testGetListOfProducts() {
        // Setup
        final List<ProductResponseDTO> expectedResult = List.of(
                new ProductResponseDTO("productId", "productName", "productDescription", new BigDecimal("0.00"),
                        "productType", "productSupplierName", "productSupplierCode",
                        new InventoryDTO("inventoryId", "productId", 0, false)));

        // Configure ProductDao.findAll(...).
        final List<Product> products = List.of(
                new Product("productId", "productName", "productDescription", new BigDecimal("0.00"), "productType",
                        "productSupplierName", "productSupplierCode"));
        when(mockProductDao.findAll()).thenReturn(products);

        // Run the test
        final List<ProductResponseDTO> result = productServiceUnderTest.getListOfProducts();

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetListOfProducts_ProductDaoReturnsNoItems() {
        // Setup
        when(mockProductDao.findAll()).thenReturn(Collections.emptyList());

        // Run the test
        final List<ProductResponseDTO> result = productServiceUnderTest.getListOfProducts();

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    void testGetProductById() {
        // Setup
        final ProductResponseDTO expectedResult = new ProductResponseDTO("productId", "productName",
                "productDescription", new BigDecimal("0.00"), "productType", "productSupplierName",
                "productSupplierCode", new InventoryDTO("inventoryId", "productId", 0, false));

        // Configure ProductDao.findById(...).
        final Optional<Product> product = Optional.of(
                new Product("productId", "productName", "productDescription", new BigDecimal("0.00"), "productType",
                        "productSupplierName", "productSupplierCode"));
        when(mockProductDao.findById("productId")).thenReturn(product);

        // Configure ApiClient.getInventoryByProductID(...).
        final InventoryDTO inventoryDTO = new InventoryDTO("inventoryId", "productId", 0, false);
        when(mockApiClient.getInventoryByProductID("productId")).thenReturn(inventoryDTO);

        // Run the test
        final ProductResponseDTO result = productServiceUnderTest.getProductById("productId");

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetProductById_ProductDaoReturnsAbsent() {
        // Setup
        when(mockProductDao.findById("productId")).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> productServiceUnderTest.getProductById("productId"))
                .isInstanceOf(ProductServiceBusinessException.class);
    }

    @Test
    void testGetProductByName() {
        // Setup
        final List<ProductResponseDTO> expectedResult = List.of(
                new ProductResponseDTO("productId", "productName", "productDescription", new BigDecimal("0.00"),
                        "productType", "productSupplierName", "productSupplierCode",
                        new InventoryDTO("inventoryId", "productId", 0, false)));

        // Configure ProductDao.findByName(...).
        final List<Product> products = List.of(
                new Product("productId", "productName", "productDescription", new BigDecimal("0.00"), "productType",
                        "productSupplierName", "productSupplierCode"));
        when(mockProductDao.findByName("productName")).thenReturn(products);

        // Run the test
        final List<ProductResponseDTO> result = productServiceUnderTest.getProductByName("productName");

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetProductByName_ProductDaoReturnsNoItems() {
        // Setup
        when(mockProductDao.findByName("productName")).thenReturn(Collections.emptyList());

        // Run the test
        final List<ProductResponseDTO> result = productServiceUnderTest.getProductByName("productName");

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    void testRemoveProduct() {
        // Setup
        // Run the test
        productServiceUnderTest.removeProduct("productId");

        // Verify the results
    }

    @Test
    void testUpdateProductInformation() {
        // Setup
        final ProductRequestDTO productRequestDTO = new ProductRequestDTO("productName", "productDescription",
                new BigDecimal("0.00"), "productType", "productSupplierName");
        final ProductResponseDTO expectedResult = new ProductResponseDTO("productId", "productName",
                "productDescription", new BigDecimal("0.00"), "productType", "productSupplierName",
                "productSupplierCode", new InventoryDTO("inventoryId", "productId", 0, false));

        // Configure ProductDao.findById(...).
        final Optional<Product> product = Optional.of(
                new Product("productId", "productName", "productDescription", new BigDecimal("0.00"), "productType",
                        "productSupplierName", "productSupplierCode"));
        when(mockProductDao.findById("productId")).thenReturn(product);

        // Configure ProductDao.save(...).
        final Product product1 = new Product("productId", "productName", "productDescription", new BigDecimal("0.00"),
                "productType", "productSupplierName", "productSupplierCode");
        when(mockProductDao.save(
                new Product("productId", "productName", "productDescription", new BigDecimal("0.00"), "productType",
                        "productSupplierName", "productSupplierCode"))).thenReturn(product1);

        // Run the test
        final ProductResponseDTO result = productServiceUnderTest.updateProductInformation("productId",
                productRequestDTO);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testUpdateProductInformation_ProductDaoFindByIdReturnsAbsent() {
        // Setup
        final ProductRequestDTO productRequestDTO = new ProductRequestDTO("productName", "productDescription",
                new BigDecimal("0.00"), "productType", "productSupplierName");
        when(mockProductDao.findById("productId")).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(
                () -> productServiceUnderTest.updateProductInformation("productId", productRequestDTO))
                .isInstanceOf(ProductServiceBusinessException.class);
    }

    @Test
    void testUpdateProductInformation_ProductDaoSaveThrowsOptimisticLockingFailureException() {
        // Setup
        final ProductRequestDTO productRequestDTO = new ProductRequestDTO("productName", "productDescription",
                new BigDecimal("0.00"), "productType", "productSupplierName");

        // Configure ProductDao.findById(...).
        final Optional<Product> product = Optional.of(
                new Product("productId", "productName", "productDescription", new BigDecimal("0.00"), "productType",
                        "productSupplierName", "productSupplierCode"));
        when(mockProductDao.findById("productId")).thenReturn(product);

        when(mockProductDao.save(
                new Product("productId", "productName", "productDescription", new BigDecimal("0.00"), "productType",
                        "productSupplierName", "productSupplierCode")))
                .thenThrow(OptimisticLockingFailureException.class);

        // Run the test
        assertThatThrownBy(
                () -> productServiceUnderTest.updateProductInformation("productId", productRequestDTO))
                .isInstanceOf(ProductServiceBusinessException.class);
    }

    @Test
    void testGetListOfProductsWithTypes() {
        // Setup
        final Map<String, List<ProductResponseDTO>> expectedResult = Map.ofEntries(Map.entry("value",
                List.of(new ProductResponseDTO("productId", "productName", "productDescription", new BigDecimal("0.00"),
                        "productType", "productSupplierName", "productSupplierCode",
                        new InventoryDTO("inventoryId", "productId", 0, false)))));

        // Configure ProductDao.findAll(...).
        final List<Product> products = List.of(
                new Product("productId", "productName", "productDescription", new BigDecimal("0.00"), "productType",
                        "productSupplierName", "productSupplierCode"));
        when(mockProductDao.findAll()).thenReturn(products);

        // Run the test
        final Map<String, List<ProductResponseDTO>> result = productServiceUnderTest.getListOfProductsWithTypes();

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetListOfProductsWithTypes_ProductDaoReturnsNoItems() {
        // Setup
        final Map<String, List<ProductResponseDTO>> expectedResult = Map.ofEntries(Map.entry("value",
                List.of(new ProductResponseDTO("productId", "productName", "productDescription", new BigDecimal("0.00"),
                        "productType", "productSupplierName", "productSupplierCode",
                        new InventoryDTO("inventoryId", "productId", 0, false)))));
        when(mockProductDao.findAll()).thenReturn(Collections.emptyList());

        // Run the test
        final Map<String, List<ProductResponseDTO>> result = productServiceUnderTest.getListOfProductsWithTypes();

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }
}
