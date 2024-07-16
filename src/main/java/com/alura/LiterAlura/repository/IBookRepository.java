package com.alura.LiterAlura.repository;


import com.alura.LiterAlura.model.Book;
import com.alura.LiterAlura.model.Language;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IBookRepository extends JpaRepository<Book, Long> {
    List<Book> findByLanguage(Language language);
    List<Book> findTop10ByOrderByDownloadCountDesc();
}
