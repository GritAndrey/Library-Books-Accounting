package ru.andreygri.librarybooksaccounting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.andreygri.librarybooksaccounting.model.Book;
import ru.andreygri.librarybooksaccounting.model.User;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    @Transactional
    @Modifying
    @Query("""
            DELETE FROM Book b WHERE b.id=:id
            """)
    int delete(@Param("id") int id);

    List<Book> findAllByOwner(User user);

    List<Book> findByNameStartingWith(String startName);
}
