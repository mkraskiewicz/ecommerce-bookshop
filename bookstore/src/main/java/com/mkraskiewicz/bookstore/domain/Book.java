package com.mkraskiewicz.bookstore.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Book {


    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
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
    private boolean active=true;
    private String format;

    @Column(columnDefinition="text")
    private String description;
    private int inStockNumber;
    @Lob
    private Byte[] image;

}
