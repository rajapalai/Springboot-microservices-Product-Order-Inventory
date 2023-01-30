package com.inventoryMicroservice.mapperUtil;

import com.inventoryMicroservice.DTO.InventoryRequestDto;
import com.inventoryMicroservice.DTO.InventoryResponseDto;
import com.inventoryMicroservice.model.Inventory;

import java.util.List;
import java.util.UUID;

public class InventoryMapper {

    public static Inventory convertToEntity (InventoryRequestDto inventoryRequestDto) {
        Inventory inventory = new Inventory();
        inventory.setInventoryId(UUID.randomUUID().toString().split("-")[1]);
        inventory.setProductId(inventoryRequestDto.getProductId());
        inventory.setQuantity(inventoryRequestDto.getQuantity());
        return inventory;
    }

    public static InventoryResponseDto convertToDto (Inventory inventory) {
        InventoryResponseDto inventoryResponseDto = new InventoryResponseDto();
        inventoryResponseDto.setInventoryId(inventory.getInventoryId());
        inventoryResponseDto.setProductId(inventory.getProductId());
        inventoryResponseDto.setQuantity(inventory.getQuantity());
        inventoryResponseDto.setInStock(inventory.getQuantity()>0);
        return inventoryResponseDto;
    }
}
