package com.filmfinder.cache;

import java.sql.SQLException;
import java.util.HashMap;

import com.filmfinder.blacklist.Blacklist;
import com.filmfinder.dashboard.Dashboard;
import com.filmfinder.db.AuthDB;
import com.filmfinder.db.ReviewDB;
import com.filmfinder.movie.Movie;
import com.filmfinder.movieLists.Watchlist;
import com.filmfinder.movieLists.Wishlist;
import com.filmfinder.review.Review;
import com.filmfinder.user.User;

import javassist.NotFoundException;

public class Cache {
    private static HashMap<Integer, User> users = new HashMap<Integer, User>();
    private static HashMap<Integer, Movie> movies = new HashMap<Integer, Movie>();
    private static HashMap<ReviewKey, Review> reviews = new HashMap<ReviewKey, Review>();
    private static HashMap<Integer, Dashboard> dashboards = new HashMap<Integer, Dashboard>();
    private static HashMap<Integer, Blacklist> blacklists = new HashMap<Integer, Blacklist>();
    private static HashMap<Integer, Wishlist> wishlists = new HashMap<Integer, Wishlist>();
    private static HashMap<Integer, Watchlist> watchlists = new HashMap<Integer, Watchlist>();

    // private static Cache cache = null;

    // private static Cache() {}

    // public static static Cache getCache() {
    // if (cache == null) {
    // cache = new Cache();
    // }
    // return cache;
    // }

    public static User getUser(int userId) throws NotFoundException, SQLException {
        User user = users.get(userId);
        if (user == null) {
            user = AuthDB.getUser(userId);
        }
        return user;
    }

    public static void putUser() {

    }

    public static void removeUser(int userId) throws SQLException {
        AuthDB.deleteUser(userId);
        users.remove(userId);
    }

    public static Movie getMovie(int movieId) throws NotFoundException, SQLException {
        Movie movie = movies.get(movieId);
        if (movie == null) {
            movie = Movie.getMovieFromDB(movieId);
            movies.put(movieId, movie);
        }
        return movie;
    }

    public static void refreshRating(int movieId) throws NotFoundException, SQLException {
        Movie movie = getMovie(movieId);
        movie.refreshRating();
    }

    public static void deleteMovie(int movieId) {
        movies.remove(movieId);
    }

    public static Review getReview(int userId, int movieId) throws SQLException, NotFoundException {
        ReviewKey rk = new ReviewKey(movieId, userId);
        Review review = reviews.get(rk);
        if (review == null) {
            review = ReviewDB.getReview(movieId, userId);
            reviews.put(rk, review);
        }
        return review;
    }

    public static void removeReview(int userId, int movieId) throws SQLException {
        ReviewDB.removeReview(userId, movieId);
        reviews.remove(new ReviewKey(movieId, userId));
    }

    public static Dashboard getDashboard(int userId) throws NotFoundException, SQLException {
        Dashboard dashboard = dashboards.get(userId);
        if (dashboard == null) {
            dashboard = new Dashboard(userId, 10);
            dashboards.put(userId, dashboard);
        }
        return dashboard;
    }

    public static Blacklist getBlacklist(int userId) throws SQLException {
        Blacklist blacklist = blacklists.get(userId);
        if (blacklist == null) {
            blacklist = new Blacklist(userId);
            blacklists.put(userId, blacklist);
        }
        return blacklist;
    }

    public static Wishlist getWishlist(int userId) throws NotFoundException, SQLException {
        Wishlist wishlist = wishlists.get(userId);
        if (wishlist == null) {
            wishlist = new Wishlist(userId);
            wishlists.put(userId, wishlist);
        }
        return wishlist;
    }

    public static Watchlist getWatchlist(int userId) throws NotFoundException, SQLException {
        Watchlist watchlist = watchlists.get(userId);
        if (watchlist == null) {
            watchlist = new Watchlist(userId);
            watchlists.put(userId, watchlist);
        }
        return watchlist;
    }

}
