package com.example.homestay.controller;

import com.example.homestay.dto.ApiResponse;
import com.example.homestay.dto.reponse.ReviewResponse;
import com.example.homestay.dto.request.ReviewRequest;
import com.example.homestay.service.review.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ApiResponse<?>> addReview(@Valid @RequestBody ReviewRequest reviewRequest) {
        return  null;
    }

    @GetMapping("/homestay/{homestayId}")
    public ResponseEntity<ApiResponse<List<ReviewResponse>>> listReviewsByHomestay(@PathVariable Integer homestayId) {

        return ResponseEntity.ok(
                new ApiResponse<>("Review by homestay list fetched successfully",
                        reviewService.listReviewByHomestay(homestayId)));

    }

}
