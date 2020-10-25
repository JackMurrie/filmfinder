package com.filmfinder.movie.movieCombo;

import java.sql.SQLException;

import org.junit.Test;

import javassist.NotFoundException;

public class TestCombo {
    @Test
    public void testCombo() {
        try {
            MovieCombo mc = new MovieCombo(2);
            System.out.println(mc);
        } catch (NotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
