package com.example.homestay.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class HomestayRequest {

    @NotBlank(message = "HOMESTAY_BLANK")
    private String name;

    private String description;

    @NotNull(message = "Type is required")
    private String type;

    @NotNull(message = "Status is required")
    private Boolean status;

    @NotNull(message = "GuestS store is required")
    private Integer guests;

    private List<String> images;

}
