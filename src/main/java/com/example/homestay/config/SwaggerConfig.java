package com.example.homestay.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(info = @Info
        (contact = @Contact(name = "Khai Bui",
                email = "khaibq1@vmogroup.com",
                url = "https://kaitech.dev.vn"),
                version = "1.0.0",
                title = "API Doc for Homestay Project"),
        servers = {@Server(url = "http://localhost:8080/api", description = "LOCAL ENV"),
                @Server(url = "http://localhost:8083/api", description = "PROD ENV")},
        security = @SecurityRequirement(name = "Authorization") // Global security requirement
)

@SecurityScheme(
        name = "Authorization",
        description = "JWT Auth",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer",
        in = SecuritySchemeIn.HEADER)
@Configuration
public class SwaggerConfig {}