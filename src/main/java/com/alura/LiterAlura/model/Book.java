package com.alura.LiterAlura.model;

import jakarta.persistence.*;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String title;
    @Enumerated(EnumType.STRING)
    private Language language;
    private Integer downloadCount;
    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    public Book() {    }

    public Book(BookData b) {
        this.title = b.title();
        this.language = Language.fromString(b.languages().get(0).trim());
        this.downloadCount = b.downloadCount();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Integer getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(Integer downloadCount) {
        this.downloadCount = downloadCount;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Book: " +
                " title='" + title + '\'' +
                ", language=" + language +
                ", download_count=" + downloadCount +
                ", " + (author != null ? getAuthor() : "N/A") +
                '}';
    }
}
