package com.example.homestay.controller;

import com.example.homestay.dto.ApiResponse;
import com.example.homestay.dto.reponse.BookingResponse;
import com.example.homestay.dto.reponse.PagingResponse;
import com.example.homestay.dto.request.BookingRequest;
import com.example.homestay.dto.request.PagingRequest;
import com.example.homestay.enums.BookingStatus;
import com.example.homestay.service.booking.BookingService;
import com.example.homestay.utils.SecurityUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/bookings")
public class BookingController {

    private final SecurityUtil securityUtil;
    private final BookingService bookingService;

    @GetMapping
    public ResponseEntity<ApiResponse<PagingResponse<BookingResponse>>> getBookings(
            @RequestParam(required = false) BookingStatus status,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {

        PagingRequest pageRequest = new PagingRequest(page, size);

        PagingResponse<BookingResponse> bookings;

        if (securityUtil.isAdmin()) {
            bookings = bookingService.listByAdmin(pageRequest, status);
        } else {
            bookings = bookingService.listByUser(pageRequest, status);
        }

        return ResponseEntity.ok(new ApiResponse<>("Booking list fetched successfully", bookings));
    }

    @PostMapping
    public ResponseEntity<BookingResponse> create(@Valid @RequestBody BookingRequest bookingRequest) {
        BookingResponse booking = bookingService.createBooking(bookingRequest);
        return ResponseEntity.ok(booking);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/{id}/confirm")
    public ResponseEntity<ApiResponse<?>> confirmBooking(@PathVariable Integer id) {
        bookingService.confirmBooking(id);
        return ResponseEntity.ok(
                new ApiResponse<>("Booking confirmed successfully"));
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<ApiResponse<?>> cancelBooking(@PathVariable Integer id) {
        bookingService.cancelBooking(id);
        return ResponseEntity.ok(
                new ApiResponse<>("Booking cancelled successfully"));
    }
}
