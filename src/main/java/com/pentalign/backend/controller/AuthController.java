package com.pentalign.backend.controller;

import com.pentalign.backend.dto.*;
import com.pentalign.backend.entities.RefreshToken;
import com.pentalign.backend.security.JwtService;
import com.pentalign.backend.service.AuthenticationService;
import com.pentalign.backend.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for handling authentication-related endpoints such as registration, login, and token refresh.
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authService;
    private final RefreshTokenService refreshTokenService;
    private final JwtService jwtService;

    /**
     * Registers a new user with the provided registration details.
     *
     * @param request the registration request containing user details
     * @return a response entity containing authentication response data
     */
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    /**
     * Authenticates a user with the provided credentials.
     *
     * @param request the authentication request containing login credentials
     * @return a response entity containing authentication response data
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }

    /**
     * Refreshes the access token using a valid refresh token.
     *
     * @param request the token refresh request containing the refresh token
     * @return a response entity containing the new access token and the refresh token
     */
    @PostMapping("/refresh")
    public ResponseEntity<TokenRefreshResponse> refresh(@RequestBody TokenRefreshRequest request) {
        RefreshToken rt = refreshTokenService.findByToken(request.getRefreshToken())
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));
        refreshTokenService.verifyExpiration(rt);

        String newAccessToken = jwtService.generateToken(rt.getUser());
        return ResponseEntity.ok(new TokenRefreshResponse(newAccessToken, rt.getToken()));
    }
}

