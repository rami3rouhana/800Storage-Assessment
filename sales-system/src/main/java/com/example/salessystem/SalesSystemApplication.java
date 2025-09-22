package com.example.salessystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point of the Spring Boot application.  This class bootstraps the
 * embedded servlet container and auto‑configures Spring components.  No
 * additional configuration is required here because Spring Boot will
 * automatically scan for components in this package and its subpackages.
 */
@SpringBootApplication
public class SalesSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(SalesSystemApplication.class, args);
    }
}