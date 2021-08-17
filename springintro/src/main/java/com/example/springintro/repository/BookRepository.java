package com.example.springintro.repository;

import com.example.springintro.model.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface BookRepository extends JpaRepository<Book,Long> {

    List<Book>findAllByReleaseDateAfter(LocalDate releaseDateAfter);
    List<Book>findAllByReleaseDateBefore(LocalDate releaseDateBefore);
    List<Book>findAllByAuthor_FirstNameAndAuthor_LastNameOrderByReleaseDateDescTitle(String firstName,String lastName);
}
