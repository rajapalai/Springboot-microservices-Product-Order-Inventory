package com.orderMicroservice.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class APIResponseDTO<T> {
    private String statusCode;
    //    private List<ErrorDTO> errors;
    private T result;
}
