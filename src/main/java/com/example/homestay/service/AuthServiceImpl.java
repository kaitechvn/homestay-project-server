package com.example.homestay.service;

import com.example.homestay.security.CustomAuthDao;
import com.example.homestay.dto.reponse.AuthLoginReponse;
import com.example.homestay.dto.request.AuthLoginRequest;
import com.example.homestay.dto.request.AuthRegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final CustomAuthDao authDao;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthLoginReponse login(AuthLoginRequest authLoginRequest) {

        String username = authLoginRequest.getUsername();
        String password = authLoginRequest.getPassword();

        UserDetails user = authDao.loadUserByUsername(username);
        List<String> roles = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        if(!passwordEncoder.matches(password, user.getPassword())) {
            System.out.println("khai yeu linh");
        }

        return AuthLoginReponse.builder()
                .accessToken(jwtService.generateAccessToken(user.getUsername(), roles))
                .build();

    }

    @Override
    public void register(AuthRegisterRequest authRegisterRequest) {

    }

    @Override
    public void forgotPassword(String email) {

    }

}
