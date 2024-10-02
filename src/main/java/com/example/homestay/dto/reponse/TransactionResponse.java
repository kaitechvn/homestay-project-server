package com.example.homestay.dto.reponse;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class TransactionResponse {

    private Integer id;
    private String transNo;
    private String method;
    private String status;
    private Integer amount;
    private String billNo;
    private LocalDate createDate;
    private String bookingId;

}