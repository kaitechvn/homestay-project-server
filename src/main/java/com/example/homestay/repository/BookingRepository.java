package com.example.homestay.repository;

import com.example.homestay.enums.BookingStatus;
import com.example.homestay.model.Booking;
import com.example.homestay.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Integer> {

    Page<Booking> findAllByStatus(Pageable pageable, BookingStatus status);
    Page<Booking> findAllByUser(Pageable pageable, User user);
    Page<Booking> findAllByUserAndStatus(Pageable pageable, User user, BookingStatus status);

    @Query("SELECT b FROM Booking b WHERE " +
            "(b.checkinDate < :checkoutDate AND b.checkoutDate > :checkinDate) " +
            "AND b.id <> :confirmedBookingId " + // Exclude the confirmed booking itself
            "AND b.status = 'PENDING'") // Only consider pending bookings for cancellation
    List<Booking> findConflictingBookings(
            @Param("checkinDate") LocalDate checkinDate,
            @Param("checkoutDate") LocalDate checkoutDate,
            @Param("confirmedBookingId") Integer confirmedBookingId
    );
}
