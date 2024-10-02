package com.example.homestay.dto.reponse;

import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class UserResponse {

    private Integer id;
    private String username;
    private String fullname;
    private String email;
    private String phone;
    private String address;
    private LocalDate dob;
    private Integer status;
    private Integer role;

}
