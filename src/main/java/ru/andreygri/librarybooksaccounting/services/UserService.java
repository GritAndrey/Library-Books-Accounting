package ru.andreygri.librarybooksaccounting.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.andreygri.librarybooksaccounting.model.Book;
import ru.andreygri.librarybooksaccounting.model.User;
import ru.andreygri.librarybooksaccounting.repository.BookRepository;
import ru.andreygri.librarybooksaccounting.repository.UserRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserService {
    public static final int EXPIRED_DAYS = 864000000;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    @Autowired
    public UserService(UserRepository userRepository, BookRepository bookRepository) {
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    @Transactional
    public User save(User user) {
        return userRepository.save(user);
    }

    @Transactional
    public boolean delete(int id) {
        return userRepository.delete(id) != 0;
    }

    public User get(int id) {
        return userRepository.findById(id).orElse(null);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public List<Book> getUserBooks(int id) {
        final User user = userRepository.getReferenceById(id);
        final List<Book> allByOwner = bookRepository.findAllByOwner(user);
        allByOwner.stream()
                .filter(book -> {
                    long diffInMillis = Math.abs(book.getTakenAt().getTime() - new Date().getTime());
                    return diffInMillis > EXPIRED_DAYS;
                })
                .forEach(book -> book.setExpired(true));
        return allByOwner;
    }

    public Optional<User> getUserByName(String name) {
        return userRepository.getUserByName(name);
    }
}
