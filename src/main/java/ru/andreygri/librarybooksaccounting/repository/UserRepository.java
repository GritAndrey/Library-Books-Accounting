package ru.andreygri.librarybooksaccounting.repository;



import ru.andreygri.librarybooksaccounting.model.Book;
import ru.andreygri.librarybooksaccounting.model.User;

import java.util.List;

public interface UserRepository {
    // null if not found, when updated
    User save(User user);

    // false if not found
    boolean delete(int id);

    // null if not found
    User get(int id);

    List<User> getAll();

   List<Book> getUserBooks(int id);
}