package com.example.homestay.mapper;

import com.example.homestay.dto.reponse.UserResponse;
import com.example.homestay.dto.request.AuthRegisterRequest;
import com.example.homestay.dto.request.UserRequest;
import com.example.homestay.dto.request.UserUpdateRequest;
import com.example.homestay.enums.UserStatus;
import com.example.homestay.model.Role;
import com.example.homestay.model.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "status", source = "status", qualifiedByName = "mapUserStatus")
    @Mapping(target = "role", source = "role", qualifiedByName = "mapRole")
    User toUser(UserRequest userRequest);

    @Mapping(target = "status", expression = "java(mapStatus(user.getStatus()))")
    @Mapping(target = "role", source = "role.id")
    UserResponse toUserResponse(User user);

    @Mapping(target = "status", source = "status", qualifiedByName = "mapUserStatus")
    @Mapping(target = "role", source = "role", qualifiedByName = "mapRole")
    void updateUser(UserUpdateRequest request, @MappingTarget User user);

    User toUserFromRegis(AuthRegisterRequest request);

    default Integer mapStatus(UserStatus status) {
        return status != null ? status.ordinal() : null;
    }

    @Named("mapUserStatus")
    default UserStatus mapUserStatus(Integer value) {
        if (value == null) {
            return null;
        }
        return UserStatus.values()[value];
    }

    @Named("mapRole")
    default Role mapRole(Integer value) {
        if (value == null) {
            return null;
        }
        Role role = new Role();
        role.setId(value);
        return role;
    }
}
