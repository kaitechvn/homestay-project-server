package com.example.homestay.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PagingRequest {

    private Integer page;
    private Integer size;

}
