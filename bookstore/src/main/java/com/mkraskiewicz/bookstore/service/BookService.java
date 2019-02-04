package com.mkraskiewicz.bookstore.service;

import com.mkraskiewicz.bookstore.api.v1.model.BookDto;

import java.util.List;

public interface BookService {

    List<BookDto> getAllBooks();
    List<BookDto> getAllActiveBooks();
    BookDto findById(Long id);
    BookDto createNewBook(BookDto bookDto);
    List<BookDto> blurrySearch(String title);
    void deleteBookById(Long id);
    BookDto findByIsbn(String isbn);
    BookDto saveBookByDTO(Long id, BookDto bookDto);
}
