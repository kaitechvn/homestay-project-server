package com.example.homestay.utils.validator;

import com.example.homestay.utils.type.PhoneType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class PhoneValidator implements ConstraintValidator<PhoneNumber, String> {

    private PhoneType phoneType;

    private static final Pattern VIETNAM_PHONE_REGEX = Pattern.compile("^(\\+84|0)(3[2-9]|5[6|8|9]|7[0|6|7|8|9]|8[1-5]|9[0-9])[0-9]{7}$");
    private static final Pattern USA_PHONE_REGEX = Pattern.compile("^(\\+1)?[2-9][0-9]{2}[2-9][0-9]{2}[0-9]{4}$");

    @Override
    public void initialize(PhoneNumber constraintAnnotation) {
        this.phoneType = constraintAnnotation.country();
    }

    @Override
    public boolean isValid(String number, ConstraintValidatorContext context) {
        if (number == null) {
            return false;
        }
        return switch (phoneType) {
            case VIETNAMESE -> VIETNAM_PHONE_REGEX.matcher(number).matches();
            case USA -> USA_PHONE_REGEX.matcher(number).matches();
        };
    }
}
