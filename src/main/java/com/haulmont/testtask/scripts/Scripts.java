package com.haulmont.testtask.scripts;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class Scripts {
    public static void setParams(ArrayList<String> params, PreparedStatement pstmt) {
        try {
            int i = 1;

            for (String param : params
            ) {
                pstmt.setString(i, param);
                i++;
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
    }
}
