package com.example.homestay.service;

import com.example.homestay.dto.reponse.PagingResponse;
import com.example.homestay.dto.reponse.UserResponse;
import com.example.homestay.dto.request.PagingRequest;
import com.example.homestay.dto.request.UserRequest;
import com.example.homestay.dto.request.UserUpdateRequest;

public interface UserService {

    PagingResponse<UserResponse> list(PagingRequest pageRequest);
    UserResponse get(Integer id);
    UserResponse create(UserRequest userRequest);
    void update (UserUpdateRequest updateRequest, Integer id);
    void delete(Integer id);
}
