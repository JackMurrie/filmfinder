package com.filmfinder.db;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbcp2.BasicDataSource;

public class MysqlDataSource {
    private static BasicDataSource dataSource;

    private MysqlDataSource() {}

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    private static BasicDataSource getDataSource() {
        if (dataSource == null) {
            BasicDataSource ds = new BasicDataSource();
            ds.setUrl("jdbc:mysql://film-finder.cqhhu7re8ogq.ap-southeast-2.rds.amazonaws.com:3306/?user=filmfinder&password=COMP3900-filmfinder&serverTimezone=UTC");
            ds.setDriverClassName("com.mysql.cj.jdbc.Driver");

            ds.setMinIdle(5);
            ds.setMaxIdle(10);
            ds.setMaxOpenPreparedStatements(100);

            dataSource = ds;
        }
        return dataSource;
    }
    
}
