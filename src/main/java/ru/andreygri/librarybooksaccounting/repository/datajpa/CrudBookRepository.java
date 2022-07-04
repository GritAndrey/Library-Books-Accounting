package ru.andreygri.librarybooksaccounting.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.andreygri.librarybooksaccounting.model.Book;

public interface CrudBookRepository extends JpaRepository<Book, Integer> {
}
