package by.kagan.elibbootproj.elibra.Models;

public class Book {
    private int id;
    private String name;
    private String author;
    private int numberOfPages;
    private int year;

    public Book() {
    }

    public Book(int id, String name, String author, int numberOfPages, int year) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.numberOfPages = numberOfPages;
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
