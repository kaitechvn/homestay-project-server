package com.example.homestay.mapper;

import com.example.homestay.dto.reponse.ReviewResponse;
import com.example.homestay.dto.request.ReviewRequest;
import com.example.homestay.model.Homestay;
import com.example.homestay.model.Review;
import com.example.homestay.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "user", source = "user")
    @Mapping(target = "homestay", source = "homestay")
    @Mapping(target = "rating", source = "reviewRequest.rating")
    Review toEntity(ReviewRequest reviewRequest, User user, Homestay homestay);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.username", target = "username")
    @Mapping(source = "homestay.id", target = "homestayId")
    @Mapping(source = "homestay.name", target = "homestayName")
    ReviewResponse toResponse(Review review);
}
