package com.example.springintro;

import com.example.springintro.model.entity.AgeRestriction;
import com.example.springintro.model.entity.Book;
import com.example.springintro.service.AuthorService;
import com.example.springintro.service.BookService;
import com.example.springintro.service.CategoryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final BookService bookService;
    private final BufferedReader bufferedReader;
    public CommandLineRunnerImpl(CategoryService categoryService, AuthorService authorService, BookService bookService, BufferedReader bufferedReader) {
        this.categoryService = categoryService;
        this.authorService = authorService;
        this.bookService = bookService;
        this.bufferedReader = bufferedReader;
    }

    @Override
    public void run(String... args) throws Exception {
        seedData();

        //printAllBooksAfterYear(2000);
//        printAllAuthorsNamesWithBooksWithReleaseDateBeforeYear(1990);
     //   printAllAuthorsAndNumberOfTheirBooks();
        //pritnALlBooksByAuthorNameOrderByReleaseDate("George", "Powell");
        System.out.println("Select ex:");
        int exNum=Integer.parseInt(bufferedReader.readLine());
        switch (exNum){
            case 1->booksTitlesByAheRest();
            case 2->goldenBook();
            case 3->bookByPrice();
            case 4->notReleaseBooks();
            case 5->booksReleasedBefore();
            case 6->authorsSearch();
            case 7->booksSearch();
            case 8->bookTitleSearch();
            case 9->countBooks();
            case 10->totalBookCopies();
            case 11->reducedBook();

        }
    }

    private void reducedBook() throws IOException {
        System.out.println("Enter title:");
        String title= bufferedReader.readLine();
        bookService.findBookInformationByGivenTitle(title)
                .forEach(System.out::println);
    }

    private void totalBookCopies() {
        authorService.findAllAuthorsAndThereTotalCopies()
                .forEach(System.out::println);
    }

    private void countBooks() throws IOException {
        System.out.println("Enter title length:");
        int titleLength=Integer.parseInt(bufferedReader.readLine());
        System.out.println(bookService.findCountOfBooksWithTitleLengthLongerThan(titleLength));

    }

    private void bookTitleSearch() throws IOException {
        System.out.println("Enter Author last name starts with str:");
        String startStr= bufferedReader.readLine();

        bookService.findAllTitleWithAuthorWithLastNameStartsWith(startStr)
        .forEach(System.out::println);
    }

    private void booksSearch() throws IOException {
        System.out.println("Enter containing str:");
        String str=bufferedReader.readLine();

        bookService.findAllBookTitlesWhereTitleContainsStr(str)
        .forEach(System.out::println);
    }

    private void authorsSearch() throws IOException {
        System.out.println("Enter first name ands with str:");
        String endStr= bufferedReader.readLine();

        authorService.findAuthorFirstNameEndsWithStr(endStr)
        .forEach(System.out::println);
    }

    private void booksReleasedBefore() throws IOException {
        System.out.println("Enter date in format dd-MM-yyyy:");
        LocalDate localDate=LocalDate.parse(bufferedReader.readLine(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        bookService.findAllBooksBeforeDate(localDate)
        .forEach(System.out::println);

    }

    private void notReleaseBooks() throws IOException {
        System.out.println("Enter year:");
        int year=Integer.parseInt(bufferedReader.readLine());
        bookService.findNotReleasedBookTitlesInYear(year)
        .forEach(System.out::println);
    }

    private void bookByPrice() {
        bookService.findAllBookTitlesWithPriceLessThan5orMoreThan40()
        .forEach(System.out::println);
    }

    private void goldenBook() {
        bookService.findALlGoldBookTitlesWithCopiesLessThan5000()
        .forEach(System.out::println);
    }

    private void booksTitlesByAheRest() throws IOException {
        System.out.println("Enter Age Restriction:");
        AgeRestriction ageRestriction=AgeRestriction.valueOf(bufferedReader.readLine().toUpperCase());

        bookService
                .findAllBookTitlesWithAgeRestriction(ageRestriction)
        .forEach(System.out::println);
    }

    private void pritnALlBooksByAuthorNameOrderByReleaseDate(String firstName, String lastName) {
        bookService
                .findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(firstName, lastName)
                .forEach(System.out::println);
    }

    private void printAllAuthorsAndNumberOfTheirBooks() {
        authorService
                .getAllAuthorsOrderByCountOfTheirBooks()
                .forEach(System.out::println);
    }

    private void printAllAuthorsNamesWithBooksWithReleaseDateBeforeYear(int year) {
        bookService
                .findAllAuthorsWithBooksWithReleaseDateBeforeYear(year)
                .forEach(System.out::println);
    }

    private void printAllBooksAfterYear(int year) {
        bookService
                .findAllBooksAfterYear(year)
                .stream()
                .map(Book::getTitle)
                .forEach(System.out::println);
    }

    private void seedData() throws IOException {
        categoryService.seedCategories();
        authorService.seedAuthors();
        bookService.seedBooks();
    }
}
