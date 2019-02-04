package com.mkraskiewicz.bookstore.api.v1.model;


import lombok.Data;

import javax.persistence.Lob;

@Data
public class BookDto {

    private Long id;

    private String title;
    private String author;
    private String publisher;
    private String publicationDate;
    private String language;
    private String category;
    private int numberOfPages;
    private String isbn;
    private double price;
    private boolean active;
    private String format;
    private String description;
    private int inStockNumber;
    @Lob
    private Byte[] image;
}
