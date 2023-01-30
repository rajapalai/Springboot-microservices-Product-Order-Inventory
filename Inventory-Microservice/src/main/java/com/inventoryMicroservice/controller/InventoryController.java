package com.inventoryMicroservice.controller;

import com.inventoryMicroservice.DAO.InventoryDAO;
import com.inventoryMicroservice.DTO.APIResponseDTO;
import com.inventoryMicroservice.DTO.InventoryRequestDto;
import com.inventoryMicroservice.DTO.InventoryResponseDto;
import com.inventoryMicroservice.service.InventoryService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory/v1/app")
@AllArgsConstructor
public class InventoryController {

    private final static String SUCCESS = "Success";
    private InventoryService inventoryService;

    @PostMapping
    public ResponseEntity<APIResponseDTO> addNewInventory(@RequestBody InventoryRequestDto inventoryRequestDto) {
        InventoryResponseDto inventoryResponseDto = inventoryService.createNewInventoryResponse(inventoryRequestDto);

        APIResponseDTO<InventoryResponseDto> apiResponseDTO = null;
        try {
            apiResponseDTO = APIResponseDTO
                    .<InventoryResponseDto>builder()
                    .statusCode(SUCCESS)
                    .result(inventoryResponseDto)
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("Controller error" + e.getMessage());
        }
        return new ResponseEntity<>(apiResponseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{inventoryId}")
    public ResponseEntity<APIResponseDTO> getInventoryById(@PathVariable(value = "inventoryId") String inventoryId) {
        InventoryResponseDto inventoryResponseDto = inventoryService.getInventoryById(inventoryId);
        APIResponseDTO<InventoryResponseDto> apiResponseDTO;

        try {
            apiResponseDTO = APIResponseDTO
                    .<InventoryResponseDto>builder()
                    .result(inventoryResponseDto)
                    .statusCode(SUCCESS)
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("Controller error" + e.getMessage());
        }
        return new ResponseEntity<>(apiResponseDTO, HttpStatus.OK);
    }

    @GetMapping("/id/{productID}")
    public ResponseEntity<APIResponseDTO> getInventoryByProductID(@PathVariable(value = "productID") String productID) {
        InventoryResponseDto inventoryResponseDto = inventoryService.getInventoryByProductId(productID);
        APIResponseDTO<InventoryResponseDto> apiResponseDTO;

        try {
            apiResponseDTO = APIResponseDTO
                    .<InventoryResponseDto>builder()
                    .result(inventoryResponseDto)
                    .statusCode(SUCCESS)
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("controller error" + e.getMessage());
        }
        return new ResponseEntity<>(apiResponseDTO, HttpStatus.OK);
    }

    @GetMapping("/external/{productID}")
    public ResponseEntity<InventoryResponseDto> getByProductID(@PathVariable(value = "productID") String productID) {
        InventoryResponseDto inventoryResponseDto = inventoryService.getInventoryByProductId(productID);
        return new ResponseEntity<>(inventoryResponseDto, HttpStatus.OK);
    }

//    @GetMapping("/listProduct/{productID}")
//    public ResponseEntity<List<InventoryResponseDto>> IsInStockOrNot (@PathVariable(value = "productID") String productID) {
//        List<InventoryResponseDto> inventoryResponseDto = inventoryService.productIsInStockOrNot(productID);
//        return new ResponseEntity<>(inventoryResponseDto,HttpStatus.OK);
//    }
}
