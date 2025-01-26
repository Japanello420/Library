package com.example.library.librarydata.repository;

import com.example.library.librarydata.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("SELECT b FROM Book b WHERE CAST(b.id AS string) = :identifier OR LOWER(b.title) = LOWER(:identifier)")
    Optional<Book> findByIdOrTitle(@Param("identifier") String identifier);
}
