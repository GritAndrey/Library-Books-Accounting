package ru.andreygri.librarybooksaccounting.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.andreygri.librarybooksaccounting.model.Book;
import ru.andreygri.librarybooksaccounting.model.User;
import ru.andreygri.librarybooksaccounting.repository.BookRepository;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book save(Book book) {
        return bookRepository.save(book);
    }

    public boolean delete(int id) {
        return bookRepository.delete(id);
    }

    public Book get(int id) {
        return bookRepository.get(id);
    }

    public List<Book> getAll() {
        return bookRepository.getAll();
    }

    public Optional<User> getBookOwner(int id) {
        return bookRepository.getBookOwner(id);
    }

    public void assign(int id, User user) {
        bookRepository.assign(id, user);
    }

    public void release(int id) {
        bookRepository.release(id);
    }
}
