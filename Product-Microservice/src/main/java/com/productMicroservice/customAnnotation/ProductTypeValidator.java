package com.productMicroservice.customAnnotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.List;

public class ProductTypeValidator implements ConstraintValidator<ProductType, String> {
    @Override
    public boolean isValid(String productType, ConstraintValidatorContext constraintValidatorContext) {
        List list = Arrays.asList("Mobiles & Tablets","Electronics","TVs & Appliances","Fashion","Beauty","Home & Furniture","Grocery");
        return list.contains(productType);
    }
}
