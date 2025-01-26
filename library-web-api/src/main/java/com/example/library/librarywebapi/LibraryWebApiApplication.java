package com.example.library.librarywebapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example.library"})
public class LibraryWebApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibraryWebApiApplication.class, args);
    }

}
