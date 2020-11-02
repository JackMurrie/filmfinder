package com.filmfinder.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.filmfinder.recommender.SimilarityPair;

import javassist.NotFoundException;

public class RecommenderDB {
    private String list = "similarityList";

    public static void putSimilarity(int userId_1, int userId_2, float similarity) throws SQLException {
        Connection c = null;
        PreparedStatement s = null;
        try {
            c = DbDataSource.getConnection();
            String q = "REPLACE INTO similarity(user_1, user_2, similarity) values (?, ?, ?);";
            s = c.prepareStatement(q);
            s.setInt(1, userId_1);
            s.setInt(2, userId_2);
            s.setFloat(3, similarity);

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

    public static ArrayList<SimilarityPair> getUserSimilaritiesTo(int userId) throws SQLException {
        Connection c = null;
        PreparedStatement s = null;
        ResultSet rs = null;
        ArrayList<SimilarityPair> list = new ArrayList<SimilarityPair>();
        try {
            c = DbDataSource.getConnection();
            String q = "SELECT user_2 uId, similarity sim FROM similarity WHERE user_1 =?";
            s = c.prepareStatement(q);

            s.setInt(1, userId);

            rs = s.executeQuery();
            
            while (rs.next()) {
                list.add(new SimilarityPair(rs.getInt("uId"), rs.getFloat("sim")));
            };

            return list;

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
