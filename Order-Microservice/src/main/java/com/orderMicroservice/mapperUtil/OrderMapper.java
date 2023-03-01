package com.orderMicroservice.mapperUtil;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.orderMicroservice.DTO.OrderRequestDTO;
import com.orderMicroservice.DTO.OrderResponseDTO;
import com.orderMicroservice.model.Order;
import java.math.BigInteger;
import java.util.UUID;

public class OrderMapper {

   private static String generateUUIDNo = String.format("%010d",new BigInteger(UUID.randomUUID().toString().replace("-",""),16));
   private static String randomOrderNumber = generateUUIDNo.substring( generateUUIDNo.length() - 10);

    public static Order convertToEntity (OrderRequestDTO orderRequestDTO) {
        Order order = new Order();
        order.setOrderId(UUID.randomUUID().toString().split("-")[4]);
        order.setOrderNumber(randomOrderNumber);
        order.setOrderLineItems(orderRequestDTO.getOrderLineItems());
        order.setDeliveryAddress(orderRequestDTO.getDeliveryAddress());
        return order;
    }

    public static OrderResponseDTO convertToDto (Order order) {
        OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
        orderResponseDTO.setOrderId(order.getOrderId());
        orderResponseDTO.setOrderNumber(order.getOrderNumber());
        orderResponseDTO.setOrderLineItems(order.getOrderLineItems());
        orderResponseDTO.setDeliveryAddress(order.getDeliveryAddress());
        return orderResponseDTO;
    }

    public static String jsonAsString(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
