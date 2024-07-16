package com.alura.LiterAlura.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    private Integer birthYear;
    private Integer deathYear;
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Book> books;

    public Author() {
    }

    public Author(BookData b) {
        if (!b.author().isEmpty()) {
            this.name = b.author().get(0).name();
            this.birthYear = b.author().get(0).birthYear();
            this.deathYear = b.author().get(0).deathYear();
        }
    }

    @Override
    public String toString() {
        return "Author - " +
                "name='" + name + '\'' +
                ", birthYear=" + birthYear +
                ", deathYear=" + birthYear
//                +
//                ", books=" + (books != null ? books.size() : 0) +
//                '}'
                ;
    }

    public void setBooks(List<Book> books) {
        books.forEach(b -> b.setAuthor(this));
        this.books = books;
    }

    public List<Book> getBooks() {
        return books;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getBirthYear() {
        return birthYear;
    }

    public Integer getDeathYear() {
        return deathYear;
    }
}
