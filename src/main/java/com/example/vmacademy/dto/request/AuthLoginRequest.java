package com.example.vmacademy.dto.request;

import com.example.vmacademy.utils.validator.PasswordConstraint;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.example.vmacademy.constant.ErrorCode.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthLoginRequest {

    @NotBlank(message = USER_BLANK)
    private String username;

    @NotBlank(message = PASSWORD_BLANK)
    @PasswordConstraint(message = PASSWORD_INVALID)
    private String password;
}
