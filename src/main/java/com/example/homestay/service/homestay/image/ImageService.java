package com.example.homestay.service.homestay.image;

import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {

    void uploadImages(Integer homestayId, @NotNull List<MultipartFile> files);

    void deleteImage(Integer homestayId, Integer imageId);
}
