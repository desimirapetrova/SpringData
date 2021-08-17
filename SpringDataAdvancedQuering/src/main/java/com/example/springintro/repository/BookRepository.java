package com.example.springintro.repository;

import com.example.springintro.model.entity.AgeRestriction;
import com.example.springintro.model.entity.Book;
import com.example.springintro.model.entity.EditionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAllByReleaseDateAfter(LocalDate releaseDateAfter);

    List<Book> findAllByReleaseDateBefore(LocalDate releaseDateBefore);

    List<Book> findAllByAuthor_FirstNameAndAuthor_LastNameOrderByReleaseDateDescTitle(String author_firstName, String author_lastName);
    List<Book>findAllByAgeRestriction(AgeRestriction ageRestriction);
    List<Book>findAllByEditionTypeAndCopiesLessThan(EditionType editionType,Integer copies);
    List<Book>findAllByPriceLessThanOrPriceGreaterThan(BigDecimal lower, BigDecimal upper);
    List<Book>findAllByReleaseDateBeforeOrReleaseDateAfter(LocalDate lower, LocalDate upper);
    List<Book>findByTitleContaining(String contStr);
    List<Book>findAllByAuthor_LastNameStartsWith(String startStr);
    @Query("SELECT count (b) from  Book b where length(b.title)>:param")
    int countBooksWithTitleLengthMoreThan(@Param(value ="param") int titleLength);

    @Query("select b from Book  b where b.title=:param")
    List<Book> findBookInformation(@Param(value = "param") String title);
}
