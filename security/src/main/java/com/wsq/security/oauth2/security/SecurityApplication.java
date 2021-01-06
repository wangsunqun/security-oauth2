package com.wsq.security.oauth2.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SecurityApplication {

    public static void main(String[] args) {
        try {
            SpringApplication.run(SecurityApplication.class, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
