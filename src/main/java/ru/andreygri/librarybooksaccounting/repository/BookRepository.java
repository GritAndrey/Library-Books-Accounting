package ru.andreygri.librarybooksaccounting.repository;


import ru.andreygri.librarybooksaccounting.model.Book;
import ru.andreygri.librarybooksaccounting.model.User;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    // null if not found, when updated
    Book save(Book user);

    // false if not found
    boolean delete(int id);

    // null if not found
    Book get(int id);

    List<Book> getAll();

    Optional<User> getBookOwner(int id);

    void assign(int id, User user);

    void release(int id);
}