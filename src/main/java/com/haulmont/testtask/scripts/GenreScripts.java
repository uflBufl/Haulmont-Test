package com.haulmont.testtask.scripts;

import com.haulmont.testtask.ConnectToDB;
import com.haulmont.testtask.Requests.GenreRequests;
import com.haulmont.testtask.entities.CountGenresDTO;
import com.haulmont.testtask.entities.Genre;

import java.sql.*;
import java.util.ArrayList;

public class GenreScripts {
    private static Connection con;
    private static PreparedStatement pstmt;
    private static ResultSet rs;

    public GenreScripts() {
        try {
            this.con = ConnectToDB.GetConnection();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void addGenre(ArrayList<String> params, ArrayList<String> args) {

        try {
            String sqlRequest = GenreRequests.getGenreInsert(args);
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


    public ArrayList<Genre> selectFilterGenres(ArrayList<String> params, ArrayList<String> args) {
        ArrayList<Genre> genres = new ArrayList<Genre>();
        try {
            if (!args.isEmpty()) {

                String sqlRequest = GenreRequests.getGenreSelectWhere(args);
                pstmt = con.prepareStatement(sqlRequest);
                Scripts.setParams(params, pstmt);

                System.out.println(pstmt.toString());

            } else {
                String sqlRequest = GenreRequests.genreSelectAll;
                pstmt = con.prepareStatement(sqlRequest);
            }

            rs = pstmt.executeQuery();

            while (rs.next()) {
                long id = rs.getLong(1);
                String name = rs.getString(2);

                Genre genre = new Genre(id, name);
                genres.add(genre);
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
        return genres;
    }

    public void deleteGenre(String genreId) {
        String sqlRequest = GenreRequests.genreDelete;

        try {
            pstmt = con.prepareStatement(sqlRequest);
            pstmt.setString(1, genreId);
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


    public void updateGenre(ArrayList<String> params, ArrayList<String> args) {
        try {
            String sqlRequest = GenreRequests.getGenreUpdate(args);
            pstmt = con.prepareStatement(sqlRequest);
            Scripts.setParams(params, pstmt);

            System.out.println(pstmt.toString());

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

    public ArrayList<CountGenresDTO> showCountGenres() {
        ArrayList<CountGenresDTO> counts = new ArrayList<CountGenresDTO>();
        try {
            String sqlRequest = GenreRequests.getShowCountGenres();
            pstmt = con.prepareStatement(sqlRequest);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                CountGenresDTO dto = new CountGenresDTO(rs.getLong(1),rs.getInt(2));
                counts.add(dto);
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
        return counts;
    }
}
