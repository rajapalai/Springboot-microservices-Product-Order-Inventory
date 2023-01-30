package com.inventoryMicroservice.serviceImpl;

import com.inventoryMicroservice.DTO.InventoryRequestDto;
import com.inventoryMicroservice.DTO.InventoryResponseDto;

import java.util.List;

public interface InventoryServiceInterface {

    public InventoryResponseDto createNewInventoryResponse (InventoryRequestDto inventoryRequestDto);
    public InventoryResponseDto getInventoryById (String inventoryId);
    public InventoryResponseDto getInventoryByProductId (String productId);
//    public List<InventoryResponseDto> productIsInStockOrNot (String productId);
}
