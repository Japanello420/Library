package com.example.library.librarydata.repository;

import com.example.library.librarydata.model.Genre;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class GenreRepositoryTest {

    @Autowired
    private GenreRepository genreRepository;

    @Test
    public void testFindByName() {
        // Arrange
        Genre genre = new Genre(null, "Test Genre");
        genreRepository.save(genre);

        // Act
        Optional<Genre> foundGenre = genreRepository.findByName("Test Genre");

        // Assert
        assertTrue(foundGenre.isPresent());
        assertEquals("Test Genre", foundGenre.get().getName());
    }

    @Test
    public void testSaveGenre() {
        // Arrange
        Genre genre = new Genre(null, "New Genre");

        // Act
        Genre savedGenre = genreRepository.save(genre);

        // Assert
        assertNotNull(savedGenre.getId());
        assertEquals("New Genre", savedGenre.getName());
    }
}
