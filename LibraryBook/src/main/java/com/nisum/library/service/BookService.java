package com.nisum.library.service;

import com.nisum.library.model.Book;
import com.nisum.library.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private final BookRepository repo;

    public BookService(BookRepository repo) {
        this.repo = repo;
    }

    public List<Book> getAllBooks() {
        return repo.findAll();
    }

    public Book getBook(Long id) {
        return repo.findById(id).orElse(null);
    }

    public Book createBook(Book book) {
        return repo.save(book);
    }

    public Book updateBook(Long id, Book book) {
        book.setId(id);
        return repo.save(book);
    }

    public void deleteBook(Long id) {
        repo.deleteById(id);
    }
}
