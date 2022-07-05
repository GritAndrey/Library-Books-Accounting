package ru.andreygri.librarybooksaccounting.repository.datajpa;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.andreygri.librarybooksaccounting.model.Book;
import ru.andreygri.librarybooksaccounting.model.User;

import java.util.List;
import java.util.Optional;
@Profile("datajpa")
public interface CrudUserRepository extends JpaRepository<User, Integer> {
    @Transactional
    @Modifying
    @Query("""
            DELETE FROM User u WHERE u.id=:id
            """)
    int delete(@Param("id") int id);

    Optional<User> getUserByName(String name);
}
