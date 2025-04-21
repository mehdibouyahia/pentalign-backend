package com.pentalign.backend;

import com.pentalign.backend.config.JwtConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(JwtConfig.class)
public class PentalignBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(PentalignBackendApplication.class, args);
	}

}
