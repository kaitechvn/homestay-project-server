package com.example.homestay.controller;

import com.example.homestay.dto.ApiResponse;
import com.example.homestay.dto.reponse.BookingResponse;
import com.example.homestay.dto.request.BookingRequest;
import com.example.homestay.service.booking.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<ApiResponse<BookingResponse>> create(@Valid @RequestBody BookingRequest bookingRequest) {
        BookingResponse booking = bookingService.createBooking(bookingRequest);
        return new ResponseEntity<>(new ApiResponse<>("Booking created successfully",
                                        booking), HttpStatus.CREATED);

    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/{id}/confirm")
    public ResponseEntity<ApiResponse<?>> confirmBooking(@PathVariable Integer id) {
        bookingService.confirmBooking(id);
        return ResponseEntity.ok(
                new ApiResponse<>("Booking confirmed successfully"));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/{id}/cancel")
    public ResponseEntity<ApiResponse<?>> cancelBooking(@PathVariable Integer id) {
        bookingService.cancelBooking(id);
        return ResponseEntity.ok(
                new ApiResponse<>("Booking cancelled successfully"));
    }
}
