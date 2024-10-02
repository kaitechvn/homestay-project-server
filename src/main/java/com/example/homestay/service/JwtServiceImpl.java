package com.example.homestay.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JwtServiceImpl implements JwtService{

    @Autowired
    private JwtEncoder jwtEncoder;

    @Value("${jwt.expiration.refresh-token}")
    private long refreshTokenExpTime;

    @Value("${jwt.expiration.access-token}")
    private long accessTokenExpTime;

    @Override
    public String generateAccessToken(String username, List<String> roles) {
        Instant issueAt = Instant.now();
        Instant accessTokenExp = issueAt.plusSeconds(accessTokenExpTime);

        JwtEncoderParameters parameters = JwtEncoderParameters.from(
                JwsHeader
                        .with(SignatureAlgorithm.RS256)
                        .build(),
                JwtClaimsSet.builder()
                        .subject(username)
                        .issuedAt(issueAt)
                        .expiresAt(accessTokenExp)
                        .claim("role", roles)
                        .build());

        return jwtEncoder.encode(parameters).getTokenValue();
    }

    @Override
    public String generateRefreshToken(String username) {
        Instant issueAt = Instant.now();
        Instant refreshTokenExp = issueAt.plusSeconds(refreshTokenExpTime);

        JwtEncoderParameters parameters = JwtEncoderParameters.from(
                JwsHeader
                        .with(SignatureAlgorithm.RS256)
                        .build(),
                JwtClaimsSet.builder()
                        .subject(username)
                        .issuedAt(issueAt)
                        .expiresAt(refreshTokenExp)
                        .build());

        return jwtEncoder.encode(parameters).getTokenValue();
    }

    @Override
    public List<String> extractRoles(UserDetails userDetails) {
        return userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
    }



}
