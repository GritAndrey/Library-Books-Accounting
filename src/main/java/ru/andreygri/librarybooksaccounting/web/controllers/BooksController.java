package ru.andreygri.librarybooksaccounting.web.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.andreygri.librarybooksaccounting.repository.BookRepository;

@Controller
@RequestMapping("/books")
public class BooksController {
    private static final Logger log = LoggerFactory.getLogger(BooksController.class);
    private final BookRepository repository;

    @Autowired
    public BooksController(BookRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public String getAll(Model model) {
        log.info(" books getAll");
        model.addAttribute("books", repository.getAll());
        return "/books/index";
    }
}
