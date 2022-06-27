package ru.andreygri.librarybooksaccounting.web.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.andreygri.librarybooksaccounting.model.Book;
import ru.andreygri.librarybooksaccounting.repository.BookRepository;

import javax.validation.Valid;

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

    @GetMapping("/{id}")
    public String getById(@PathVariable int id, Model model) {
        log.info("books getById  Id: " + id);
        model.addAttribute("book", repository.get(id));
        return "books/show";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable int id) {
        log.info("DeleteMapping : " + repository.get(id));
        repository.delete(id);
        return "redirect:/books";
    }
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable int id, Model model) {
        final Book book = repository.get(id);
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
        repository.save(book);
        return "redirect:/books";
    }
}
