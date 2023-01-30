package com.orderMicroservice.service;

import com.orderMicroservice.DAO.OrderDAO;
import com.orderMicroservice.DTO.OrderRequestDTO;
import com.orderMicroservice.DTO.OrderResponseDTO;
import com.orderMicroservice.config.ApiClient;
import com.orderMicroservice.mapperUtil.OrderMapper;
import com.orderMicroservice.model.Order;
import com.orderMicroservice.serviceInterface.OrderServiceInterface;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class OrderService implements OrderServiceInterface {
    private OrderDAO orderDAO;
    private ApiClient apiClient;


    @Override
    public OrderResponseDTO placeOrder(OrderRequestDTO orderRequestDTO) {
        OrderResponseDTO orderResponseDTO;

        Order order = OrderMapper.convertToEntity(orderRequestDTO);
        Order placeOrder = orderDAO.save(order);

        orderResponseDTO = OrderMapper.convertToDto(placeOrder);

        return orderResponseDTO;
    }

    @Override
    public OrderResponseDTO getInvoiceByOrderNumber(String orderNumber) {
        OrderResponseDTO orderResponseDTO;

        Order order =  orderDAO.findByOrderNumber(orderNumber);

        orderResponseDTO = OrderMapper.convertToDto(order);

        return orderResponseDTO;
    }

}
