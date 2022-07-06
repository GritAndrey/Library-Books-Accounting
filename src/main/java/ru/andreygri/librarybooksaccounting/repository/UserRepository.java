package ru.andreygri.librarybooksaccounting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.andreygri.librarybooksaccounting.model.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Transactional
    @Modifying
    @Query("""
            DELETE FROM User u WHERE u.id=:id
            """)
    int delete(@Param("id") int id);

    Optional<User> getUserByName(String name);
}
