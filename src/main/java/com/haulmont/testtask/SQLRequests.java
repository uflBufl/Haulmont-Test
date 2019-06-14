package com.haulmont.testtask;

public class SQLRequests {
    public static String createTableAuthor = "CREATE TABLE author (id BIGINT IDENTITY PRIMARY KEY , firstName VARCHAR(50) NOT NULL, secondName VARCHAR(50) NOT NULL, thirdName VARCHAR(50) NOT NULL)";

    public static String createTableGenre = "CREATE TABLE genre (id BIGINT IDENTITY PRIMARY KEY , name VARCHAR(50) NOT NULL)";

    public static String createTableBook = "CREATE TABLE book (id BIGINT IDENTITY PRIMARY KEY , name VARCHAR(50) NOT NULL, author BIGINT NOT NULL, genre BIGINT NOT NULL, publisher VARCHAR(50) NOT NULL, year DATE NOT NULL, city VARCHAR(50) NOT NULL, FOREIGN KEY (author) REFERENCES author(id) ON DELETE RESTRICT ON UPDATE CASCADE, FOREIGN KEY (genre) REFERENCES genre(id) ON DELETE RESTRICT ON UPDATE CASCADE)";

    public static String drop = "DROP TABLE book";

    public static String delete = "DELETE FROM genre WHERE id = 1";

    public static String insert = "INSERT INTO book (name, author, genre, publisher, year, city) VALUES('Книга',0,0,'Журнал', NOW(),'Самара')";

    public static String select = "SELECT id, name FROM genre";
}
