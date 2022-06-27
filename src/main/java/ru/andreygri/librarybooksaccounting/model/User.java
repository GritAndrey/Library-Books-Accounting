package ru.andreygri.librarybooksaccounting.model;

public class User extends AbstractNamedEntity {
    private int year;

    public User() {
    }

    public User(Integer id, String name, int year) {
        super(id, name);
        this.year = year;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", year=" + year +
                '}';
    }
}
