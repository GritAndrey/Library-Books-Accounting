package ru.andreygri.librarybooksaccounting.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.andreygri.librarybooksaccounting.model.Book;
import ru.andreygri.librarybooksaccounting.model.User;
import ru.andreygri.librarybooksaccounting.repository.BookRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Transactional
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Transactional
    public boolean delete(int id) {
        return bookRepository.delete(id) != 0;
    }

    public Book get(int id) {
        return bookRepository.findById(id).orElse(null);
    }

    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    public Optional<User> getBookOwner(int id) {
        return bookRepository.findById(id).map(Book::getOwner);
    }

    @Transactional
    public void assign(int id, User user) {
        final Book book = bookRepository.getReferenceById(id);
        book.setOwner(user);
        bookRepository.save(book);
    }

    @Transactional
    public void release(int id) {
        final Book book = bookRepository.getReferenceById(id);
        book.setOwner(null);
        bookRepository.save(book);
    }
}
