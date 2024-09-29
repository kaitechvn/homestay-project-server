package com.example.homestay.service.booking;

import com.example.homestay.dto.reponse.BookingResponse;
import com.example.homestay.dto.request.BookingRequest;
import com.example.homestay.model.Booking;

import java.util.List;

public interface BookingService {

    BookingResponse createBooking(BookingRequest bookingRequest);

    // ADMIN, HOST
    void cancelBooking(Integer bookingId);

    // ADMIN, HOST
    void confirmBooking(Integer bookingId);

    // Get a booking by its ID
    Booking getBookingById(Integer bookingId);

    // Get all bookings for a specific user
    List<Booking> getBookingsByUser(Integer userId);

    // Update booking details for ADMIN
    Booking updateBooking(Integer bookingId, Booking booking);


}