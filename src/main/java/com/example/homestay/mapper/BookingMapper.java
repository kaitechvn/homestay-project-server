package com.example.homestay.mapper;

import com.example.homestay.dto.reponse.BookingResponse;
import com.example.homestay.dto.request.BookingRequest;
import com.example.homestay.model.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookingMapper {

    @Mapping(target = "status", constant = "PENDING")
    Booking toBooking(BookingRequest bookingRequest);

    @Mapping(target = "bookingId", source = "booking.id")
    @Mapping(target = "homestayId", source = "booking.homestay.id")
    @Mapping(target = "homestayName", source = "booking.homestay.name")
    BookingResponse toBookingResponse(Booking booking);
}
