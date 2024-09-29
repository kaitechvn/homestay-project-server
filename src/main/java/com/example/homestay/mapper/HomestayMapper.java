package com.example.homestay.mapper;

import com.example.homestay.dto.reponse.HomestayResponse;
import com.example.homestay.dto.request.HomestayRequest;
import com.example.homestay.dto.request.HomestayUpdateRequest;
import com.example.homestay.enums.HomestayStatus;
import com.example.homestay.model.District;
import com.example.homestay.model.Homestay;
import com.example.homestay.model.Images;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface HomestayMapper {

    @Mapping(target = "images", source = "images", qualifiedByName = "mapStringsToImages")
    @Mapping(target = "district", source = "districtId", qualifiedByName = "mapDistrictIdToDistrict")
    @Mapping(target = "status", source = "status", qualifiedByName = "mapHomestayStatus")
    Homestay toHomestay(HomestayRequest request);

    @Mapping(target = "district", source = "district.name")
    @Mapping(target = "status", source = "status", qualifiedByName = "mapHomestayStatusToInteger")
    HomestayResponse toHomestayResponse(Homestay homestay);

    @Mapping(target = "district", source = "districtId", qualifiedByName = "mapDistrictIdToDistrict")
    @Mapping(target = "status", source = "status", qualifiedByName = "mapHomestayStatus")
    void updateHomestay(HomestayUpdateRequest request, @MappingTarget Homestay homestay);

    @Named("mapHomestayStatus")
    default HomestayStatus mapHomestayStatus(Integer value) {
        if (value == null) {
            return null;
        }
        return HomestayStatus.values()[value];
    }

    @Named("mapHomestayStatusToInteger")
    default Integer mapHomestayStatusToInteger(HomestayStatus status) {
        if (status == null) {
            return null;
        }
        return status.ordinal();
    }

    @Named("mapImagesToStrings")
    default List<String> mapImagesToStrings(List<Images> images) {
        if (images == null) {
            return null;
        }
        return images.stream()
                .map(image -> image.getUrl()) // Adjust this method to get the appropriate String representation
                .collect(Collectors.toList());
    }

    @Named("mapDistrictIdToDistrict")
    default District mapDistrictIdToDistrict(Integer districtId) {
        if (districtId == null) {
            return null;
        }
        District district = new District();
        district.setId(districtId);  // Only setting the ID, can fetch full details in service layer if necessary
        return district;
    }

    @Named("mapStringsToImages")
    default List<Images> mapStringsToImages(List<String> imageUrls) {
        if (imageUrls == null) {
            return null;
        }
        return imageUrls.stream()
                .map(url -> {
                    Images image = new Images();
                    image.setUrl(url);  // Assuming Images has a `url` field
                    return image;
                })
                .collect(Collectors.toList());
    }

}
