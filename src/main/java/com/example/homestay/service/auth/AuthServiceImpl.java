package com.example.homestay.service.auth;

import com.example.homestay.enums.UserStatus;
import com.example.homestay.exception.ErrorCode;
import com.example.homestay.exception.ExistingException;
import com.example.homestay.mapper.UserMapper;
import com.example.homestay.model.Role;
import com.example.homestay.model.User;
import com.example.homestay.repository.RoleRepository;
import com.example.homestay.repository.UserRepository;
import com.example.homestay.security.CustomAuthDao;
import com.example.homestay.dto.reponse.AuthResponse;
import com.example.homestay.dto.request.AuthLoginRequest;
import com.example.homestay.dto.request.AuthRegisterRequest;
import com.example.homestay.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final CustomAuthDao authDao;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JwtService jwtService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtDecoder jwtDecoder;

    @Override
    public AuthResponse login(AuthLoginRequest request, HttpServletResponse response) {
        UserDetails user = authDao.loadUserByUsername(request.getUsername());

        // Check if the provided password matches the encoded password
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        List<String> roles = jwtService.extractRoles(user);

        // Generate access and refresh tokens
        String accessToken = jwtService.generateAccessToken(user.getUsername(), roles);
        String refreshToken = jwtService.generateRefreshToken(user.getUsername());

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public void register(AuthRegisterRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new ExistingException(ErrorCode.USER_EXISTED);
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ExistingException(ErrorCode.EMAIL_EXISTED);
        }

        // Encode the password
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        // Map request to User entity and set the encoded password
        User user = userMapper.toUserFromRegis(request);
        user.setPassword(encodedPassword);

        // Assign default status
        user.setStatus(UserStatus.ACTIVE); // Set default status

        // Assign default role
        Role defaultRole = roleRepository.findByName("USER");
        if (defaultRole == null) {
            throw new RuntimeException("Default role not found"); // Handle this case as needed
        }
        user.setRole(defaultRole);

        // Save the new user
        userRepository.save(user);
    }


    @Override
    public void forgotPassword(String email) {
    }

    @Override
    public AuthResponse refreshToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        // Check if the authorization header is present and valid
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Invalid Authorization header"); // Better to use a custom exception
        }

        String refreshToken = authHeader.substring(7);

        // Decode the refresh token and extract username
        Jwt jwt = jwtDecoder.decode(refreshToken);
        String username = jwt.getSubject();
        log.info("Username is {}", username);
        System.out.println("Correct");

        UserDetails user = authDao.loadUserByUsername(username);
        List<String> roles = jwtService.extractRoles(user);

        // Generate new access token
        String accessToken = jwtService.generateAccessToken(username, roles);

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
