package com.example.library.librarydata.repository;

import com.example.library.librarydata.model.Author;
import com.example.library.librarydata.model.Book;
import com.example.library.librarydata.model.Genre;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Test
    public void testSaveAndFindBook() {
        // Arrange
        Author author = new Author(null, "Test Author");
        Genre genre = new Genre(null, "Test Genre");
        authorRepository.save(author);
        genreRepository.save(genre);

        Book book = new Book();
        book.setTitle("Test Book");
        book.setIsbn("123456789");
        book.setPublishedDate("2024-01-01");
        book.setAuthors(Set.of(author));
        book.setGenres(Set.of(genre));

        // Act
        Book savedBook = bookRepository.save(book);
        Optional<Book> foundBook = bookRepository.findById(savedBook.getId());

        // Assert
        assertTrue(foundBook.isPresent());
        assertEquals("Test Book", foundBook.get().getTitle());
        assertEquals(1, foundBook.get().getAuthors().size());
        assertEquals(1, foundBook.get().getGenres().size());
    }
}
