package com.filmfinder.movieLists;

import java.sql.SQLException;

import com.filmfinder.db.WishlistDB;
import com.google.gson.annotations.JsonAdapter;

import javassist.NotFoundException;

@JsonAdapter(MovieListSerializer.class)
public class Wishlist extends MovieList {

    public Wishlist(int userId) throws NotFoundException, SQLException {
        dbUtil = new WishlistDB(userId);
        movies = dbUtil.getMovies();
    }

}
