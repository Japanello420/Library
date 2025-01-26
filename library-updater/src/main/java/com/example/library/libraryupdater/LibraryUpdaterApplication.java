package com.example.library.libraryupdater;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.example.library")
public class LibraryUpdaterApplication {
    public static void main(String[] args) {
        SpringApplication.run(LibraryUpdaterApplication.class, args);
    }
}
