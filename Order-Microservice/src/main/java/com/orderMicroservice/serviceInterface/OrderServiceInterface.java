package com.orderMicroservice.serviceInterface;

import com.orderMicroservice.DTO.OrderRequestDTO;
import com.orderMicroservice.DTO.OrderResponseDTO;

public interface OrderServiceInterface {

    public OrderResponseDTO placeOrder (OrderRequestDTO orderRequestDTO);
    public OrderResponseDTO getInvoiceByOrderNumber (String orderNumber);
}
