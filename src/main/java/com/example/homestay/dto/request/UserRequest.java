package com.example.homestay.dto.request;

import com.example.homestay.utils.type.PhoneType;
import com.example.homestay.utils.validator.PhoneNumber;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

    private String firstName;

    private String lastName;

    @NotBlank(message = "USER_BLANK")
    private String username;

    @NotBlank(message = "PASSWORD_BLANK")
//    @PasswordConstraint(message = PASSWORD_INVALID)
    private String password;

    @NotBlank(message = "EMAIL_BLANK")
    @Email(regexp = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", message = "EMAIL_INVALID")
    private String email;

    @NotBlank(message = "PHONE_BLANK")
    @PhoneNumber(country = PhoneType.VIETNAMESE, message = "PHONE_INVALID")
    private String phoneNumber;

    @NotBlank
    private String address;

    @NotNull(message = "DOB_BLANK")
//    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dOb;

}
