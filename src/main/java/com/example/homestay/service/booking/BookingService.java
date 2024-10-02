package com.example.homestay.service.booking;

import com.example.homestay.dto.reponse.BookingResponse;
import com.example.homestay.dto.reponse.PagingResponse;
import com.example.homestay.dto.request.BookingRequest;
import com.example.homestay.dto.request.PagingRequest;
import com.example.homestay.enums.BookingStatus;

public interface BookingService {

    BookingResponse createBooking(BookingRequest bookingRequest);

    // ADMIN, HOST
    void cancelBooking(Integer bookingId);

    // ADMIN, HOST
    void confirmBooking(Integer bookingId);

    // Get all bookings for a specific user
    PagingResponse<BookingResponse> listByUser(PagingRequest request, BookingStatus status);

    // Get all bookings for admin management
    PagingResponse<BookingResponse> listByAdmin(PagingRequest request, BookingStatus status);

    // Delete for ADMIN
    void deleteBooking(Integer bookingId);

}