package com.example.library.librarywebapi.controller;

import com.example.library.librarydata.model.Author;
import com.example.library.librarydata.model.Book;
import com.example.library.librarydata.model.Genre;
import com.example.library.librarydata.repository.BookRepository;
import com.example.library.librarydata.repository.AuthorRepository;
import com.example.library.librarydata.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Set;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/api/books")
public class BookController {

    private final BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("/getALL")
    public String getAllBooksHtml(Model model) {
        model.addAttribute("books", bookRepository.findAll());
        return "books-table";
    }

    @GetMapping("/delete")
    public String getDeleteForm() {
        return "delete-books";
    }

    @PostMapping("/delete")
    public String deleteBook(@RequestParam("identifier") String identifier, Model model) {
        Optional<Book> bookToDelete = bookRepository.findByIdOrTitle(identifier);
        if (bookToDelete.isPresent()) {
            bookRepository.delete(bookToDelete.get());
            model.addAttribute("message", "Book deleted successfully.");
        } else {
            model.addAttribute("error", "Book not found with ID or Title: " + identifier);
        }
        return "delete-books";
    }

    @GetMapping("/edit")
    public String getEditForm() {
        return "edit-book";
    }

    @PostMapping("/edit/form")
    public String editBook(
            @RequestParam("identifier") String identifier,
            @RequestParam("title") String title,
            @RequestParam("isbn") String isbn,
            @RequestParam("publishedDate") String publishedDate,
            @RequestParam("authors") String authors,
            @RequestParam("genres") String genres,
            Model model) {

        Optional<Book> bookToEdit = bookRepository.findByIdOrTitle(identifier);

        if (bookToEdit.isPresent()) {
            Book book = bookToEdit.get();
            book.setTitle(title);
            book.setIsbn(isbn);
            book.setPublishedDate(publishedDate);

            // Automatyczne dodawanie autorów
            if (!authors.isBlank()) {
                Set<Author> authorSet = parseAndSaveAuthors(authors);
                book.setAuthors(authorSet);
            }

            // Automatyczne dodawanie gatunków
            if (!genres.isBlank()) {
                Set<Genre> genreSet = parseAndSaveGenres(genres);
                book.setGenres(genreSet);
            }

            bookRepository.save(book);
            model.addAttribute("message", "Book updated successfully.");
        } else {
            model.addAttribute("error", "Book not found with ID or Title: " + identifier);
        }

        return "edit-book";
    }

    @GetMapping("/add")
    public String showAddBookForm() {
        return "add-book";
    }

    @PostMapping("/add")
    public String addBook(
            @RequestParam("title") String title,
            @RequestParam("isbn") String isbn,
            @RequestParam("publishedDate") String publishedDate,
            @RequestParam("authors") String authors,
            @RequestParam("genres") String genres,
            Model model) {

        Book newBook = new Book();
        newBook.setTitle(title);
        newBook.setIsbn(isbn);
        newBook.setPublishedDate(publishedDate);

        // Parsuj i zapisuj autorów
        Set<Author> authorSet = parseAndSaveAuthors(authors);
        newBook.setAuthors(authorSet);

        // Parsuj i zapisuj gatunki
        Set<Genre> genreSet = parseAndSaveGenres(genres);
        newBook.setGenres(genreSet);

        // Zapisz książkę w bazie danych
        bookRepository.save(newBook);
        model.addAttribute("message", "Book added successfully.");

        return "add-book";
    }

    private Set<Author> parseAndSaveAuthors(String authors) {
        String[] authorNames = authors.split(",");
        return Arrays.stream(authorNames)
                .map(String::trim)
                .map(name -> {
                    // Sprawdź, czy autor istnieje w bazie, jeśli nie, zapisz nowego
                    return authorRepository.findByName(name)
                            .orElseGet(() -> authorRepository.save(new Author(null, name)));
                })
                .collect(Collectors.toSet());
    }

    private Set<Genre> parseAndSaveGenres(String genres) {
        String[] genreNames = genres.split(",");
        return Arrays.stream(genreNames)
                .map(String::trim)
                .map(name -> {
                    // Sprawdź, czy gatunek istnieje w bazie, jeśli nie, zapisz nowy
                    return genreRepository.findByName(name)
                            .orElseGet(() -> genreRepository.save(new Genre(null, name)));
                })
                .collect(Collectors.toSet());
    }
}