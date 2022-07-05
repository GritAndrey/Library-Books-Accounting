package ru.andreygri.librarybooksaccounting.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import ru.andreygri.librarybooksaccounting.model.Book;
import ru.andreygri.librarybooksaccounting.model.User;
import ru.andreygri.librarybooksaccounting.repository.UserRepository;

import java.util.List;
import java.util.Optional;
@Profile("datajpa")
@Repository
public class DataJpaUserRepository implements UserRepository {
    private final CrudUserRepository repository;

    @Autowired
    public DataJpaUserRepository(CrudUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User save(User user) {
        return repository.save(user);
    }

    @Override
    public boolean delete(int id) {
        return repository.delete(id) != 0;
    }

    @Override
    public User get(int id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<User> getAll() {
        return repository.findAll();
    }

    @Override
    public List<Book> getUserBooks(int id) {
        return repository.findById(id).orElse(null).getOwnedBooks();
    }

    @Override
    public Optional<User> getUserByName(String name) {
        return repository.getUserByName(name);
    }
}
