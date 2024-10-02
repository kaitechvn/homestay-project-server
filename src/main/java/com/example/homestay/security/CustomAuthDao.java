package com.example.homestay.security;

import com.example.homestay.exception.ErrorCode;
import com.example.homestay.exception.NotFoundException;
import com.example.homestay.model.User;
import com.example.homestay.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class CustomAuthDao implements UserDetailsService {

    private final UserRepository userRepository;

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

            log.info("loading user by username: {}", username);

            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

            return user;
        }
}