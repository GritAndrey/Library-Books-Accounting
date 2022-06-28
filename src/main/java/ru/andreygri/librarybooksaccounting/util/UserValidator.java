package ru.andreygri.librarybooksaccounting.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.andreygri.librarybooksaccounting.model.User;
import ru.andreygri.librarybooksaccounting.repository.UserRepository;

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

            if (repository.getUserByName(user.getName()).isPresent())
                errors.rejectValue("name", "", "Username already exists");
        }
    }

