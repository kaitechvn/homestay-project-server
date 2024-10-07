package com.example.homestay.cronjob;

import com.example.homestay.enums.BookingStatus;
import com.example.homestay.model.Booking;
import com.example.homestay.repository.BookingRepository;
import com.example.homestay.repository.LockDateRepository;
import com.example.homestay.service.booking.BookingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

//@EnableAsync
@Service
@RequiredArgsConstructor
@Log4j2
public class BookingSchedule {

    private final BookingRepository bookingRepository;
    private final LockDateRepository lockDateRepository;
    private final BookingService bookingService;

//    @Scheduled(cron = "0 */1 * * * ?")
    public void cancelPendingBookings() {

        log.info("Scheduled task to cancel pending bookings started at {}", LocalDate.now());

        List<Booking> pendingBookings = bookingRepository.findAllByStatus(BookingStatus.PENDING);

        for (Booking booking : pendingBookings) {

            boolean hasConflict = lockDateRepository.existsByHomestayIdAndLockDateBetween(
                    booking.getHomestay().getId(),
                    booking.getCheckinDate(),
                    booking.getCheckoutDate()
            );

            if (hasConflict) {
                bookingService.cancelBooking(booking.getId());
            }
        }

        log.info("Scheduled task to cancel pending bookings done at {}", LocalDate.now()); // Log when task starts

    }

}
