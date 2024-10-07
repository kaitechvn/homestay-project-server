package com.example.homestay.repository;

import com.example.homestay.enums.BookingStatus;
import com.example.homestay.model.Booking;
import com.example.homestay.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {

    List<Booking> findAllByStatus(BookingStatus status); // Method to find bookings by status
    Page<Booking> findAllByStatus(Pageable pageable, BookingStatus status);
    Page<Booking> findAllByUser(Pageable pageable, User user);
    Page<Booking> findAllByUserAndStatus(Pageable pageable, User user, BookingStatus status);

}
