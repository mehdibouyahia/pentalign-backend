package com.pentalign.backend.service;

import com.pentalign.backend.entities.RefreshToken;
import com.pentalign.backend.entities.User;
import com.pentalign.backend.repository.RefreshTokenRepository;
import com.pentalign.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

/**
 * Service for managing refresh tokens for user authentication.
 */
@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    @Value("${jwt.refresh.expiration-ms}")
    private Long refreshTokenDurationMs;

    /**
     * Creates a new refresh token for the specified user.
     * Deletes any existing tokens for the user before creating a new one.
     *
     * @param userId the user ID
     * @return the created refresh token
     */
    @Transactional
    public RefreshToken createRefreshToken(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Delete existing token(s) for this user
        deleteByUserId(userId);

        RefreshToken rt = RefreshToken.builder()
                .user(user)
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(refreshTokenDurationMs))
                .build();

        return refreshTokenRepository.save(rt);
    }

    /**
     * Finds a refresh token by its token string.
     *
     * @param token the token string
     * @return an optional containing the refresh token if found
     */
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    /**
     * Verifies if the refresh token is expired and deletes it if so.
     *
     * @param token the refresh token to verify
     * @throws RuntimeException if the token is expired
     */
    public void verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().isBefore(Instant.now())) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException("Refresh token expired. Please login again.");
        }
    }

    /**
     * Deletes all refresh tokens associated with the specified user.
     *
     * @param userId the user ID
     */
    @Transactional
    public void deleteByUserId(Long userId) {
        refreshTokenRepository.deleteByUser(userId);
    }
}
