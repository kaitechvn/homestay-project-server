package com.example.homestay.service.booking;

import com.example.homestay.dto.reponse.BookingResponse;
import com.example.homestay.dto.request.BookingRequest;
import com.example.homestay.enums.BookingStatus;
import com.example.homestay.exception.ErrorCode;
import com.example.homestay.exception.NotFoundException;
import com.example.homestay.mapper.BookingMapper;
import com.example.homestay.model.Booking;
import com.example.homestay.model.Homestay;
import com.example.homestay.model.LockDate;
import com.example.homestay.model.User;
import com.example.homestay.repository.BookingRepository;
import com.example.homestay.repository.HomestayRepository;
import com.example.homestay.repository.LockDateRepository;
import com.example.homestay.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final HomestayRepository homestayRepository;
    private final BookingMapper bookingMapper;
    private final LockDateRepository lockDateRepository;

    @Override
    @Transactional
    public BookingResponse createBooking(BookingRequest bookingRequest) {

        User user = getCurrentUser(bookingRequest);

        // Fetch the Homestay based on homestayId from bookingRequest
        Homestay homestay = homestayRepository.findById(bookingRequest.getHomestayId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.HOMESTAY_NOT_FOUND));

        // Map BookingRequest to Booking entity
        Booking booking = bookingMapper.toBooking(bookingRequest);

        // Calculate total amount and set it
        int totalAmount = calculateTotalAmount(bookingRequest, homestay.getPrice());
        booking.setTotalAmount(totalAmount);

        // Set user and homestay in the Booking entity
        booking.setUser(user);
        booking.setHomestay(homestay);

        // Save the booking
        Booking savedBooking = bookingRepository.save(booking);

        return bookingMapper.toBookingResponse(savedBooking);
    }

    @Override
    @Transactional
    public void cancelBooking(Integer bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.BOOKING_NOT_FOUND));

        // Check current status to prevent re-cancellation
        if (booking.getStatus() == BookingStatus.CANCELLED) {
            throw new IllegalStateException("Booking is already canceled.");
        }

        // Check if the booking is already confirmed or completed
        if (booking.getStatus() == BookingStatus.CONFIRMED || booking.getStatus() == BookingStatus.COMPLETED) {
            throw new IllegalStateException("Booking cannot be canceled because it is already confirmed or completed.");
        }

        // Check if cancellation is within the allowed time frame (e.g., 24 hours before check-in)
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime checkinTime = booking.getCheckinDate().atStartOfDay(); // Adjust as necessary
        long hoursUntilCheckin = ChronoUnit.HOURS.between(now, checkinTime);

        if (hoursUntilCheckin < 24) {
            throw new IllegalStateException("Booking cannot be canceled within 24 hours of check-in.");
        }

        // Update booking status to CANCELLED
        booking.setStatus(BookingStatus.CANCELLED);
        bookingRepository.save(booking);
    }

    @Override
    @Transactional
    public void confirmBooking(Integer bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.BOOKING_NOT_FOUND));

        // Check current status to prevent re-confirmation
        if (booking.getStatus() != BookingStatus.PENDING) {
            throw new IllegalStateException("Booking cannot be confirmed in its current state.");
        }

        booking.setStatus(BookingStatus.CONFIRMED);
        bookingRepository.save(booking);

        lockHomestayDates(booking);
    }

    @Override
    public Booking getBookingById(Integer bookingId) {
        return null;
    }

    @Override
    public List<Booking> getBookingsByUser(Integer userId) {
        return List.of();
    }

    @Override
    public Booking updateBooking(Integer bookingId, Booking booking) {
        return null;
    }

    // methods help main services
    private int calculateTotalAmount(BookingRequest bookingRequest, int homestayPrice) {
        long daysBetween = ChronoUnit.DAYS.between(bookingRequest.getCheckinDate(), bookingRequest.getCheckoutDate());
        return (int) (daysBetween * homestayPrice);
    }

    private void lockHomestayDates(Booking booking) {
        LocalDate checkinDate = booking.getCheckinDate();
        LocalDate checkoutDate = booking.getCheckoutDate();

        // Use a new variable for the iteration
        LocalDate currentDate = checkinDate;

        // Iterate over the date range and lock each date
        while (!currentDate.isAfter(checkoutDate)) {
            // Create a new lock date without checking for existing ones
            LockDate lockDate = new LockDate();
            lockDate.setHomestayId(booking.getHomestay().getId());
            lockDate.setLockDate(currentDate);
            lockDateRepository.save(lockDate);

            // Increment the currentDate variable for the loop
            currentDate = currentDate.plusDays(1);
        }
    }

    private User getCurrentUser(BookingRequest bookingRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        // Check if the current user is an ADMIN
        if (authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {
            // If the user is ADMIN, fetch the user by ID from the bookingRequest
            return userRepository.findById(bookingRequest.getUserId())
                    .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));
        } else {
            // If the user is not ADMIN, retrieve the user using the username from security context
            return userRepository.findByUsername(currentUsername)
                    .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));
        }
    }

}