package com.filmfinder.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

import javassist.bytecode.stackmap.BasicBlock.Catch;




public class MysqlConnector {
    private Connection connection = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;


    public void readDb(String query) throws Exception{
        try {
            // Class.forName("com.mysql.cj.jdbc.Driver");
            // connection = DriverManager.getConnection("jdbc:mysql://film-finder.cqhhu7re8ogq.ap-southeast-2.rds.amazonaws.com:3306/?user=filmfinder&password=COMP3900-filmfinder&serverTimezone=UTC");
            connection = DbDataSource.getConnection();

            statement = connection.createStatement();

            // TEST QUERY: select * from film_finder.genre
            resultSet = statement.executeQuery(query);

            printResult();

        } catch (Exception e) {
            throw e;
        } finally {
            close();
        }
    }
    public void readDb() throws Exception {
        readDb("select * from film_finder.genre");
    }

    private void printResult() throws Exception {
        try {
            ResultSetMetaData rsmd = resultSet.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
                
            System.out.println("----");
            System.out.println(rsmd.getColumnName(1));
            System.out.println("----");
            while (resultSet.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1) System.out.print(",  ");
                    String columnValue = resultSet.getString(i);
                    System.out.print(columnValue);
                }
                System.out.println("");
            }
        } catch (Exception e) {
            throw e;
        }

    }

    // You need to close the resultSet
    private void close() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }

            if (connection != null) {
                connection.close();
            }
        } catch (Exception e) {

        }
    }
    
}
