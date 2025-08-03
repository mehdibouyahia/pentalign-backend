package com.pentalign.backend.security;

import com.pentalign.backend.config.JwtConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(JwtServiceTest.class);

    @Mock
    private JwtConfig jwtConfig;

    private JwtService jwtService;
    private UserDetails userDetails;

    @BeforeEach
    void setUp() {
        logger.info("═══════════════════════════════════════════════════════════");
        logger.info("🔧 Setting up JwtServiceTest...");
        
        // Use lenient() to avoid unnecessary stubbing warnings
        lenient().when(jwtConfig.getSecret()).thenReturn("mySecretKeyForTesting123456789012345678901234567890");
        lenient().when(jwtConfig.getExpirationMs()).thenReturn(900000L); // 15 minutes

        jwtService = new JwtService(jwtConfig);
        userDetails = new User("testuser", "password", Collections.emptyList());
        
        logger.info("✅ JwtServiceTest setup completed");
        logger.info("═══════════════════════════════════════════════════════════");
    }

    // ═══════════════════════════════════════════════════════════════════════════════════
    // UTILITY METHODS
    // ═══════════════════════════════════════════════════════════════════════════════════

    private void logTestStart(String testName) {
        logger.info("");
        logger.info("🚀 TEST: {}", testName);
        logger.info("───────────────────────────────────────────────────────────");
    }

    private void logTestPass(String testName, String details) {
        logger.info("✅ PASS: {} - {}", testName, details);
        logger.info("");
    }

    private void logTestInfo(String message, Object... args) {
        logger.info("📝 " + message, args);
    }

    private void logException(Exception exception) {
        logger.info("🔥 Exception: {} - {}", exception.getClass().getSimpleName(), exception.getMessage());
    }

    // ═══════════════════════════════════════════════════════════════════════════════════
    // TEST METHODS
    // ═══════════════════════════════════════════════════════════════════════════════════

    @Test
    void shouldGenerateTokenSuccessfully() {
        logTestStart("shouldGenerateTokenSuccessfully");
        
        // When
        String token = jwtService.generateToken(userDetails);
        logTestInfo("Generated token length: {} characters", token.length());

        // Then
        assertNotNull(token);
        assertFalse(token.isEmpty());
        assertTrue(token.contains("."));
        assertEquals(3, token.split("\\.").length);
        
        logTestPass("shouldGenerateTokenSuccessfully", 
                   String.format("Token generated with %d parts", token.split("\\.").length));
    }

    @Test
    void shouldExtractUsernameFromToken() {
        logTestStart("shouldExtractUsernameFromToken");
        
        // Given
        String token = jwtService.generateToken(userDetails);
        logTestInfo("Using token for user: {}", userDetails.getUsername());

        // When
        String extractedUsername = jwtService.extractUsername(token);

        // Then
        assertEquals("testuser", extractedUsername);
        
        logTestPass("shouldExtractUsernameFromToken", 
                   String.format("Expected: testuser, Got: %s", extractedUsername));
    }

    @Test
    void shouldValidateTokenSuccessfully() {
        logTestStart("shouldValidateTokenSuccessfully");
        
        // Given
        String token = jwtService.generateToken(userDetails);
        logTestInfo("Validating token for user: {}", userDetails.getUsername());

        // When
        boolean isValid = jwtService.isTokenValid(token, userDetails);

        // Then
        assertTrue(isValid);
        
        logTestPass("shouldValidateTokenSuccessfully", String.format("Token valid: %s", isValid));
    }

    @Test
    void shouldReturnFalseForInvalidUser() {
        logTestStart("shouldReturnFalseForInvalidUser");
        
        // Given
        String token = jwtService.generateToken(userDetails);
        UserDetails differentUser = new User("differentuser", "password", Collections.emptyList());
        logTestInfo("Testing token for original user: {} against different user: {}", 
                   userDetails.getUsername(), differentUser.getUsername());

        // When
        boolean isValid = jwtService.isTokenValid(token, differentUser);

        // Then
        assertFalse(isValid);
        
        logTestPass("shouldReturnFalseForInvalidUser", 
                   String.format("Token correctly invalid for different user: %s", isValid));
    }

    @Test
    void shouldThrowExceptionForMalformedToken() {
        logTestStart("shouldThrowExceptionForMalformedToken");
        
        // Given
        String malformedToken = "invalid.token";
        logTestInfo("Testing malformed token: {}", malformedToken);

        // When/Then
        Exception exception = assertThrows(Exception.class, () -> jwtService.extractUsername(malformedToken));
        logException(exception);
        
        logTestPass("shouldThrowExceptionForMalformedToken", "Exception thrown as expected");
    }

    @Test
    void shouldReturnFalseForNullToken() {
        logTestStart("shouldReturnFalseForNullToken");
        
        logTestInfo("Testing null token validation");

        // When
        boolean isValid = jwtService.isTokenValid(null, userDetails);

        // Then
        assertFalse(isValid);
        
        logTestPass("shouldReturnFalseForNullToken", String.format("Null token rejected: %s", !isValid));
    }

    @Test
    void shouldReturnFalseForNullUserDetails() {
        logTestStart("shouldReturnFalseForNullUserDetails");
        
        // Given
        String token = jwtService.generateToken(userDetails);
        logTestInfo("Testing valid token against null user details");

        // When
        boolean isValid = jwtService.isTokenValid(token, null);

        // Then
        assertFalse(isValid);
        
        logTestPass("shouldReturnFalseForNullUserDetails", 
                   String.format("Null user details rejected: %s", !isValid));
    }

    @Test
    void shouldThrowExceptionForNullUserDetailsInGeneration() {
        logTestStart("shouldThrowExceptionForNullUserDetailsInGeneration");
        
        logTestInfo("Testing token generation with null user details");

        // When/Then
        Exception exception = assertThrows(IllegalArgumentException.class, () -> jwtService.generateToken(null));
        logException(exception);
        
        logTestPass("shouldThrowExceptionForNullUserDetailsInGeneration", "IllegalArgumentException thrown");
    }

    @Test
    void shouldThrowExceptionForEmptyToken() {
        logTestStart("shouldThrowExceptionForEmptyToken");
        
        logTestInfo("Testing empty token extraction");

        // When/Then
        Exception exception = assertThrows(IllegalArgumentException.class, () -> jwtService.extractUsername(""));
        logException(exception);
        
        logTestPass("shouldThrowExceptionForEmptyToken", "IllegalArgumentException thrown for empty token");
    }

    @Test
    void shouldThrowExceptionForBlankToken() {
        logTestStart("shouldThrowExceptionForBlankToken");
        
        logTestInfo("Testing blank token extraction");

        // When/Then
        Exception exception = assertThrows(IllegalArgumentException.class, () -> jwtService.extractUsername("   "));
        logException(exception);
        
        logTestPass("shouldThrowExceptionForBlankToken", "IllegalArgumentException thrown for blank token");
    }

    @Test
    void shouldExtractExpirationFromToken() {
        logTestStart("shouldExtractExpirationFromToken");
        
        // Given
        String token = jwtService.generateToken(userDetails);
        java.util.Date now = new java.util.Date();
        logTestInfo("Current time: {}", now);

        // When
        java.util.Date expiration = jwtService.extractExpiration(token);

        // Then
        assertNotNull(expiration);
        assertTrue(expiration.after(now));
        
        long diffInMs = expiration.getTime() - now.getTime();
        long diffInMinutes = diffInMs / (1000 * 60);
        
        logTestInfo("Token expires at: {}", expiration);
        logTestInfo("Time until expiration: {} minutes", diffInMinutes);
        
        logTestPass("shouldExtractExpirationFromToken", "Expiration extracted successfully");
    }

    @Test
    void shouldReturnTrueForNonExpiredToken() {
        logTestStart("shouldReturnTrueForNonExpiredToken");
        
        // Given
        String token = jwtService.generateToken(userDetails);
        logTestInfo("Testing expiration status of fresh token");

        // When
        boolean isExpired = jwtService.isTokenExpired(token);

        // Then
        assertFalse(isExpired);
        
        logTestPass("shouldReturnTrueForNonExpiredToken", 
                   String.format("Fresh token not expired: %s", !isExpired));
    }
}