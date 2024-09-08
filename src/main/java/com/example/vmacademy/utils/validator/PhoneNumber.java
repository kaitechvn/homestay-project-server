package com.example.vmacademy.utils.validator;

import com.example.vmacademy.utils.type.PhoneType;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Constraint(validatedBy = PhoneValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface PhoneNumber {

    String message() default "phone number invalid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    PhoneType country(); // Parameter to specify phone type
}
