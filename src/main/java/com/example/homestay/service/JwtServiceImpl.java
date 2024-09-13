package com.example.homestay.service;

import com.example.homestay.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class JwtServiceImpl implements JwtService{

    @Autowired
    private JwtEncoder jwtEncoder;

    @Value("${jwt.expiration.refresh-token}")
    private long refreshTokenExpTime;

    @Value("${jwt.expiration.access-token}")
    private long accessTokenExpTime;

    private final Instant issueAt = TimeUtils.getIssuedAt();
    private final Instant accessTokenExp = TimeUtils.getExpiredAt(accessTokenExpTime);
    private final Instant refreshTokenExp = TimeUtils.getExpiredAt(refreshTokenExpTime);

    @Override
    public String generateAccessToken(String username, List<String> roles) {

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

}
