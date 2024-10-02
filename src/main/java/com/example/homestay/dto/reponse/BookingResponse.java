package com.example.homestay.dto.reponse;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class BookingResponse {

    private Integer bookingId;
    private String contactName;
    private String contactPhone;
    private String contactEmail;
    private LocalDate checkinDate;
    private LocalDate checkoutDate;
    private Integer guests;
    private String status;
    private Integer totalAmount;
}
