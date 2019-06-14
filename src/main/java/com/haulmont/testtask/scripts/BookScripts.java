package com.haulmont.testtask.scripts;

import com.haulmont.testtask.ConnectToDB;
import com.haulmont.testtask.Requests.BookRequests;
import com.haulmont.testtask.entities.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class BookScripts {
    private static Connection con;
    private static PreparedStatement pstmt;
    private static ResultSet rs;

    public BookScripts() {
        try {
            this.con = ConnectToDB.GetConnection();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void addBook(ArrayList<String> params, ArrayList<String> args) {

        try {
            String sqlRequest = BookRequests.getBookInsert(args);
            pstmt = con.prepareStatement(sqlRequest);
            Scripts.setParams(params, pstmt);
            pstmt.executeUpdate();

        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                pstmt.close();
            } catch (SQLException se) { /*can't do anything */ }
        }
    }

    public ArrayList<Book> selectFilterBooks(ArrayList<String> params, ArrayList<String> args) {
        ArrayList<Book> books = new ArrayList<Book>();
        try {
            if (!args.isEmpty()) {

                String sqlRequest = BookRequests.getBookSelectWhere(args);

                System.out.println(sqlRequest);

                pstmt = con.prepareStatement(sqlRequest);
                Scripts.setParams(params, pstmt);

                System.out.println(pstmt.toString());

            } else {
                String sqlRequest = BookRequests.bookSelectAll;
                pstmt = con.prepareStatement(sqlRequest);
            }

            rs = pstmt.executeQuery();

            while (rs.next()) {
                long id = rs.getLong(1);
                String name = rs.getString(2);
                long author = rs.getLong(3);
                long genre = rs.getLong(4);
                String publisher = rs.getString(5);
                Date year = rs.getDate(6);
                String city = rs.getString(7);

                Book book = new Book(id, name, author, genre, publisher, year, city);
                books.add(book);
            }

        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                pstmt.close();
            } catch (SQLException se) { /*can't do anything */ }
            try {
                rs.close();
            } catch (SQLException se) { /*can't do anything */ }
        }
        return books;
    }

    public void deleteBook(String bookId) {
        String sqlRequest = BookRequests.bookDelete;

        try {
            pstmt = con.prepareStatement(sqlRequest);
            pstmt.setString(1, bookId);
            pstmt.executeUpdate();

        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                pstmt.close();
            } catch (SQLException se) { /*can't do anything */ }
        }

    }

    public void updateBook(ArrayList<String> params, ArrayList<String> args) {
        try {
            String sqlRequest = BookRequests.getBookUpdate(args);
            pstmt = con.prepareStatement(sqlRequest);
            Scripts.setParams(params, pstmt);

            pstmt.executeUpdate();

        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                pstmt.close();
            } catch (SQLException se) { /*can't do anything */ }
        }
    }
}
