package com.example.homestay.dto.request;

import com.example.homestay.utils.type.PhoneType;
import com.example.homestay.utils.validator.PhoneNumber;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthRegisterRequest {

    private String firstName;
    private String lastName;

    @NotBlank(message = "USERNAME_BLANK")
    private String username;

    @NotBlank(message = "PASSWORD_BLANK")
    private String password;

    @NotBlank(message = "EMAIL_BLANK")
    @Email(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = "EMAIL_INVALID")
    private String email;

    @NotBlank(message = "PHONE_BLANK")
    @PhoneNumber(country = PhoneType.VIETNAMESE, message = "PHONE_INVALID")
    private String phone;

    private String address;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dOb;


}

