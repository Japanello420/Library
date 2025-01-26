package com.example.library.libraryupdater.Client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class GoogleBooksClient {
    private static final String API_KEY = "AIzaSyBqM7F827Xw76aYeuX-DR82h6jhZzr06CA";
    private static final String API_URL = "https://www.googleapis.com/books/v1/volumes?q=publishedDate:%s&key=" + API_KEY;

    public String fetchBooks() {
        RestTemplate restTemplate = new RestTemplate();
        System.out.println("Calling URL: " + API_URL);
        return restTemplate.getForObject(API_URL, String.class);
    }
}
