package ru.andreygri.librarybooksaccounting.model;

import jakarta.persistence.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "book")
public class Book extends AbstractNamedEntity {
    @Column(name = "author")
    @NotEmpty(message = "Author can`t be blank")
    @Size(min = 2, max = 100, message = "Author name length must be between 2 and 100")
    private String author;

    @Column(name = "year")
    private int year;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User owner;

    public Book(Integer id, String name, String author, int year) {
        super(id, name);
        this.author = author;
        this.year = year;
    }

    public Book() {
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
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
