package ru.andreygri.librarybooksaccounting.model;

public class Book extends AbstractNamedEntity {

    private String author;
    private String year;
    private Integer userId = null;

    public Book(Integer id, String name, String author, String year) {
        super(id, name);
        this.author = author;
        this.year = year;
    }
    public Book(){
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", year='" + year + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
