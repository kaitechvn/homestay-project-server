package com.example.homestay.service.booking;

import com.example.homestay.dto.reponse.BookingResponse;
import com.example.homestay.dto.reponse.PagingResponse;
import com.example.homestay.dto.request.BookingRequest;
import com.example.homestay.dto.request.PagingRequest;
import com.example.homestay.enums.BookingStatus;
import com.example.homestay.exception.ErrorCode;
import com.example.homestay.exception.NotFoundException;
import com.example.homestay.mapper.BookingMapper;
import com.example.homestay.model.*;
import com.example.homestay.repository.*;
import com.example.homestay.utils.SecurityUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;

    private final BookingMapper bookingMapper;
    private final SecurityUtil securityUtil;
    private final UserRepository userRepository;
    private final HomestayRepository homestayRepository;
    private final LockDateRepository lockDateRepository;

    @Override
    public PagingResponse<BookingResponse> listByAdmin(PagingRequest pageRequest, BookingStatus status) {
        Pageable pageable = PageRequest.of(pageRequest.getPage() - 1, pageRequest.getSize());

        Page<Booking> pageResult;
        if (status != null) {
            // If status is provided, filter by it
            pageResult = bookingRepository.findAllByStatus(pageable, status);
        } else {
            // If no status is provided, fetch all bookings
            pageResult = bookingRepository.findAll(pageable);
        }

        return PagingResponse.from(
                pageRequest.getPage(),
                pageRequest.getSize(),
                pageResult.getTotalElements(),
                pageResult.stream()
                        .map(bookingMapper::toBookingResponse)
                        .toList());

    }

    @Override
    public PagingResponse<BookingResponse> listByUser(PagingRequest pageRequest, BookingStatus status) {

        Pageable pageable = PageRequest.of(pageRequest.getPage() - 1, pageRequest.getSize());

        User user = securityUtil.getCurrentUser();

        Page<Booking> pageResult;
        if (status != null) {
            // If status is provided, filter by it
            pageResult = bookingRepository.findAllByUserAndStatus(pageable, user, status);
        } else {
            // If no status is provided, fetch all bookings
            pageResult = bookingRepository.findAllByUser(pageable, user);
        }

        return PagingResponse.from(
                pageRequest.getPage(),
                pageRequest.getSize(),
                pageResult.getTotalElements(),
                pageResult.stream()
                        .map(bookingMapper::toBookingResponse)
                        .toList());

    }


    @Override
    @Transactional
    public BookingResponse createBooking(BookingRequest bookingRequest) {

        User user = getCurrentUserForBooking(bookingRequest);

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
    public void deleteBooking(Integer bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.BOOKING_NOT_FOUND));

        bookingRepository.delete(booking);
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

    private User getCurrentUserForBooking(BookingRequest bookingRequest) {
        // Check if the current user is ADMIN
        if (securityUtil.isAdmin()) {
            // If the user is ADMIN, fetch the user by ID from the bookingRequest
            return userRepository.findById(bookingRequest.getUserId())
                    .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));
        } else {
            // If the user is a regular USER, retrieve the user from security context
            return securityUtil.getCurrentUser();
        }
    }

}