package com.filmfinder.db;

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DbDataSource {
    private static HikariDataSource dataSource;

    static {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://film-finder.cqhhu7re8ogq.ap-southeast-2.rds.amazonaws.com:3306/film_finder?serverTimezone=UTC");
        config.setUsername("filmfinder");
        config.setPassword("COMP3900-filmfinder");
        config.addDataSourceProperty("minimumIdle", "5");
        config.addDataSourceProperty("maximumPoolSize", "25");
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");

        dataSource = new HikariDataSource(config);
    }

    private DbDataSource() {}

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
