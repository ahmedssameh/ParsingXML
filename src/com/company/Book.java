package com.company;



public class Book {
    String id;
    private String author;
    private String title;
    private String genre;
    private double price;
    private String date;
    private String description;

    public Book() {
    }

    @Override
    public String toString() {
        return "Book{" +
                "id='" + id + '\'' +
                ", author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", genre='" + genre + '\'' +
                ", price=" + price +
                ", date='" + date + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public Book(String id, String author, String title, String genre, double price, String date, String description) {
        this.id=id;
        this.author = author;
        this.title = title;
        this.genre = genre;
        this.price = price;
        this.date = date;
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
