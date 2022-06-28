package ru.andreygri.librarybooksaccounting.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class Book extends AbstractNamedEntity {
    @NotEmpty(message = "Author can`t be blank")
    @Size(min = 2, max = 100, message = "Author name length must be between 2 and 100")
    private String author;
    private int year;

    public Book(Integer id, String name, String author, int year) {
        super(id, name);
        this.author = author;
        this.year = year;
    }

    public Book() {
    }


    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", year='" + year + '\'' +
                '}';
    }
}
