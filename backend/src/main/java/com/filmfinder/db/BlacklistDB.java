package com.filmfinder.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.filmfinder.user.User;
import com.filmfinder.user.Users;

import javassist.NotFoundException;

public class BlacklistDB {
    public static Users getBlacklist(int userId) throws SQLException {
        ArrayList<User> blacklist = new ArrayList<User>();
        // return new Users(blacklist);
        Connection c = null;
        PreparedStatement s = null;
        ResultSet rs = null;
        // ArrayList<Integer> returnList = new ArrayList<Integer>();
        try {
            c = DbDataSource.getConnection();
            String q = "SELECT blacklisted_id id FROM blacklist WHERE owner_id=?";
            s = c.prepareStatement(q);

            s.setInt(1, userId);

            rs = s.executeQuery();

            while (rs.next()) {
                try {
                    blacklist.add(User.getUser(rs.getInt("id")));
                } catch (NotFoundException e) {
                    e.printStackTrace();
                }
            };

            return new Users(blacklist);

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

    public static void addToBlacklist(int userId, int blacklistedUserId) throws SQLException {
        Connection c = null;
        PreparedStatement s = null;
        try {
            c = DbDataSource.getConnection();
            String q = "REPLACE INTO blacklist (owner_id, blacklisted_id) values (?, ?);";
            s = c.prepareStatement(q);
            s.setInt(1, userId);
            s.setInt(2, blacklistedUserId);

            s.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw e;
        } finally {
            try {
                if (c != null) c.close();
                if (s != null) s.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return;
    }

    public static int removeFromBlacklist(int userId, int unblacklistId) throws SQLException {
        Connection c = null;
        PreparedStatement s = null;
        try {
            c = DbDataSource.getConnection();
            String q = "DELETE FROM blacklist WHERE owner_id=? AND blacklisted_id=?";
            s = c.prepareStatement(q);
            s.setInt(1, userId);
            s.setInt(2, unblacklistId);

            return s.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw e;
        } finally{
            try {
                if (c != null) c.close();
                if (s != null) s.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

}
