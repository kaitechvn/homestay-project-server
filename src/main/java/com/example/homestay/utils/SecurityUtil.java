package com.example.homestay.utils;

import com.example.homestay.model.Booking;
import com.example.homestay.model.User;
import com.example.homestay.repository.BookingRepository;
import com.example.homestay.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SecurityUtil {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookingRepository bookingRepository;

    public boolean isUserOwner(String username, Integer id) {
        User user = userRepository.findById(id).orElse(null);

        return user != null && user.getUsername().equals(username);
    }

    public boolean isBookingOwner(String username, Integer bookingId) {
        Optional<Booking> booking = bookingRepository.findById(bookingId);

        return booking.map(value -> value.getUser().getUsername().equals(username)).orElse(false);
    }


    public boolean isUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getAuthorities() != null) {
            return authentication.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("USER"));
        }
        return false;
    }

    public boolean isAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getAuthorities() != null) {
            return authentication.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ADMIN"));
        }
        return false;
    }

    // Method to get the current authenticated user
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            String username = authentication.getName();
            return userRepository.findByUsername(username).orElse(null);  // Ensure username is unique in DB
        }
        return null;
    }

}