package com.productMicroservice.DTO;

import com.productMicroservice.customAnnotation.ProductType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ProductRequestDTO {

    @NotBlank(message = "ProductName should not be NULL or EMPTY")
    private String productName;
    @NotBlank(message = "ProductDescription should not be NULL or EMPTY")
    private String productDescription;
    @Min(value = 5000, message = "Product amount can not be less than 5000")
    @Max(value = 300000, message = "Product amount can not be more than 300000")
    private BigDecimal productAmount;
    @ProductType
    @NotBlank(message = "ProductType should not be NULL or EMPTY")
    private String productType;
    @NotBlank(message = "ProductSupplierName should not be NULL or EMPTY")
    private String productSupplierName;

}
