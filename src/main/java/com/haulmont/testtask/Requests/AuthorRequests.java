package com.haulmont.testtask.Requests;

import java.util.ArrayList;

public class AuthorRequests {
    public static final String authorSelectAll = "SELECT * FROM author ";

    public static final String authorDelete = "DELETE FROM author " +
            "WHERE id = ? ";

    public static String getAuthorInsert(ArrayList<String> args) {
        String s = "INSERT INTO author ";
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

    public static String getAuthorUpdate(ArrayList<String> args) {
        String s = "UPDATE author ";
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

    public static String getAuthorSelectWhere(ArrayList<String> args) {
        String s = "SELECT * from author ";
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
}
