package com.example.library.librarydata.repository;

import com.example.library.librarydata.model.Author;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    public void testFindByName() {
        // Arrange
        Author author = new Author(null, "Test Author");
        authorRepository.save(author);

        // Act
        Optional<Author> foundAuthor = authorRepository.findByName("Test Author");

        // Assert
        assertTrue(foundAuthor.isPresent());
        assertEquals("Test Author", foundAuthor.get().getName());
    }

    @Test
    public void testSaveAuthor() {
        // Arrange
        Author author = new Author(null, "New Author");

        // Act
        Author savedAuthor = authorRepository.save(author);

        // Assert
        assertNotNull(savedAuthor.getId());
        assertEquals("New Author", savedAuthor.getName());
    }
}
