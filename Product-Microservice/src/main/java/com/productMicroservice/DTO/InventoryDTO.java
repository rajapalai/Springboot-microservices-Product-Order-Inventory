package com.productMicroservice.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryDTO {

    private String inventoryId;
    private String productId;
    private Integer quantity;
    private boolean isInStock;
}
