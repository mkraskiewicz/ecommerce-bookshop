package com.mkraskiewicz.bookstore.service.impl;

import com.mkraskiewicz.bookstore.domain.Book;
import com.mkraskiewicz.bookstore.repository.BookRepository;
import com.mkraskiewicz.bookstore.service.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

    private final BookRepository bookRepository;

    public ImageServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    @Transactional
    public void saveImageFile(Long recipeId, MultipartFile file) {

        try{
            Book book = bookRepository.findById(recipeId).get();

            Byte[] byteObjects = new Byte[file.getBytes().length];
            int itr = 0;

            for(byte b : file.getBytes()){
                byteObjects[itr++] = b;
            }
            book.setImage(byteObjects);

            bookRepository.save(book);
        }catch (IOException exception){
            log.error("Error occured", exception);
            exception.printStackTrace();
        }
    }
}
