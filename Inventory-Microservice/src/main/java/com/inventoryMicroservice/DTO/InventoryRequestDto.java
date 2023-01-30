package com.inventoryMicroservice.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class InventoryRequestDto {

    private String inventoryId;
    private String productId;
    private Integer quantity;
}
