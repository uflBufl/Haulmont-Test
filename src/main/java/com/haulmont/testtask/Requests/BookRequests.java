package com.haulmont.testtask.Requests;

import java.util.ArrayList;

public class BookRequests {
    public static final String bookSelectAll = "SELECT * FROM book ";

    public static final String bookDelete = "DELETE FROM book " +
            "WHERE id = ? ";

    public static String getBookInsert(ArrayList<String> args) {
        String s = "INSERT INTO book ";
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

    public static String getBookUpdate(ArrayList<String> args) {
        String s = "UPDATE book ";
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

    public static String getBookSelectWhere(ArrayList<String> args) {
        String s = "SELECT * from book ";
        int size = args.size();
        if (!args.isEmpty()) {
            s = s + "WHERE ";
            int num = 0;
            for (String arg : args
            ) {

                if(arg.equals("name")){
                    s = s + arg + " LIKE ? ";
                }
                else{
                    s = s + arg + "= ? ";
                }

                num++;

                if (num != size) {
                    s = s + "AND ";
                }
            }
        }
        return s;
    }
}
