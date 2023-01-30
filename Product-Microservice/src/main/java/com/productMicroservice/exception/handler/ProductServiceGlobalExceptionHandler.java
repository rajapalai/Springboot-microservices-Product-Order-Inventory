package com.productMicroservice.exception.handler;

import com.productMicroservice.DTO.APIResponseDTO;
import com.productMicroservice.DTO.ErrorDTO;
import com.productMicroservice.exception.ErrorDetails;
import com.productMicroservice.exception.ProductResourceNotFoundException;
import com.productMicroservice.exception.ProductServiceBusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@RestControllerAdvice
public class ProductServiceGlobalExceptionHandler {

    public static final String FAILURE = "Failure";

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public APIResponseDTO<?> handleMethodArgumentExceptionApiResponse(MethodArgumentNotValidException exception) {
        APIResponseDTO<?> apiResponseDTO = new APIResponseDTO<>();
        List<ErrorDTO> listErrors = new ArrayList<>();
        exception.getBindingResult().getFieldErrors()
                .forEach(error -> {
                    ErrorDTO errorDTO = new ErrorDTO(error.getField(), error.getDefaultMessage());
                    listErrors.add(errorDTO);
                });
        apiResponseDTO.setStatusCode(FAILURE);
        apiResponseDTO.setErrors(listErrors);

        return apiResponseDTO;
    }

    @ExceptionHandler(ProductServiceBusinessException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public APIResponseDTO<?> handleServiceException(ProductServiceBusinessException exception) {
        APIResponseDTO<?> apiResponseDTO = new APIResponseDTO<>();
        apiResponseDTO.setStatusCode(FAILURE);
        apiResponseDTO.setErrors(Collections.singletonList(new ErrorDTO("", exception.getMessage())));
        return apiResponseDTO;
    }

    @ExceptionHandler(ProductResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorDetails> handleResourceNotFound(ProductResourceNotFoundException exception, WebRequest webRequest) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
}