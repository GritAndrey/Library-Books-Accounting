package ru.andreygri.librarybooksaccounting.repository.datajpa;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.andreygri.librarybooksaccounting.model.Book;
import ru.andreygri.librarybooksaccounting.model.User;

import java.util.Optional;
@Profile("datajpa")
public interface CrudBookRepository extends JpaRepository<Book, Integer> {
    @Modifying
    @Query("""
            DELETE FROM Book b WHERE b.id=:id
            """)
    int delete(@Param("id") int id);

    Optional<User> findByOwnerId(int id);
}
