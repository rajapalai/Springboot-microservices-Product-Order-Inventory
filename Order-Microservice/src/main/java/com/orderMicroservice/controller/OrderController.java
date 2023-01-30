package com.orderMicroservice.controller;

import com.orderMicroservice.DTO.APIResponseDTO;
import com.orderMicroservice.DTO.OrderRequestDTO;
import com.orderMicroservice.DTO.OrderResponseDTO;
import com.orderMicroservice.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order/v1/app")
@AllArgsConstructor
public class OrderController {

    private final static String SUCCESS = "Success";
    private OrderService orderService;

    @PostMapping("/placeOrder")
    public ResponseEntity<APIResponseDTO> placeTheNewOrder (@RequestBody OrderRequestDTO orderRequestDTO) {
        OrderResponseDTO orderResponseDTO = orderService.placeOrder(orderRequestDTO);

        APIResponseDTO<OrderResponseDTO> apiResponseDTO = null;
        try {
            apiResponseDTO = APIResponseDTO
                    .<OrderResponseDTO>builder()
                    .statusCode(SUCCESS)
                    .result(orderResponseDTO)
                    .build();
        }catch (Exception e) {
            throw new RuntimeException("Controller error" + e.getMessage());
        }
        return new ResponseEntity<>(apiResponseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{orderNumber}")
    public ResponseEntity<APIResponseDTO> getOrderInvoice (@PathVariable(value = "orderNumber")  String orderNumber) {
        OrderResponseDTO orderResponseDTO = orderService.getInvoiceByOrderNumber(orderNumber);

        APIResponseDTO<OrderResponseDTO> apiResponseDTO;
        try {
            apiResponseDTO = APIResponseDTO
                    .<OrderResponseDTO>builder()
                    .statusCode(SUCCESS)
                    .result(orderResponseDTO)
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("Controller error" + e.getMessage());
        }
        return new ResponseEntity<>(apiResponseDTO,HttpStatus.OK);
    }
}
