package com.example.vmacademy.service;

import com.example.vmacademy.dto.reponse.PagingResponse;
import com.example.vmacademy.dto.reponse.UserResponse;
import com.example.vmacademy.dto.request.PagingRequest;
import com.example.vmacademy.dto.request.UserRequest;
import com.example.vmacademy.dto.request.UserUpdateRequest;
import com.example.vmacademy.model.User;

public interface UserService {

    PagingResponse<UserResponse> list(PagingRequest pageRequest);
    UserResponse get(Integer id);
    UserResponse create(UserRequest userRequest);
    void update (UserUpdateRequest updateRequest, Integer id);
    void partialUpdate(UserUpdateRequest updateRequest, Integer id);
    void delete(Integer id);
}
