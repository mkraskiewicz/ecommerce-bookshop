package com.mkraskiewicz.bookstore.service.impl;

import com.mkraskiewicz.bookstore.api.v1.model.BookDto;
import com.mkraskiewicz.bookstore.api.v1.model.mapper.BookMapper;
import com.mkraskiewicz.bookstore.domain.Book;
import com.mkraskiewicz.bookstore.exceptions.ResourceNotFoundException;
import com.mkraskiewicz.bookstore.repository.BookRepository;
import com.mkraskiewicz.bookstore.service.BookService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public BookServiceImpl(BookRepository bookRepository, BookMapper bookMapper) {

        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    @Override
    public List<BookDto> getAllBooks() {

        return bookRepository.findAll()
                .stream()
                .map(bookMapper::bookToBookDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookDto> getAllActiveBooks() {

        return bookRepository.findAll()
                .stream().filter(Book::isActive)
                .map(bookMapper::bookToBookDto)
                .collect(Collectors.toList());
    }

    @Override
    public BookDto findById(Long id) {

        return bookRepository.findById(id)
                .map(bookMapper::bookToBookDto)
                .orElseThrow(ResourceNotFoundException::new);

    }

    @Override
    public BookDto createNewBook(BookDto bookDto) {

        return saveAndReturnDto(bookMapper.bookDtoToBook(bookDto));
    }

    @Override
    public List<BookDto> blurrySearch(String title) {
        return bookRepository.findByTitleContaining(title)
                .stream().filter(Book::isActive)
                .map(bookMapper::bookToBookDto)
                .collect(Collectors.toList());
    }

    @Override
    public BookDto saveBookByDTO(Long id, BookDto bookDto) {

        Book book = bookMapper.bookDtoToBook(bookDto);
        book.setId(id);
        return saveAndReturnDto(book);
    }

    @Override
    public BookDto findByIsbn(String isbn) {

        return bookRepository.findByIsbn(isbn)
                .map(bookMapper::bookToBookDto)
                .orElseThrow(ResourceNotFoundException::new);
    }

    private BookDto saveAndReturnDto(Book bookToSave) {

        Book savedBook = bookRepository.save(bookToSave);
        return  bookMapper.bookToBookDto(savedBook);
    }

    @Override
    public void deleteBookById(Long id) {

        bookRepository.deleteById(id);
    }
}
