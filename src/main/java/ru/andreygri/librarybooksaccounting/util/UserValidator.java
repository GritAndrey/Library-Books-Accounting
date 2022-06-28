package ru.andreygri.librarybooksaccounting.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.andreygri.librarybooksaccounting.model.User;
import ru.andreygri.librarybooksaccounting.repository.UserRepository;

import java.util.Objects;
import java.util.Optional;

@Component
public class UserValidator implements Validator {

    private final UserRepository repository;

    @Autowired
    public UserValidator(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;
        final Optional<User> userByName = repository.getUserByName(user.getName());
        if (userByName.isPresent() && !Objects.equals(userByName.get().getId(), user.getId()))
            errors.rejectValue("name", "", "Username already exists");
    }
}
