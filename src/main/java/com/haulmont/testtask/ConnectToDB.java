package com.haulmont.testtask;

import com.haulmont.testtask.Requests.GenreRequests;
import com.haulmont.testtask.entities.Genre;
import com.haulmont.testtask.scripts.Scripts;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ConnectToDB {

    private static Connection con;

    public static Connection GetConnection(){
    return con;
    }

    public static String Connect(){

        try {
            Class.forName("org.hsqldb.jdbcDriver");
        } catch (ClassNotFoundException e) {
            System.err.println("НЕ удалось загрузить драйвер ДБ.");
            e.printStackTrace();
            System.exit(1);
            return "error";
        }

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(
                    "jdbc:hsqldb:file:dbpath/dbname", "SA", "");
            con = connection;
        } catch (SQLException e) {
            System.err.println("НЕ удалось создать соединение.");
            e.printStackTrace();
            System.exit(1);
        }
        String message = "Main UI";

        try {
            Statement statement = connection.createStatement();
            String query;

            try {

                query = "CREATE TABLE author (id BIGINT IDENTITY PRIMARY KEY , firstName VARCHAR(50) NOT NULL, secondName VARCHAR(50) NOT NULL, thirdName VARCHAR(50) NOT NULL)";

                statement.executeUpdate(query);

                query = "CREATE TABLE genre (id BIGINT IDENTITY PRIMARY KEY , name VARCHAR(50) NOT NULL)";

                statement.executeUpdate(query);

                query = "CREATE TABLE book (id BIGINT IDENTITY PRIMARY KEY , name VARCHAR(50) NOT NULL, author BIGINT NOT NULL, genre BIGINT NOT NULL, publisher VARCHAR(50) NOT NULL, year DATE NOT NULL, city VARCHAR(50) NOT NULL, FOREIGN KEY (author) REFERENCES author(id) ON DELETE RESTRICT ON UPDATE CASCADE, FOREIGN KEY (genre) REFERENCES genre(id) ON DELETE RESTRICT ON UPDATE CASCADE)";

                statement.executeUpdate(query);

                query = "INSERT INTO author (firstName, secondName, thirdName) VALUES('Евгений','Барабанов','Андреевич')";

                statement.executeUpdate(query);

                query = "INSERT INTO genre (name) VALUES('Хорор')";

                statement.executeUpdate(query);

                query = "INSERT INTO book (name, author, genre, publisher, year, city) VALUES('Книга',0,0,'Москва', NOW(),'Самара')";

                statement.executeUpdate(query);

            } catch (SQLException e) {
                System.out.println("Table already created");
                // если таблица уже существует, будет исключение, игнорируем его.
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return message;
    }
}
