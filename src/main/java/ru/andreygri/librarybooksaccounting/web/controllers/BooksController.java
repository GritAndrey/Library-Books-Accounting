package ru.andreygri.librarybooksaccounting.web.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.andreygri.librarybooksaccounting.model.Book;
import ru.andreygri.librarybooksaccounting.model.User;
import ru.andreygri.librarybooksaccounting.repository.BookRepository;
import ru.andreygri.librarybooksaccounting.repository.UserRepository;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BooksController {
    private static final Logger log = LoggerFactory.getLogger(BooksController.class);
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @Autowired
    public BooksController(BookRepository repository, UserRepository userRepository) {
        this.bookRepository = repository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public String getAll(Model model) {
        log.info(" books getAll");
        model.addAttribute("books", bookRepository.getAll());
        return "/books/index";
    }

    @GetMapping("/{id}")
    public String getById(@PathVariable int id, Model model, @ModelAttribute("user") User user) {
        model.addAttribute("book", bookRepository.get(id));
        log.info("books getById  Id: " + id + " " + bookRepository.get(id));
        Optional<User> bookOwner = bookRepository.getBookOwner(id);

        if (bookOwner.isPresent())
            model.addAttribute("owner", bookOwner.get());
        else
            model.addAttribute("users", userRepository.getAll());
        return "books/show";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable int id) {
        log.info("DeleteMapping : " + bookRepository.get(id));
        bookRepository.delete(id);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable int id, Model model) {
        final Book book = bookRepository.get(id);
        model.addAttribute("book", book);
        log.info("GetMapping(\"/{id}/edit\")" + book);
        return "/books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult,
                         @PathVariable int id) {

        //bookValidator.validate(book, bindingResult);
        if (bindingResult.hasErrors()) {
            return "/books/edit";
        }
        log.info("Edit Book" + book);
        bookRepository.save(book);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int id) {
        bookRepository.release(id);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") int id, @ModelAttribute("user") User user) {
        bookRepository.assign(id, user);
        return "redirect:/books/" + id;
    }
}
