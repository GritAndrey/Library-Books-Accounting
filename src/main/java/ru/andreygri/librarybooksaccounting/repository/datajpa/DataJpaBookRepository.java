package ru.andreygri.librarybooksaccounting.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.andreygri.librarybooksaccounting.model.Book;
import ru.andreygri.librarybooksaccounting.model.User;
import ru.andreygri.librarybooksaccounting.repository.BookRepository;

import java.util.List;
import java.util.Optional;

@Repository
public class DataJpaBookRepository implements BookRepository {
    private final CrudBookRepository repository;

    @Autowired(required = false)
    public DataJpaBookRepository(CrudBookRepository repository) {
        this.repository = repository;
    }

    @Override
    public Book save(Book book) {
        return null;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public Book get(int id) {
        return null;
    }

    @Override
    public List<Book> getAll() {
        return null;
    }

    @Override
    public Optional<User> getBookOwner(int id) {
        return Optional.empty();
    }

    @Override
    public void assign(int id, User user) {
    }

    @Override
    public void release(int id) {

    }
}
