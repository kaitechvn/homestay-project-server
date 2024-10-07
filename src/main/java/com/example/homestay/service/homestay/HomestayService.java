package com.example.homestay.service.homestay;

import com.example.homestay.dto.reponse.HomestayResponse;
import com.example.homestay.dto.reponse.PagingResponse;
import com.example.homestay.dto.request.HomestayRequest;
import com.example.homestay.dto.request.HomestayUpdateRequest;
import com.example.homestay.dto.request.PagingRequest;
import com.example.homestay.model.Homestay;

import java.time.LocalDate;
import java.util.List;


public interface HomestayService {

    PagingResponse<HomestayResponse> list(PagingRequest pageRequest);

    HomestayResponse get(Integer id);


    HomestayResponse create(HomestayRequest homestayRequest);

    void update(HomestayUpdateRequest updateRequest, Integer id);

    void delete(Integer id);

    PagingResponse<HomestayResponse> filter(
                                        PagingRequest pageRequest,
                                        Integer districtId,
                                        Integer minPrice,
                                        Integer maxPrice,
                                        Integer guests,
                                        LocalDate checkIn,
                                        LocalDate checkOut
    );

    List<HomestayResponse> getTopRatedHomestays();

}




