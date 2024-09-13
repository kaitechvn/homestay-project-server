package com.example.homestay.mapper;

import com.example.homestay.dto.reponse.UserResponse;
import com.example.homestay.dto.request.UserRequest;
import com.example.homestay.dto.request.UserUpdateRequest;
import com.example.homestay.model.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toUser(UserRequest userRequest);

    UserResponse toUserResponse(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUser(UserUpdateRequest request, @MappingTarget User user);


}
