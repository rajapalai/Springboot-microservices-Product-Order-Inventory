package com.orderMicroservice.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderResponseDTO {
    private String orderId;
    private String orderNumber;
    private String productId;
    private List<OrderLineItemsDTO> orderLineItems;
    private AddressDTO deliveryAddress;



}
