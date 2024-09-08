package com.example.vmacademy.dto.request;

import com.example.vmacademy.utils.type.PhoneType;
import com.example.vmacademy.utils.validator.PasswordConstraint;
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

import static com.example.vmacademy.utils.Constants.ErrorCode.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank(message = USER_BLANK)
    private String username;

    @NotBlank(message = PASSWORD_BLANK)
    @PasswordConstraint(message = PASSWORD_INVALID)
    private String password;

    @NotBlank(message = EMAIL_BLANK)
    @Email(regexp = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", message = EMAIL_INVALID)
    private String email;

    @NotBlank(message = PHONE_BLANK)
    @PhoneNumber(country = PhoneType.VIETNAMESE, message = PHONE_INVALID)
    private String phoneNumber;

    @NotBlank
    private String address;

    @NotNull(message = DOB_BLANK)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dOb;

}
