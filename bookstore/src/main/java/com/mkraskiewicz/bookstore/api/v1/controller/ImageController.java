package com.mkraskiewicz.bookstore.api.v1.controller;

import com.mkraskiewicz.bookstore.api.v1.model.BookDto;
import com.mkraskiewicz.bookstore.service.BookService;
import com.mkraskiewicz.bookstore.service.ImageService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(ImageController.BASE_URL)
public class ImageController {

    public static final String BASE_URL = "/api/v1/book";
    private final ImageService imageService;
    private final BookService bookService;

    public ImageController(ImageService imageService, BookService bookService) {
        this.imageService = imageService;
        this.bookService = bookService;
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PostMapping("/{bookId}/image")
    @ResponseStatus(HttpStatus.OK)
    public BookDto handleImagePost(@PathVariable String bookId, @RequestParam("file") MultipartFile file){

        imageService.saveImageFile(Long.valueOf(bookId),file);

        return bookService.findById(1L);
    }




    @GetMapping("/{bookId}/image")
    @ResponseStatus(HttpStatus.OK)
    public void renderImageFromDB(@PathVariable String bookId, HttpServletResponse response) throws IOException{

        BookDto bookDto = bookService.findById(Long.valueOf(bookId));

        byte[] byteArray = new byte[bookDto.getImage().length];
        int itr = 0;

        for(Byte wrappedByte : bookDto.getImage()){
            byteArray[itr++] = wrappedByte;
        }

        response.setContentType("image/jpeg");
        InputStream is = new ByteArrayInputStream(byteArray);
        IOUtils.copy(is, response.getOutputStream());
    }
}