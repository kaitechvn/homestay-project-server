package com.example.homestay.service;

import com.example.homestay.cronjob.LockDateSchedule;
import com.example.homestay.enums.BookingStatus;
import com.example.homestay.model.Booking;
import com.example.homestay.repository.BookingRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static org.mockito.Mockito.*;

@Log4j2
@ExtendWith(MockitoExtension.class)
public class LockDateScheduleTest {

    @Mock
    private BookingRepository bookingRepository;

    @InjectMocks
    private LockDateSchedule lockDateSchedule;

    private Booking completedBooking;
    private Booking completedBooking2;
    private Booking conflictingBooking;

    @BeforeEach
    public void setup() {

        completedBooking = new Booking();
        completedBooking.setId(1);
        completedBooking.setCheckinDate(LocalDate.of(2024, 10, 1));
        completedBooking.setCheckoutDate(LocalDate.of(2024, 10, 5));
        completedBooking.setStatus(BookingStatus.COMPLETED);

        completedBooking2 = new Booking();
        completedBooking2.setId(3);
        completedBooking2.setCheckinDate(LocalDate.of(2024, 10, 7));
        completedBooking2.setCheckoutDate(LocalDate.of(2024, 10, 12));
        completedBooking2.setStatus(BookingStatus.COMPLETED);

        conflictingBooking = new Booking();
        conflictingBooking.setId(2);
        conflictingBooking.setCheckinDate(LocalDate.of(2024, 10, 3));
        conflictingBooking.setCheckoutDate(LocalDate.of(2024, 10, 7));
        conflictingBooking.setStatus(BookingStatus.PENDING);
    }

    @Test
    public void testResolveBookingConflicts() {

        List<Booking> allBookings = Collections.singletonList(completedBooking);
        List<Booking> conflictingBookings = Collections.singletonList(conflictingBooking);

        when(bookingRepository.findAll()).thenReturn(allBookings);
        when(bookingRepository.findConflictingBookings(
                completedBooking.getCheckinDate(),
                completedBooking.getCheckoutDate(),
                completedBooking.getId()
        )).thenReturn(conflictingBookings);

        log.info("All bookings: {}", allBookings);
        log.info("Conflicting bookings: {}", conflictingBookings);

        // Call the method under test
        lockDateSchedule.resolveBookingConflicts();

        // Assert that the conflicting booking's status was changed to CANCELLED
        assert conflictingBooking.getStatus() == BookingStatus.CANCELLED;
        log.info("Updated status of conflicting booking: {}", conflictingBooking.getStatus());

        // Verify that save method was called for the conflicting booking
        verify(bookingRepository, times(1)).save(conflictingBooking);

    }

    @Test
    public void testNoConflicts() {
        // Mock the repository to return no conflicting bookings
        List<Booking> allBookings = Arrays.asList(completedBooking, completedBooking2);

        when(bookingRepository.findAll()).thenReturn(allBookings);
        when(bookingRepository.findConflictingBookings(
                completedBooking.getCheckinDate(),
                completedBooking.getCheckoutDate(),
                completedBooking.getId()
        )).thenReturn(Collections.emptyList());

        log.info("All bookings with no conflict: {}", allBookings);

        // Call the method under test
        lockDateSchedule.resolveBookingConflicts();

        // Verify that save was not called since there are no conflicting bookings
        verify(bookingRepository, times(0)).save(any(Booking.class));
    }


}
