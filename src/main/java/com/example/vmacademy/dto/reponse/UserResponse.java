package com.example.vmacademy.dto.reponse;

import lombok.*;
import java.time.LocalDate;

@Getter
@Builder
public class UserResponse {

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String address;
    private LocalDate dateOfBirth;

}
