package com.inventoryMicroservice.DTO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InventoryResponseDto {

    private String inventoryId;
    private String productId;
    private Integer quantity;
    private boolean isInStock;
}
