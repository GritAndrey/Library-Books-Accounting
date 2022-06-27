package ru.andreygri.librarybooksaccounting.repository;


import ru.andreygri.librarybooksaccounting.model.Book;

import java.util.List;

public interface BookRepository {
    // null if not found, when updated
    Book save(Book user);

    // false if not found
    boolean delete(int id);

    // null if not found
    Book get(int id);

    List<Book> getAll();
}