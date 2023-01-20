package com.productMicroservice.customAnnotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = ProductTypeValidator.class)
public @interface ProductType {

    String message() default "Please input product type -> Mobiles & Tablets/Electronics/TVs & Appliances/Fashion/Beauty/Home & Furniture/Grocery";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
