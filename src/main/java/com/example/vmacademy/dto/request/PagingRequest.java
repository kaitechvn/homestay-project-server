package com.example.vmacademy.dto.request;

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
