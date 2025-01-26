package com.example.library.libraryupdater;

import com.example.library.libraryupdater.Client.GoogleBooksClient;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class GoogleBooksClientTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private GoogleBooksClient googleBooksClient;

    public GoogleBooksClientTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFetchBooks() {
        // Arrange
        String expectedUrl = "https://www.googleapis.com/books/v1/volumes?q=publishedDate:%s&key=AIzaSyBqM7F827Xw76aYeuX-DR82h6jhZzr06CA";
        String expectedResponse = "{\"items\": []}"; // Przykładowa odpowiedź z API
        when(restTemplate.getForObject(expectedUrl, String.class)).thenReturn(expectedResponse);

        // Act
        String actualResponse = googleBooksClient.fetchBooks();

        // Assert
        assertNotNull(actualResponse);
        assertEquals(expectedResponse, actualResponse);
        verify(restTemplate, times(1)).getForObject(expectedUrl, String.class);
    }
}
