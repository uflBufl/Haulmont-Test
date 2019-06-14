package com.haulmont.testtask.entities;

import java.util.Date;
import java.util.Objects;

public class Book {
    private long id;
    private String name;
    private long author;
    private long genre;
    private String publisher;
    private Date year;
    private String city;

    public Book(){}

    public Book(long id, String name, long author, long genre, String publisher, Date year, String city){
        this.id = id;
        this.name = name;
        this.author = author;
        this.genre = genre;
        this.publisher = publisher;
        this.year = year;
        this.city = city;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getAuthor() {
        return author;
    }

    public void setAuthor(long author) {
        this.author = author;
    }

    public long getGenre() {
        return genre;
    }

    public void setGenre(long genre) {
        this.genre = genre;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Date getYear() {
        return year;
    }

    public void setYear(Date year) {
        this.year = year;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        Book book = (Book) o;
        return getId() == book.getId() &&
                getAuthor() == book.getAuthor() &&
                getGenre() == book.getGenre() &&
                getName().equals(book.getName()) &&
                getPublisher().equals(book.getPublisher()) &&
                getYear().equals(book.getYear()) &&
                getCity().equals(book.getCity());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getAuthor(), getGenre(), getPublisher(), getYear(), getCity());
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", author=" + author +
                ", genre=" + genre +
                ", publisher='" + publisher + '\'' +
                ", year=" + year +
                ", city='" + city + '\'' +
                '}';
    }
}
