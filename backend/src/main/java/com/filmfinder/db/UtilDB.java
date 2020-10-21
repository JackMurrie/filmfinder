package com.filmfinder.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javassist.NotFoundException;

public class UtilDB {
    public static int getUserId(String email) throws SQLException, NotFoundException {
        Connection c = null;
        PreparedStatement s = null;
        ResultSet rs = null;
        try {
            c = DbDataSource.getConnection();
            String q = "SELECT id FROM user WHERE email=?";
            s = c.prepareStatement(q);
            s.setString(1, email);

            rs = s.executeQuery();
            
            if (!rs.next()) {
                throw new NotFoundException("Email doesn't exist in database");
            };
            return rs.getInt("id");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw e;
        } finally {
            try {
                if (c != null) c.close();
                if (s != null) s.close();
                if (rs != null) rs.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                throw e;
            }
        }
    }
}
