package com.example.homestay.dto.reponse;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PagingResponse <T> {

    private Integer currentPage;
    private Integer pageSize;
    private Integer totalPages;
    private Long totalElements;
    private List<T> data = new ArrayList<>();

    public static <T> PagingResponse<T> from(Integer page, Integer pageSize, Long totalElements, List<T> data) {
        int totalPages = (int) (totalElements / pageSize);
        if (totalElements % pageSize != 0) {
            totalPages++;
        }
        return PagingResponse.<T>builder()
                .currentPage(page)
                .pageSize(pageSize)
                .totalElements(totalElements)
                .totalPages(totalPages)
                .data(data)
                .build();
    }
}
