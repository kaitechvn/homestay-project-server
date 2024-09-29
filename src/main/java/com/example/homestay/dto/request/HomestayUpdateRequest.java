package com.example.homestay.dto.request;

import lombok.*;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HomestayUpdateRequest {

    private String name;
    private String description;
    private Integer status;
    private Integer districtId;
    private Integer guest;
    private Double rating;
    private Integer reviewCount;

}
