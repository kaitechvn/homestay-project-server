package com.example.homestay.cronjob;

import com.example.homestay.enums.BookingStatus;
import com.example.homestay.model.Booking;
import com.example.homestay.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.List;

@EnableAsync
@Component
public class LockDateSchedule {

    @Autowired
    private BookingRepository bookingRepository;

    @Async
    @Scheduled(cron = "${cron.expression}")
    public void scheduleLockDateTask() {
        resolveBookingConflicts();
    }

    public void resolveBookingConflicts() {
        List<Booking> allBookings = bookingRepository.findAll();

        for (Booking booking : allBookings) {
            if (booking.getStatus().equals(BookingStatus.COMPLETED)) {
                List<Booking> conflictingBookings = findConflictingBookings(booking);

                // Cancel conflicting bookings
                for (Booking conflict : conflictingBookings) {
                    conflict.setStatus(BookingStatus.CANCELLED);
                    bookingRepository.save(conflict); // Save the cancellation
                }
            }
        }
    }

    private List<Booking> findConflictingBookings(Booking confirmedBooking) {
        return bookingRepository.findConflictingBookings(
                confirmedBooking.getCheckinDate(), confirmedBooking.getCheckoutDate(),
                confirmedBooking.getId()
        );
    }
}
