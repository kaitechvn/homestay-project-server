package com.example.homestay.controller;

import com.example.homestay.dto.ApiResponse;
import com.example.homestay.dto.reponse.HomestayResponse;
import com.example.homestay.dto.reponse.PagingResponse;
import com.example.homestay.dto.request.HomestayRequest;
import com.example.homestay.dto.request.HomestayUpdateRequest;
import com.example.homestay.dto.request.PagingRequest;
import com.example.homestay.service.homestay.HomestayService;
import com.example.homestay.service.homestay.image.ImageService;
import com.example.homestay.service.homestay.lockdate.LockDateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDate;
import java.util.List;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/homestays")
public class HomestayController {

    private final ImageService imageService;
    private final HomestayService homestayService;
    private final LockDateService lockDateService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<HomestayResponse>> get(@PathVariable Integer id) {
        HomestayResponse homestay = homestayService.get(id);
        return ResponseEntity.ok(new ApiResponse<>("Homestay fetched successfully", homestay));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PagingResponse<HomestayResponse>>> list(
            @RequestParam Integer page,
            @RequestParam Integer size) {
        PagingRequest pagingRequest = new PagingRequest(page, size);
        PagingResponse<HomestayResponse> homestays = homestayService.list(pagingRequest);
        return ResponseEntity.ok(new ApiResponse<>("Homestay list fetched successfully", homestays));
    }

    @GetMapping("/filter")
    public ResponseEntity<ApiResponse<PagingResponse<HomestayResponse>>> filter(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "5") Integer size,
            @RequestParam(value = "districtId", required = false) Integer districtId,
            @RequestParam(value = "minPrice", required = false) Integer minPrice,
            @RequestParam(value = "maxPrice", required = false) Integer maxPrice,
            @RequestParam(value = "guests", required = false) Integer guests,
            @RequestParam(value = "checkIn", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkIn,
            @RequestParam(value = "checkOut", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOut
    ) {
        PagingRequest pageRequest = new PagingRequest(page, size);

        PagingResponse<HomestayResponse> response = homestayService.filter(
                pageRequest, districtId, minPrice, maxPrice, guests, checkIn, checkOut
        );

        return ResponseEntity.ok(new ApiResponse<>("Homestay filter fetched successfully", response));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<HomestayResponse>> create(@RequestBody @Valid HomestayRequest homestayRequest) {
        HomestayResponse homestay = homestayService.create(homestayRequest);
        return new ResponseEntity<>(new ApiResponse<>("Homestay created successfully", homestay), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> update(@RequestBody @Valid HomestayUpdateRequest homestayUpdateRequest,
                                                 @PathVariable Integer id) {
        homestayService.update(homestayUpdateRequest, id);
        return ResponseEntity.ok(new ApiResponse<>("Homestay updated successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> delete(@PathVariable Integer id) {
        homestayService.delete(id);
        return ResponseEntity.ok(new ApiResponse<>("Homestay deleted successfully"));
    }

    // Images
    @PostMapping("/{homestayId}/images")
    public ResponseEntity<ApiResponse<?>> uploadImage(@PathVariable Integer homestayId,
                                                      @RequestParam("image") List<MultipartFile> files) {
        imageService.uploadImages(homestayId, files);
        return ResponseEntity.ok(
                new ApiResponse<>("Image uploaded successfully"));
    }

    // Delete a specific image
    @DeleteMapping("/{homestayId}/images/{imageId}")
    public ResponseEntity<ApiResponse<?>> deleteImage(@PathVariable Integer homestayId,
                                                      @PathVariable Integer imageId) {
        imageService.deleteImage(homestayId, imageId);
        return ResponseEntity.ok(
                new ApiResponse<>("Image deleted successfully"));
    }

    // Lock Dates
    @GetMapping("/{homestayId}/lockdates")
    public ResponseEntity<List<LocalDate>> getLockDates(@PathVariable Integer homestayId) {
        List<LocalDate> lockDates = lockDateService.getLockDates(homestayId);
        return ResponseEntity.ok(lockDates);
    }

    // Add a lock date for a homestay
    @PostMapping("/{homestayId}/lockdates")
    public ResponseEntity<Void> addLockDate(@PathVariable("homestayId") Integer homestayId,
                                            @RequestBody List<LocalDate> lockDate) {
        lockDateService.addLockDates(homestayId, lockDate);
        return ResponseEntity.ok().build();
    }

    // Remove a specific lock date for a homestay
    @PostMapping("/{homestayId}/unlockdates")
    public ResponseEntity<Void> removeLockDate(@PathVariable("homestayId") Integer homestayId,
                                               @RequestBody List<LocalDate> unlockDate) {
        lockDateService.removeLockDates(homestayId, unlockDate);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/top-rated")
    public ResponseEntity<List<HomestayResponse>> getTopRatedHomestays() {
        List<HomestayResponse> topRatedHomestays = homestayService.getTopRatedHomestays();
        return ResponseEntity.ok(topRatedHomestays);
    }

}
