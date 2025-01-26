package com.example.library.libraryupdater.Service;

import com.example.library.librarydata.model.Author;
import com.example.library.librarydata.model.Genre;
import com.example.library.librarydata.model.Book;
import com.example.library.librarydata.repository.AuthorRepository;
import com.example.library.librarydata.repository.BookRepository;
import com.example.library.librarydata.repository.GenreRepository;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.library.libraryupdater.Client.GoogleBooksClient;

import java.util.HashSet;
import java.util.Set;

@Service
public class UpdaterService {
    private final GoogleBooksClient googleBooksClient;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final BookRepository bookRepository;

    public UpdaterService(GoogleBooksClient googleBooksClient, AuthorRepository authorRepository, GenreRepository genreRepository, BookRepository bookRepository) {
        this.googleBooksClient = googleBooksClient;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
        this.bookRepository = bookRepository;
    }

    @Transactional
    public void updateData() {
        String response = googleBooksClient.fetchBooks();

        JsonObject jsonResponse = JsonParser.parseString(response).getAsJsonObject();
        JsonArray items = jsonResponse.getAsJsonArray("items");

        if (items != null) {
            for (JsonElement item : items) {
                JsonObject volumeInfo = item.getAsJsonObject().getAsJsonObject("volumeInfo");

                // Authors
                Set<Author> authors = new HashSet<>();
                if (volumeInfo.has("authors")) {
                    for (JsonElement authorName : volumeInfo.getAsJsonArray("authors")) {
                        String name = authorName.getAsString();
                        Author author = authorRepository.findByName(name).orElseGet(() -> new Author(null, name));
                        authors.add(author);
                        authorRepository.save(author);
                    }
                }

                // Genres (Categories)
                Set<Genre> genres = new HashSet<>();
                if (volumeInfo.has("categories")) {
                    for (JsonElement category : volumeInfo.getAsJsonArray("categories")) {
                        String genreName = category.getAsString();
                        Genre genre = genreRepository.findByName(genreName).orElseGet(() -> new Genre(null, genreName));
                        genres.add(genre);
                        genreRepository.save(genre);
                    }
                }

                // Books
                if (volumeInfo.has("title")) {
                    String title = volumeInfo.get("title").getAsString();
                    String publishedDate = volumeInfo.has("publishedDate") ? volumeInfo.get("publishedDate").getAsString() : null;
                    String isbn = null;

                    if (volumeInfo.has("industryIdentifiers")) {
                        JsonArray identifiers = volumeInfo.getAsJsonArray("industryIdentifiers");
                        for (JsonElement identifier : identifiers) {
                            JsonObject idObj = identifier.getAsJsonObject();
                            if ("ISBN_13".equals(idObj.get("type").getAsString())) {
                                isbn = idObj.get("identifier").getAsString();
                                break;
                            }
                        }
                    }

                    Book book = new Book();
                    book.setTitle(title);
                    book.setIsbn(isbn);
                    book.setPublishedDate(publishedDate);
                    book.setAuthors(authors);
                    book.setGenres(genres);
                    bookRepository.save(book);
                    System.out.println("Saving book: " + title);
                }
            }
        } else {
            System.out.println("No items found in the response.");
        }
    }
}