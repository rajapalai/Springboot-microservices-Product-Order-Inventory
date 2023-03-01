package com.productMicroservice.model;

import com.productMicroservice.customAnnotation.ProductType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Product")
public class Product implements Serializable {

    @Id
    private String productId;
    @Field("product-name")
    private String productName;
    @Field("description")
    private String productDescription;
    @Field("amount")
    private BigDecimal productAmount;
    @Field("type")
    private String productType;
    @Field("supplier")
    private String productSupplierName;
    @Field("supplier-code")
    private String productSupplierCode;

}
