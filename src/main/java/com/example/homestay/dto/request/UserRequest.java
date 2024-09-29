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

    private String fullname;

    @NotBlank(message = "USERNAME_BLANK")
    private String username;

    @NotBlank(message = "PASSWORD_BLANK")
//    @PasswordConstraint(message = PASSWORD_INVALID)
    private String password;

    @NotBlank(message = "EMAIL_BLANK")
    @Email(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = "EMAIL_INVALID")
    private String email;

    private String address;

    @PhoneNumber(country = PhoneType.VIETNAMESE, message = "PHONE_INVALID")
    private String phone;

    @NotNull(message = "DOB_BLANK")
    private LocalDate dob;

    private Integer status;
    private Integer role;

}
