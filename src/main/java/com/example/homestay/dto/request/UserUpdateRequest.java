package com.example.homestay.dto.request;

import com.example.homestay.utils.type.PhoneType;
import com.example.homestay.utils.validator.PhoneNumber;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateRequest {

    private String firstName;
    private String lastName;

    @Email(regexp = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", message = "{error.user.email.invalid}")
    private String email;

    @PhoneNumber(country = PhoneType.VIETNAMESE, message = "{error.user.phone.invalid}" )
    private String phoneNumber;

    private String address;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dOb;
}
