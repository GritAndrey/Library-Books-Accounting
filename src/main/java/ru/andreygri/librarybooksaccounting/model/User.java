package ru.andreygri.librarybooksaccounting.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "users")
public class User extends AbstractNamedEntity {
    @Column(name = "year")
    private int year;
    @OneToMany(mappedBy = "owner")
    private List<Book> ownedBooks;

    public User() {
    }

    public User(Integer id, String name, int year) {
        super(id, name);
        this.year = year;
    }

    public List<Book> getOwnedBooks() {
        return ownedBooks;
    }

    public void setOwnedBooks(List<Book> ownedBooks) {
        this.ownedBooks = ownedBooks;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", year=" + year +
                '}';
    }
}
