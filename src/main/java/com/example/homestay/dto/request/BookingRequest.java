package com.example.homestay.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class BookingRequest {

    private Integer userId;

    @NotNull
    private Integer homestayId;

    @NotNull
    private LocalDate checkinDate;

    @NotNull
    private LocalDate checkoutDate;

    @NotNull
    private Integer guests;

    @NotNull
    private String contactName;

    @NotNull
    private String contactPhone;

    @NotNull
    private String contactEmail;

}


