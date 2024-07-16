package com.alura.LiterAlura.repository;

import com.alura.LiterAlura.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IAuthorRepository extends JpaRepository<Author, Long> {
    Author findByName(String name);
    List<Author> findByBirthYearLessThanEqualAndDeathYearGreaterThanEqualOrDeathYearIsNull(Integer yeat, Integer year2);
}
