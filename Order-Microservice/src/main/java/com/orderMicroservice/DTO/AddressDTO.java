package com.orderMicroservice.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO {
    private String firstName;
    private String lastName;
    private String completeAddress;
    private String landmark;
    private String pinCode;
    private String contactNumber;
}
