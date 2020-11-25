package com.filmfinder.blacklist;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;

import org.junit.Test;

public class TestBlacklist {
    @Test
    public void testBlacklist() {
        try {
            Blacklist b = new Blacklist(1);
            System.out.println(b);
        } catch (SQLException e) {
            assertTrue(false);
        }
    }
    
    @Test
    public void testInsertBlacklist() {
        Blacklist b;
        try {
            b = new Blacklist(1);
            b.add(2);
            b.refresh();
            System.out.println(b);
        } catch (SQLException e) {
            assertTrue(false);
            e.printStackTrace();
        }
    }

    // @Test
    public void testRemove() {
        Blacklist b;
        try {
            b = new Blacklist(1);
            b.add(2);
            assertEquals(b.remove(2), 1);
            b.refresh();
            System.out.println(b);
        } catch (SQLException e) {
            assertTrue(false);
            e.printStackTrace();
        }
    }
}
