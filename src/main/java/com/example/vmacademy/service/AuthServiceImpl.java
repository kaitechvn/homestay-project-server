package com.example.vmacademy.service;

import com.example.vmacademy.exception.AuthenticateException;
import com.example.vmacademy.security.CustomAuthDao;
import com.example.vmacademy.dto.reponse.AuthLoginReponse;
import com.example.vmacademy.dto.request.AuthLoginRequest;
import com.example.vmacademy.dto.request.AuthRegisterRequest;
import com.example.vmacademy.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.vmacademy.utils.Constants.ErrorCode.PASSWORD_MISMATCH;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final CustomAuthDao authDao;
    private final JwtEncoder jwtEncoder;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthLoginReponse login(AuthLoginRequest authLoginRequest) {

        String username = authLoginRequest.getUsername();
        String password = authLoginRequest.getPassword();

        UserDetails user = authDao.loadUserByUsername(username);
        List<String> roles = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)  // Extract authority name as a string
                .collect(Collectors.toList());

        if(!passwordEncoder.matches(password, user.getPassword())) {
            throw new AuthenticateException(PASSWORD_MISMATCH, "Credentials Error") {
            };
        }

        return AuthLoginReponse.builder()
                .accessToken(grantAccessToken(user.getUsername(), roles))
                .build();

    }

    private String grantAccessToken(String username, List<String> roles) {
        long iat = System.currentTimeMillis() / 1000;
        long exp = iat + Duration.ofHours(1).toSeconds();

        JwtEncoderParameters parameters = JwtEncoderParameters.from(
                JwsHeader
                        .with(SignatureAlgorithm.RS256)
                        .build(),
                JwtClaimsSet.builder()
                        .subject("khaibui")
                        .issuedAt(Instant.ofEpochSecond(iat))
                        .expiresAt(Instant.ofEpochSecond(exp))
                        .claim("username", username)
                        .claim("role", roles)
                        .build());

        try {
            return jwtEncoder.encode(parameters).getTokenValue();
        } catch (JwtEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void register(AuthRegisterRequest authRegisterRequest) {

    }

    @Override
    public void forgotPassword(String email) {

    }
}
