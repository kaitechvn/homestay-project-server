package com.example.homestay.service.review;

import com.example.homestay.dto.reponse.ReviewResponse;
import com.example.homestay.dto.request.ReviewRequest;
import com.example.homestay.exception.ErrorCode;
import com.example.homestay.exception.NotFoundException;
import com.example.homestay.mapper.ReviewMapper;
import com.example.homestay.model.Booking;
import com.example.homestay.model.Homestay;
import com.example.homestay.model.Review;
import com.example.homestay.model.User;
import com.example.homestay.repository.BookingRepository;
import com.example.homestay.repository.HomestayRepository;
import com.example.homestay.repository.ReviewRepository;
import com.example.homestay.utils.SecurityUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService{

    private final SecurityUtil securityUtil;
    private final BookingRepository bookingRepository;
    private final ReviewMapper reviewMapper;
    private final ReviewRepository reviewRepository;
    private final HomestayRepository homestayRepository;

    @Override
    public List<ReviewResponse> listReviewByHomestay(Integer homestayId) {

        Homestay homestay = homestayRepository.findById(homestayId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.HOMESTAY_NOT_FOUND));

        List<Review> reviews = reviewRepository.findAllByHomestay(homestay);

        return reviews.stream()
                .sorted(Comparator.comparing(Review::getCreatedAt).reversed())
                .map(reviewMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void addReview(ReviewRequest reviewRequest) {

        User user = securityUtil.getCurrentUser();

        Booking booking = bookingRepository.findById(reviewRequest.getBookingId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.BOOKING_NOT_FOUND));

        if (booking.getIsReview()) {
            throw new RuntimeException("Da review");
        }

        Review review = reviewMapper.toEntity(reviewRequest, user, booking.getHomestay());

        reviewRepository.save(review);

        booking.setIsReview(true);
        bookingRepository.save(booking);
    }

    @Override
    public void deleteReview(Integer reviewId) {

    }

}
