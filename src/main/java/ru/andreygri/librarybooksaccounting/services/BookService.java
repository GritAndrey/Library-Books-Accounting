package ru.andreygri.librarybooksaccounting.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.andreygri.librarybooksaccounting.model.Book;
import ru.andreygri.librarybooksaccounting.model.User;
import ru.andreygri.librarybooksaccounting.repository.BookRepository;

import java.util.Date;
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

    public void update(Book book) {
        final Optional<Book> bookToUpdate = bookRepository.findById(book.getId());
        bookToUpdate.ifPresent(b -> {
            book.setOwner(b.getOwner());
            book.setId(b.getId());
            bookRepository.save(book);
        });
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

    public List<Book> getAll(boolean sort) {
        if(sort) {
            return bookRepository.findAll(Sort.by("year"));
        }
        return bookRepository.findAll();
    }

    public Optional<User> getBookOwner(int id) {
        return bookRepository.findById(id).map(Book::getOwner);
    }

    @Transactional
    public void assign(int id, User user) {
        bookRepository.findById(id).ifPresent(book -> {
            book.setOwner(user);
            book.setTakenAt(new Date());
            bookRepository.save(book);
        });
    }

    @Transactional
    public void release(int id) {
        bookRepository.findById(id).ifPresent(book -> {
            book.setOwner(null);
            book.setTakenAt(null);
            bookRepository.save(book);
        });
    }

    public List<Book> findWithPagination(Integer page, Integer booksPerPage, boolean sort) {
        if (sort)
            return bookRepository.findAll(PageRequest.of(page, booksPerPage, Sort.by("year"))).getContent();
        return bookRepository.findAll(PageRequest.of(page, booksPerPage)).getContent();
    }

    public List<Book> searchByTitle(String query) {
        return bookRepository.findByNameStartingWith(query);
    }
}
