package com.example.homestay.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReviewRequest {

    private Integer bookingId;
    private String comment;
    private Integer rating;

}
