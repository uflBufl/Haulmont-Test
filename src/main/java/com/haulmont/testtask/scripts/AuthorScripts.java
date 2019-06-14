package com.haulmont.testtask.scripts;

import com.haulmont.testtask.ConnectToDB;
import com.haulmont.testtask.Requests.AuthorRequests;
import com.haulmont.testtask.entities.Author;

import java.sql.*;
import java.util.ArrayList;

public class AuthorScripts {
    private static Connection con;
    private static PreparedStatement pstmt;
    private static ResultSet rs;

    public AuthorScripts() {
        try {
            this.con = ConnectToDB.GetConnection();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void addAuthor(ArrayList<String> params, ArrayList<String> args) {
        try {
            String sqlRequest = AuthorRequests.getAuthorInsert(args);
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


    public ArrayList<Author> selectFilterAuthors(ArrayList<String> params, ArrayList<String> args) {
        ArrayList<Author> authors = new ArrayList<Author>();
        try {
            if (!args.isEmpty()) {

                String sqlRequest = AuthorRequests.getAuthorSelectWhere(args);
                pstmt = con.prepareStatement(sqlRequest);
                Scripts.setParams(params, pstmt);

            } else {
                String sqlRequest = AuthorRequests.authorSelectAll;
                pstmt = con.prepareStatement(sqlRequest);
            }

            rs = pstmt.executeQuery();

            while (rs.next()) {
                long id = rs.getLong(1);
                String firstName = rs.getString(2);
                String secondName = rs.getString(3);
                String thirdName = rs.getString(4);

                Author author = new Author(id, firstName, secondName, thirdName);
                authors.add(author);
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
        return authors;
    }

    public void deleteAuthor(String authorId) {
        String sqlRequest = AuthorRequests.authorDelete;

        try {
            pstmt = con.prepareStatement(sqlRequest);
            pstmt.setString(1, authorId);
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


    public void updateAuthor(ArrayList<String> params, ArrayList<String> args) {
        try {
            String sqlRequest = AuthorRequests.getAuthorUpdate(args);
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
