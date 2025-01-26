package com.example.library.libraryupdater;

import com.example.library.librarydata.model.Author;
import com.example.library.librarydata.model.Book;
import com.example.library.librarydata.model.Genre;
import com.example.library.librarydata.repository.AuthorRepository;
import com.example.library.librarydata.repository.BookRepository;
import com.example.library.librarydata.repository.GenreRepository;
import com.example.library.libraryupdater.Client.GoogleBooksClient;
import com.example.library.libraryupdater.Service.UpdaterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.*;

public class UpdaterServiceTest {

    @Mock
    private GoogleBooksClient googleBooksClient;

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private GenreRepository genreRepository;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private UpdaterService updaterService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testUpdateData_WhenBooksExist() {
        // Arrange
        String jsonResponse = """
                {
                    "items": [
                        {
                            "volumeInfo": {
                                "title": "Test Book",
                                "publishedDate": "2024-01-01",
                                "authors": ["Author1", "Author2"],
                                "categories": ["Fiction", "Adventure"],
                                "industryIdentifiers": [
                                    {"type": "ISBN_13", "identifier": "1234567890123"}
                                ]
                            }
                        }
                    ]
                }
                """;

        when(googleBooksClient.fetchBooks()).thenReturn(jsonResponse);

        // Mockowanie repozytorium autorów
        when(authorRepository.findByName("Author1")).thenReturn(Optional.empty());
        when(authorRepository.findByName("Author2")).thenReturn(Optional.empty());
        when(authorRepository.save(any(Author.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Mockowanie repozytorium gatunków
        when(genreRepository.findByName("Fiction")).thenReturn(Optional.empty());
        when(genreRepository.findByName("Adventure")).thenReturn(Optional.empty());
        when(genreRepository.save(any(Genre.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        updaterService.updateData();

        // Assert
        verify(googleBooksClient, times(1)).fetchBooks();
        verify(authorRepository, times(1)).save(argThat(author -> author.getName().equals("Author1")));
        verify(authorRepository, times(1)).save(argThat(author -> author.getName().equals("Author2")));
        verify(genreRepository, times(1)).save(argThat(genre -> genre.getName().equals("Fiction")));
        verify(genreRepository, times(1)).save(argThat(genre -> genre.getName().equals("Adventure")));
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    public void testUpdateData_WhenNoBooksExist() {
        // Arrange
        String jsonResponse = """
                {
                    "items": []
                }
                """;

        when(googleBooksClient.fetchBooks()).thenReturn(jsonResponse);

        // Act
        updaterService.updateData();

        // Assert
        verify(googleBooksClient, times(1)).fetchBooks();
        verify(authorRepository, never()).save(any(Author.class));
        verify(genreRepository, never()).save(any(Genre.class));
        verify(bookRepository, never()).save(any(Book.class));
    }

    @Test
    public void testUpdateData_WithNullResponse() {
        // Arrange
        when(googleBooksClient.fetchBooks()).thenReturn(null);

        // Act
        updaterService.updateData();

        // Assert
        verify(googleBooksClient, times(1)).fetchBooks();
        verify(authorRepository, never()).save(any(Author.class));
        verify(genreRepository, never()).save(any(Genre.class));
        verify(bookRepository, never()).save(any(Book.class));
    }
}
