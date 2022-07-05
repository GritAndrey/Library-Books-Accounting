package ru.andreygri.librarybooksaccounting.repository.datajpa;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.andreygri.librarybooksaccounting.model.Book;
import ru.andreygri.librarybooksaccounting.model.User;
import ru.andreygri.librarybooksaccounting.repository.BookRepository;

import java.util.List;
import java.util.Optional;
@Profile("datajpa")
@Repository
public class DataJpaBookRepository implements BookRepository {
    private final CrudBookRepository repository;

    @Autowired(required = false)
    public DataJpaBookRepository(CrudBookRepository repository) {
        this.repository = repository;
    }

    @Override
    public Book save(Book book) {
        return repository.save(book);
    }

    @Override
    public boolean delete(int id) {
        return repository.delete(id) != 0;
    }

    @Override
    public Book get(int id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<Book> getAll() {
        return repository.findAll();
    }

    @Override
    public Optional<User> getBookOwner(int id) {
        return repository.findByOwnerId(id);
    }

    @Override
    public void assign(int id, User user) {
        final Book book = repository.getReferenceById(id);
        Hibernate.initialize(book.getOwner());
        book.setOwner(user);
        repository.save(book);
    }

    @Override
    public void release(int id) {
        final Book book = repository.getReferenceById(id);
        book.setOwner(null);
        repository.save(book);
    }
}
