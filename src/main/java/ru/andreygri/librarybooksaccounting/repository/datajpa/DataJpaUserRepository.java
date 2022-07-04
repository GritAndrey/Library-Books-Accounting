package ru.andreygri.librarybooksaccounting.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.andreygri.librarybooksaccounting.model.Book;
import ru.andreygri.librarybooksaccounting.model.User;
import ru.andreygri.librarybooksaccounting.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Repository
public class DataJpaUserRepository implements UserRepository {
    private final CrudUserRepository repository;

    @Autowired
    public DataJpaUserRepository(CrudUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User save(User user) {
        return null;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public User get(int id) {
        return null;
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public List<Book> getUserBooks(int id) {
        return null;
    }

    @Override
    public Optional<User> getUserByName(String name) {
        return Optional.empty();
    }
}
