package com.example.homestay.service.homestay.image;

import com.example.homestay.exception.ApiException;
import com.example.homestay.exception.ErrorCode;
import com.example.homestay.exception.ImageSizeException;
import com.example.homestay.exception.NotFoundException;
import com.example.homestay.model.Homestay;
import com.example.homestay.model.Images;
import com.example.homestay.repository.HomestayRepository;
import com.example.homestay.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final HomestayRepository homestayRepository;
    private final ImageRepository imageRepository;

    @Value("${file.max-file-size}")
    private long maxFileSize;

    @Override
    public void uploadImages(Integer homestayId, List<MultipartFile> files) {

        Homestay homestay = homestayRepository.findById(homestayId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.HOMESTAY_NOT_FOUND));

        for (MultipartFile file : files) {
            try {

                if (file.isEmpty()) {
                    throw new IllegalArgumentException("File is empty: " + file.getOriginalFilename());
                }

                if (file.getSize() > maxFileSize) {
                    continue;
                }

                Path targetStaticDir = Paths.get("target/classes/static/homestays", String.valueOf(homestay.getId()));
                Path resourcesStaticDir = Paths.get("src/main/resources/static/homestays", String.valueOf(homestay.getId()));

                if (!Files.exists(targetStaticDir)) {
                    Files.createDirectories(targetStaticDir);
                }

                if (!Files.exists(resourcesStaticDir)) {
                    Files.createDirectories(resourcesStaticDir);
                }

                String fileName = Objects.requireNonNull(file.getOriginalFilename());
                Path targetFilePath = targetStaticDir.resolve(fileName);
                Files.copy(file.getInputStream(), targetFilePath, StandardCopyOption.REPLACE_EXISTING);

                Path resourcesFilePath = resourcesStaticDir.resolve(fileName);
                Files.copy(file.getInputStream(), resourcesFilePath, StandardCopyOption.REPLACE_EXISTING);

                // Generate the public URL (e.g., http://localhost:8080/api/homestays/1/anh1.jpg)
                String imageUrl = "/homestays/" + homestay.getId() + "/" + fileName;
                String fullImageUrl = "http://localhost:8080/api" + imageUrl;

                // Save the image entity with the public URL
                Images image = new Images();
                image.setUrl(fullImageUrl); // Save the URL instead of the file system path
                image.setHomestay(homestay);

                // Save the image entity to the database
                imageRepository.save(image);

            } catch (IOException e) {
                throw new ApiException(ErrorCode.FORBIDDEN);
            }
        }
    }

    @Override
    public void deleteImage(Integer homestayId, Integer imageId) {
        // Find the image by ID
        Images image = imageRepository.findById(imageId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.IMAGE_NOT_FOUND));

        // Ensure the image belongs to the specified homestay
        if (!image.getHomestay().getId().equals(homestayId)) {
            throw new RuntimeException("You cannot delete this image");
        }

        // Get the URL stored in the database
        String imageUrl = image.getUrl();  // Full URL: http://localhost:8080/homestays/1/anh1.jpg
        System.out.println("Stored URL: " + imageUrl);

        // Remove the 'localhost:8080' part of the URL to get the relative path
        String imageRelativePath = imageUrl.replace("http://localhost:8080", "");

        // Optionally, if your URL structure includes "api", you might want to remove that part too
        imageRelativePath = imageRelativePath.replace("/api", "");

        System.out.println("Relative path: " + imageRelativePath);

        // Convert the relative path to an absolute file system path
        String projectRootPath = Paths.get("").toAbsolutePath().toString(); // Gets the project root path


        // Paths for the image in both directories
        Path targetFilePath = Paths.get(projectRootPath, "target", "classes", "static", imageRelativePath);
        Path resourcesFilePath = Paths.get(projectRootPath, "src", "main", "resources", "static", imageRelativePath);

        // Log the file system paths
        System.out.println("Target file system path: " + targetFilePath);
        System.out.println("Resources file system path: " + resourcesFilePath);

        try {
            // Delete the image file if it exists in the target/classes/static
            Files.deleteIfExists(targetFilePath);

            // Delete the image file if it exists in src/main/resources/static
            Files.deleteIfExists(resourcesFilePath);

            // Get the parent folder for both paths
            Path targetParentFolder = targetFilePath.getParent();
            Path resourcesParentFolder = resourcesFilePath.getParent();

            // Check if the target folder is empty and delete if it is
            if (Files.isDirectory(targetParentFolder) && isDirectoryEmpty(targetParentFolder)) {
                Files.delete(targetParentFolder); // Delete the folder if empty
            }

            // Check if the resources folder is empty and delete if it is
            if (Files.isDirectory(resourcesParentFolder) && isDirectoryEmpty(resourcesParentFolder)) {
                Files.delete(resourcesParentFolder); // Delete the folder if empty
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete file: " + e.getMessage());
        }

// Delete the image record from the database
        imageRepository.delete(image);
    }

    // Helper method to check if a directory is empty
    private boolean isDirectoryEmpty(Path directory) throws IOException {
        try (DirectoryStream<Path> dirStream = Files.newDirectoryStream(directory)) {
            return !dirStream.iterator().hasNext();
        }
    }
}

