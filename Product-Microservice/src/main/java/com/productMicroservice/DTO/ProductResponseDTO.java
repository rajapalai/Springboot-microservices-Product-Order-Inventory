package com.productMicroservice.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductResponseDTO {

    private String productId;
    private String productName;
    private String productDescription;
    private BigDecimal productAmount;
    private String productType;
    private String productSupplierName;
    private String productSupplierCode;
    @Transient
    private InventoryDTO inventory;


}
