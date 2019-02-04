package com.mkraskiewicz.bookstore.api.v1.model.mapper;


import com.mkraskiewicz.bookstore.api.v1.model.BookDto;
import com.mkraskiewicz.bookstore.domain.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BookMapper {

    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    @Mapping(source ="id", target ="id")
    BookDto bookToBookDto(Book book);

    @Mapping(source = "id", target = "id")
    Book bookDtoToBook(BookDto bookDto);
}
