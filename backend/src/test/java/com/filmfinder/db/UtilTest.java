package com.filmfinder.db;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class UtilTest {
    @Test
    public void testGetUid() {
        System.out.println("Running get user test: ");
        assertTrue(true);
        try  {
            System.out.println(UtilDB.getUserId("testing@somthingrandom.test"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            assertTrue(false);
        } 
    }
}
