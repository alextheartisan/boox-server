package com.github.alextheartisan.boox;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.github.alextheartisan.boox")
public class BooxApplication {

    public static void main(String[] args) {
        SpringApplication.run(BooxApplication.class, args);
    }
}
