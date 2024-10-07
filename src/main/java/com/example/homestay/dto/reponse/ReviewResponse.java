package com.example.homestay.dto.reponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewResponse {

    private Integer id;
    private Integer userId;
    private String username;
    private Integer homestayId;
    private String homestayName;
    private Integer rating;
    private String comment;
    private ZonedDateTime createdAt;

}
