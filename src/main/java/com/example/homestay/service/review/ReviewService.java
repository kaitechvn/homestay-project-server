package com.example.homestay.service.review;

import com.example.homestay.dto.reponse.ReviewResponse;
import com.example.homestay.dto.request.ReviewRequest;

import java.util.List;

public interface ReviewService {

    List<ReviewResponse> listReviewByHomestay(Integer homestayId);

    // For ADMIN
    // Page List review

    void addReview(ReviewRequest reviewRequest);
    void deleteReview(Integer reviewId);
}
