package com.inventoryMicroservice.service;

import com.inventoryMicroservice.DAO.InventoryDAO;
import com.inventoryMicroservice.DTO.InventoryRequestDto;
import com.inventoryMicroservice.DTO.InventoryResponseDto;
import com.inventoryMicroservice.mapperUtil.InventoryMapper;
import com.inventoryMicroservice.model.Inventory;
import com.inventoryMicroservice.serviceImpl.InventoryServiceInterface;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class InventoryService implements InventoryServiceInterface {

    private InventoryDAO inventoryDAO;

    @Override
    public InventoryResponseDto createNewInventoryResponse(InventoryRequestDto inventoryRequestDto) {
        InventoryResponseDto inventoryResponseDto;

        Inventory inventory = InventoryMapper.convertToEntity(inventoryRequestDto);

        Inventory saveInventory = inventoryDAO.save(inventory);

        inventoryResponseDto = InventoryMapper.convertToDto(saveInventory);
        return inventoryResponseDto;
    }

    @Override
    public InventoryResponseDto getInventoryById(String inventoryId) {

        InventoryResponseDto inventoryResponseDto;

        Inventory inventory = inventoryDAO.findById(inventoryId).get();
//                .orElseThrow(() -> new RuntimeException("Product not found in inventory service"));

        inventoryResponseDto = InventoryMapper.convertToDto(inventory);
        return inventoryResponseDto;
    }

    @Override
    public InventoryResponseDto getInventoryByProductId(String productId) {
        InventoryResponseDto inventoryResponseDto;

        Inventory inventory = inventoryDAO.findByProductId(productId);
        inventoryResponseDto = InventoryMapper.convertToDto(inventory);
        return inventoryResponseDto;
    }

//    @Override
//    @Transactional(readOnly = true)
//    public List<InventoryResponseDto> productIsInStockOrNot(String productId) {
//        List<InventoryResponseDto> inventoryResponseDto;
//        List<Inventory> inventoryList = inventoryDAO.findBycheckStock(productId);
//
//            inventoryResponseDto = inventoryList.stream()
//                    .map(InventoryMapper::convertToDto)
//                    .collect(Collectors.toList());
//        return inventoryResponseDto;
//    }
}
