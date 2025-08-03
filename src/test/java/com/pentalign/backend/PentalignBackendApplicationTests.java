package com.pentalign.backend;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@RequiredArgsConstructor
class PentalignBackendApplicationTests {

    private final ApplicationContext applicationContext;

    @Test
    void contextLoads() {
        // Verify that the application context loads and contains expected beans
        assertNotNull(applicationContext);
        assertTrue(applicationContext.containsBean("jwtService"));
    }

}
