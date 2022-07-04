package ru.andreygri.librarybooksaccounting.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.andreygri.librarybooksaccounting.model.User;

public interface CrudUserRepository extends JpaRepository<User,Integer> {
}
