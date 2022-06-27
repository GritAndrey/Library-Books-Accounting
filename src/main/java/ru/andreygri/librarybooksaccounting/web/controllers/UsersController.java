package ru.andreygri.librarybooksaccounting.web.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.andreygri.librarybooksaccounting.repository.UserRepository;

@Controller
@RequestMapping("/users")
public class UsersController {
    private static final Logger log = LoggerFactory.getLogger(UsersController.class);
    private final UserRepository repository;

    @Autowired
    public UsersController(UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public String getAll(Model model) {
        log.info(" users getAll");
        model.addAttribute("users", repository.getAll());
        return "/users/index";
    }
}
