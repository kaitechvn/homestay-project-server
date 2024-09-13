package com.example.homestay.dto.reponse;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class HomestayResponse {

    private String name;
    private String description;
    private String type;
    private Boolean status;
    private List<String> images;
    private Integer guest;
    private Double rating;
    private Integer reviewCount;
}
