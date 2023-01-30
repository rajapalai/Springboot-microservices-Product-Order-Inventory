package com.orderMicroservice.model;

import com.orderMicroservice.DTO.AddressDTO;
import com.orderMicroservice.DTO.OrderLineItemsDTO;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Order")

public class Order {
    private String orderId;
    private String orderNumber;
    private List<OrderLineItemsDTO> orderLineItems;
    private AddressDTO deliveryAddress;
}
