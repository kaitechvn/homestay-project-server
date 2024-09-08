package com.example.vmacademy.dto.request;

import com.example.vmacademy.utils.type.PhoneType;
import com.example.vmacademy.utils.validator.PhoneNumber;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class AuthRegisterRequest {

    private String firstName;

    private String lastName;

    private String username;

    @NotBlank(message = "Siuu")
    private String password;

    @NotBlank(message = "{error.user.email.blank}")
    @Email(regexp = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", message = "{error.user.email.invalid}")
    private String email;

    @PhoneNumber(country = PhoneType.VIETNAMESE, message = "{error.user.phone.invalid}")
    private String phoneNumber;

    private String address;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dOb;
}

