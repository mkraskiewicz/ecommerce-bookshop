package com.mkraskiewicz.bookstore.api.v1.controller;

import com.mkraskiewicz.bookstore.api.v1.model.BookDto;
import com.mkraskiewicz.bookstore.api.v1.model.mapper.BookMapper;
import com.mkraskiewicz.bookstore.service.BookService;
import com.mkraskiewicz.bookstore.service.ImageService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(BookController.BASE_URL)
public class BookController {

    public static final String BASE_URL = "/api/v1/book";
    private final BookService bookService;
    private final BookMapper bookMapper;
    private final  ImageService imageService;

    public BookController(BookService bookService, BookMapper bookMapper, ImageService imageService) {
        this.bookService = bookService;
        this.bookMapper = bookMapper;
        this.imageService = imageService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public BookDto createNewBook(@RequestBody BookDto bookDto) {

        return bookService.createNewBook(bookDto);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<BookDto> getAllBooks() {

        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BookDto getBookById(@PathVariable("id") Long id) {

        return bookService.findById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BookDto updateBook(@PathVariable("id") Long id, @RequestBody BookDto bookDto){

        return bookService.saveBookByDTO(id, bookDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteBook(@PathVariable("id") Long id) {

        bookService.deleteBookById(id);
    }
}
