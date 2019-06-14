package com.haulmont.testtask.Requests;

import java.util.ArrayList;

public class GenreRequests {
    public static final String genreSelectAll = "SELECT * FROM genre ";

    public static final String genreDelete = "DELETE FROM genre " +
            "WHERE id = ? ";

    public static String getGenreInsert(ArrayList<String> args) {
        String s = "INSERT INTO genre ";
        int size = args.size();
        if (!args.isEmpty()) {
            s = s + "(";
            int num = 0;
            for (String arg : args
            ) {

                s = s + arg;
                num++;

                if (num != size) {
                    s = s + ", ";
                }
            }
            s = s + ") VALUES(";
            num = 0;
            for (String arg : args
            ) {

                s = s + "?";
                num++;

                if (num != size) {
                    s = s + ", ";
                }
            }
            s = s + ");";
        }

        return s;
    }

    public static String getGenreUpdate(ArrayList<String> args) {
        String s = "UPDATE genre ";
        int size = args.size();
        if (!args.isEmpty()) {
            s = s + "SET ";
            int num = 0;
            for (String arg : args
            ) {

                s = s + arg + " = ? ";
                num++;

                if (num != size) {
                    s = s + ", ";
                }
            }
        }

        s = s + "WHERE id = ?;";

        return s;
    }

    public static String getGenreSelectWhere(ArrayList<String> args) {
        String s = "SELECT * from genre ";
        int size = args.size();
        if (!args.isEmpty()) {
            s = s + "WHERE ";
            int num = 0;
            for (String arg : args
            ) {

                s = s + arg + "= ? ";
                num++;

                if (num != size) {
                    s = s + "AND ";
                }
            }
        }
        return s;
    }

    public static String getShowCountGenres(){
        return "SELECT genre, COUNT(*) FROM book GROUP BY genre";
    }
}
