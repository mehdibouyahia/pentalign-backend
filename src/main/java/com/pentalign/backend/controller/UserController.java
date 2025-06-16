package com.pentalign.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for user-related endpoints.
 * Provides endpoints that require authentication.
 */
@RestController
@RequestMapping("/api/user")
public class UserController {
    /**
     * Test endpoint to verify authentication.
     * Returns a message if the user is authenticated.
     *
     * @return a response entity with a success message
     */
    @GetMapping("/test")
    public ResponseEntity<String> testSecured() {
        return ResponseEntity.ok("You're authenticated!");
    }
}
