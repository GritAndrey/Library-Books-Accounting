package ru.andreygri.librarybooksaccounting.web.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.andreygri.librarybooksaccounting.model.User;
import ru.andreygri.librarybooksaccounting.repository.UserRepository;

import javax.validation.Valid;

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

    @GetMapping("/{id}")
    public String getById(@PathVariable int id, Model model) {
        log.info("users getById  Id: " + id);
        model.addAttribute("user", repository.get(id));
        model.addAttribute("books", repository.getUserBooks(id));
        return "users/show";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable int id) {
        log.info("DeleteMapping : " + repository.get(id));
        repository.delete(id);
        return "redirect:/users";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable int id, Model model) {
        final User user = repository.get(id);
        model.addAttribute("user", user);
        log.info("GetMapping(\"/{id}/edit\")" + user);
        return "/users/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") @Valid User user,
                         BindingResult bindingResult,
                         @PathVariable int id) {

        //userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "/users/edit";
        }
        log.info("Edit user" + user);
        repository.save(user);
        return "redirect:/users";
    }
}
