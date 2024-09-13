//package com.example.homestay.service;
//
//import com.example.homestay.model.Booking;
//import com.example.homestay.repository.BookingRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//
//
//
//    @Service
//    public class BookingService {
//
//        @Autowired
//        private BookingRepository bookingRepository;
//
//        // Method to accept booking
//        public Booking acceptBooking(Long bookingId, Long hostId) {
//            Booking booking = bookingRepository.find(bookingId)
//                    .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));
//
//            // Ensure that only the host of the homestay can accept the booking
//            if (!booking.getHomestay().getH.equals(hostId)) {
//                throw new UnauthorizedException("You are not authorized to accept this booking");
//            }
//
//            // Update the status of the booking to Accepted
//            booking.setStatus(2); // Status 2 represents 'Accepted'
//            booking.setUpdatedAt(LocalDateTime.now());
//            return bookingRepository.save(booking);
//        }
//
//        // Method to reject booking
//        public Booking rejectBooking(Long bookingId, Long hostId) {
//            Booking booking = bookingRepository.findById(bookingId)
//                    .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));
//
//            if (!booking.getHomestay().getHostId().equals(hostId)) {
//                throw new UnauthorizedException("You are not authorized to reject this booking");
//            }
//
//            booking.setStatus(3); // Status 3 represents 'Rejected'
//            booking.setUpdatedAt(LocalDateTime.now());
//            return bookingReposit
//}
