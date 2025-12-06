package com.example.springbootflyway.controllers;

import com.example.springbootflyway.dao.BookRepository;
import com.example.springbootflyway.models.Book;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping
    public Book create() {
        Book book = new Book();
        book.setId(1L);
        book.setName("Flyway 정복하기");
        book.setAuthor("원저자");

        bookRepository.save(book);
        return book;
    }
}
