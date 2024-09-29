package com.example.homestay.dto.reponse;

import com.example.homestay.model.Images;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class HomestayResponse {

    private Integer id;
    private String name;
    private String description;
    private Integer status;
    private List<Images> images;
    private Integer bathrooms;
    private Integer bedrooms;
    private String district;
    private String address;
    private Integer price;
    private Integer guests;
    private Double rating;
    private Integer reviewCount;
}
