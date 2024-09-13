package com.example.homestay.dto.reponse;

import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class UserResponse {

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String address;
    private LocalDate dateOfBirth;

}
