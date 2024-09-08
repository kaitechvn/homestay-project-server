package com.example.vmacademy.mapper;

import com.example.vmacademy.dto.reponse.UserResponse;
import com.example.vmacademy.dto.request.UserRequest;
import com.example.vmacademy.dto.request.UserUpdateRequest;
import com.example.vmacademy.model.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toUser(UserRequest userRequest);

    UserResponse toUserResponse(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdateUser(UserUpdateRequest request, @MappingTarget User user);

    void fullUpdateUser(UserUpdateRequest request, @MappingTarget User user);


}
