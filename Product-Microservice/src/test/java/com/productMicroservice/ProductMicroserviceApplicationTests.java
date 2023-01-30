package com.productMicroservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.productMicroservice.DAO.ProductDao;
import com.productMicroservice.DTO.InventoryDTO;
import com.productMicroservice.DTO.ProductRequestDTO;
import com.productMicroservice.DTO.ProductResponseDTO;
import com.productMicroservice.controller.ProductController;
import com.productMicroservice.mapperUtil.ProductMapper;
import com.productMicroservice.model.Product;
import com.productMicroservice.service.ProductService;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.NoMoreInteractions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
class ProductMicroserviceApplicationTests {

    private static final String URL = "http://localhost:9001/product/v1/app";

    @InjectMocks
    private ProductController productController;
    @Mock
    private ProductService productService;
    @MockBean
    private ProductDao productDao;
    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.productController).build();
    }

    @Test
    @Order(1)
    public void test_addNewProduct() throws Exception {

        Product product = new Product("d3a3811b",
                "Samsung",
                "S20 Ultra",
                BigDecimal.valueOf(110000),
                "Mobiles & Tablets",
                "Salenet",
                "cd1824e9");

        ProductRequestDTO productRequestDTO = new ProductRequestDTO(
                "Samsung",
                "S20 Ultra",
                BigDecimal.valueOf(110000),
                "Mobiles & Tablets",
                "Salenet");

        Mockito.when(productDao.save(ArgumentMatchers.any(Product.class))).thenReturn(product);
        mockMvc.perform(MockMvcRequestBuilders
                        .post(URL)
                        .content(convertObjectAsString(productRequestDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.productId").value("d3a3811b"));
        Mockito.verify(productDao, Mockito.times(1)).save(ArgumentMatchers.any(Product.class));
        Mockito.verifyNoMoreInteractions(productDao);
    }

    @Test
    @Order(2)
    public void test_getProducts() throws Exception {
        List<Product> products = Arrays.asList(
                new Product("63c8c5de2461106d9019da33",
                        "Samsung",
                        "S20 Ultra",
                        BigDecimal.valueOf(110000),
                        "Mobiles & Tablets",
                        "Salenet",
                        "cd1824e9"),
                new Product("63c8dd390040ee0d6bbf5777",
                        "Hawkins",
                        "Hawkins Contura Black 3 L Pressure Cooker  (Hard Anodized)",
                        BigDecimal.valueOf(5000),
                        "Home & Furniture",
                        "MYTHANGLORYRetail",
                        "bd1cabb2"));

        Mockito.when(productDao.findAll()).thenReturn(products);
        mockMvc.perform(MockMvcRequestBuilders.get(URL)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("*").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.[0].productId").value("63c8c5de2461106d9019da33"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.[1].productId").value("63c8dd390040ee0d6bbf5777"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*]", Matchers.hasSize(2)));

    }

    @Test
    @Order(3)
    public void test_getProductById() throws Exception {

        Product product = new Product("d3a3811b",
                "Samsung",
                "S20 Ultra",
                BigDecimal.valueOf(110000),
                "Mobiles & Tablets",
                "Salenet",
                "cd1824e9");

        Mockito.when(productDao.findById("d3a3811b")).thenReturn(Optional.ofNullable(product));
        mockMvc.perform(MockMvcRequestBuilders
                        .get(URL + "/id/{productId}", "d3a3811b")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("*").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.productId").value("d3a3811b"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*]", Matchers.hasSize(2)));
        Mockito.verify(productDao, Mockito.times(1)).findById("d3a3811b");
        Mockito.verifyNoMoreInteractions(productDao);
    }

    @Test
    @Order(4)
    public void test_getProductsByName() throws Exception {
        List<Product> products = Arrays.asList(
                new Product("63c8c4a72461106d9019da30",
                        "Apple iPhone",
                        "iPhone 13 pro max",
                        BigDecimal.valueOf(180000),
                        "Mobiles & Tablets",
                        "iDestiny",
                        "70f0bdfb"),
                new Product("63c8c4bb2461106d9019da31",
                        "Apple iPhone",
                        "iPhone 14 pro max",
                        BigDecimal.valueOf(130000),
                        "Mobiles & Tablets",
                        "iDestiny",
                        "cfd42e9c"));

        Mockito.when(productDao.findByName("Apple iPhone")).thenReturn(products);
        mockMvc.perform(MockMvcRequestBuilders
                        .get(URL + "/name/{productName}", "Apple iPhone")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("*").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.[0].productName").value("Apple iPhone"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.[1].productName").value("Apple iPhone"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*]", Matchers.hasSize(2)));
        Mockito.verify(productDao, Mockito.times(1)).findByName("Apple iPhone");
        Mockito.verifyNoMoreInteractions(productDao);
    }

    @Test
    @Order(5)
    public void test_updateProductById() throws Exception {
        ProductRequestDTO productRequestDTO = new ProductRequestDTO(
                "Apple iPhone",
                "iPhone 14 pro max",
                BigDecimal.valueOf(180000),
                "Mobiles & Tablets",
                "iDestiny");
        ProductResponseDTO productResponseDTO = new ProductResponseDTO("d3a3811b",
                "Apple iPhone",
                "iPhone 14 pro max",
                BigDecimal.valueOf(130000),
                "Mobiles & Tablets",
                "iDestiny",
                "cfd42e9c",
                new InventoryDTO("f7db","d3a3811b",10,"in stock"));
        Product productUpdate = new Product("d3a3811b",
                "Apple iPhone",
                "iPhone 14 pro max",
                BigDecimal.valueOf(130000),
                "Mobiles & Tablets",
                "iDestiny",
                "cfd42e9c");

        Mockito.when(productDao.findById("d3a3811b")).thenReturn(Optional.of(productUpdate));
        Mockito.when(productDao.save(ArgumentMatchers.any(Product.class))).thenReturn(productUpdate);
        mockMvc.perform(MockMvcRequestBuilders
                        .put(URL + "/update/{productId}", "d3a3811b")
                        .content(convertObjectAsString(productRequestDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("*").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.productId").value("d3a3811b"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*]", Matchers.hasSize(2)));
    }

    private String convertObjectAsString(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException exception) {
            exception.printStackTrace();
        }
        return null;
    }

}
