package com.alura.LiterAlura.principal;

import com.alura.LiterAlura.model.*;
import com.alura.LiterAlura.repository.IAuthorRepository;
import com.alura.LiterAlura.repository.IBookRepository;
import com.alura.LiterAlura.service.ConsumoAPI;
import com.alura.LiterAlura.service.ConvierteDatos;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Principal {
    private Scanner keyboard = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books/";
    private ConvierteDatos conversor = new ConvierteDatos();
    private IBookRepository bookRepository;
    private IAuthorRepository authorRepository;
    private List<Book> books;
    private List<Author> authors;

    public Principal(IBookRepository bookRepository, IAuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public void showMenu() {
        var option = -1;
        while (option != 0) {
            var menu = """
                    ----- WELCOME! ------
                    1 - Search book by title
                    2 - List all registered books
                    3 - List all registered authors
                    4 - List alive authors at a certain date (year)
                    5 - List books by language
                    6 - List top 10 most downloaded books
                                       
                                       
                    15 - TESTS!!!
                                  
                    0 - Exit
                    Enter the option:
                    >>>
                    """;
            System.out.println(menu);
            option = keyboard.nextInt();
            keyboard.nextLine();

            switch (option) {
                case 1:
                    getBookData();
                    break;
                case 2:
                    showBooks();
                    break;
                case 3:
                    listAllAuthors();
                    break;
                case 4:
                    listAllAuthorsAlive();
                    break;
                case 5:
                    listBooksByLanguage();
                    break;
                case 6:
                    listTop10MostDownloadedBooks();
                    break;

                case 0:
                    System.out.println(">>> Closing programm ... byee");
                    break;
                default:
                    System.out.println(">>> Invalid option >:| ");
            }
        }

    }

    private BookData getBookData() {
        Scanner teclado = new Scanner(System.in);
        System.out.println("Enter the name of the book you wish to search for: ");
        var bookName = teclado.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + "?search=" + bookName.replace(" ", "%20"));
        // System.out.println(json);

        BookSearch bookSearch = conversor.obtenerDatos(json, BookSearch.class);

        if (bookSearch.getResults().size() > 0) {
            BookData firstBook = bookSearch.getResults().get(0);
            System.out.println(
                    " >>> Title: " + firstBook.title() +
                            " - Author: " + firstBook.author().get(0).name() +
                            " - Download Count: " + firstBook.downloadCount() +
                            " - Language: " + String.join(", ", firstBook.languages())

            );
            saveBook(firstBook);
            return firstBook;
        }
        System.out.println("No data found for the specified book.");
        return null;

    }
    private void showBooks(){
        books = bookRepository.findAll();
        // System.out.println(books);
        books.stream()
                .sorted(Comparator.comparing(Book::getLanguage))
                .forEach(System.out::println);
    }
    private void saveBook(BookData b){
        Author author = new Author();
        if (b.author() != null && !b.author().isEmpty()){
            String authorName = b.author().get(0).name();
            author = authorRepository.findByName(authorName);
            if (author == null){
                author = new Author(b);
                authorRepository.save(author);
            }
        }
        Book book = new Book(b);
        book.setAuthor(author);
        bookRepository.save(book);
    }
    private void listAllAuthors(){
        authors = authorRepository.findAll();
        authors.stream()
                .forEach(System.out::println);
    }
    private void  listAllAuthorsAlive(){
        System.out.println("Enter the year (example: 2022) you want to consult for alive authors");
        var inputYear = keyboard.nextInt();
        List<Author> authorsAlive = authorRepository.findByBirthYearLessThanEqualAndDeathYearGreaterThanEqualOrDeathYearIsNull(inputYear, inputYear);
        if (authorsAlive.size() > 0){
            System.out.println("Authors found alive year " + inputYear);
            authorsAlive.stream()
                    .sorted(Comparator.comparing(Author::getName))
                    .forEach(System.out::println);
        } else {
            System.out.println("No authors found to be alive in the year "+inputYear);
        }
    }
    private void listBooksByLanguage(){
        System.out.println("Enter the language you wish to search books for, input must be two-character language codes");
        var input = keyboard.nextLine();
        var language = Language.fromString(input);
        List<Book> filteredBooks = bookRepository.findByLanguage(language);
        System.out.println("Results for language: "+language+" search are: ");
        filteredBooks.forEach(System.out::println);
    }
    private void listTop10MostDownloadedBooks(){
        List<Book> top10Books = bookRepository.findTop10ByOrderByDownloadCountDesc();
        if (!top10Books.isEmpty()){
            System.out.println("The top 10 most downloaded books are: ");
            top10Books.forEach(System.out::println);
        } else {
            System.out.println("Error :(");
        }
    }
}
