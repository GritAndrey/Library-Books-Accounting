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
import ru.andreygri.librarybooksaccounting.services.BookService;
import ru.andreygri.librarybooksaccounting.services.UserService;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BooksController {
    private static final Logger log = LoggerFactory.getLogger(BooksController.class);
    private final BookService bookService;
    private final UserService userService;

    @Autowired
    public BooksController(BookService bookService, UserService userService) {
        this.bookService = bookService;
        this.userService = userService;
    }

    @GetMapping
    public String getAll(Model model, @RequestParam(name = "page", required = false) Integer page,
                         @RequestParam(name = "books_per_page", required = false) Integer booksPerPage,
                         @RequestParam(value = "sort_by_year", required = false) boolean sortByYear) {
        log.info(" books getAll");
        if (page == null || booksPerPage == null) {
            model.addAttribute("books", bookService.getAll(sortByYear));
        } else {
            model.addAttribute("books", bookService.findWithPagination(page, booksPerPage,sortByYear));
        }
        return "/books/index";
    }

    @GetMapping("/{id}")
    public String getById(@PathVariable int id, Model model, @ModelAttribute("user") User user) {
        model.addAttribute("book", bookService.get(id));
        Optional<User> bookOwner = bookService.getBookOwner(id);

        if (bookOwner.isPresent())
            model.addAttribute("owner", bookOwner.get());
        else
            model.addAttribute("users", userService.getAll());
        return "books/show";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable int id) {
        log.info("DeleteMapping : " + bookService.get(id));
        bookService.delete(id);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable int id, Model model) {
        final Book book = bookService.get(id);
        model.addAttribute("book", book);
        log.info("GetMapping(\"/{id}/edit\")" + book);
        return "/books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult,
                         @PathVariable int id) {

        if (bindingResult.hasErrors()) {
            return "/books/edit";
        }
        log.info("Edit Book" + book);
        bookService.update(book);
        return "redirect:/books";
    }

    @GetMapping("/new")
    public String create(@ModelAttribute("book") Book book) {
        return "/books/new";
    }

    @PostMapping
    public String create(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        log.info("Controller POST New Book: " + book);

        if (bindingResult.hasErrors()) {
            return "/books/new";
        }
        bookService.save(book);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int id) {
        bookService.release(id);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") int id, @ModelAttribute("user") User user) {
        bookService.assign(id, user);
        return "redirect:/books/" + id;
    }
    @GetMapping("/search")
    public String searchPage() {
        return "books/search";
    }

    @PostMapping("/search")
    public String makeSearch(Model model, @RequestParam("query") String query) {
        model.addAttribute("books", bookService.searchByTitle(query));
        return "books/search";
    }
}
