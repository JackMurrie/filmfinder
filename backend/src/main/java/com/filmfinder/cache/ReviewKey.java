package com.filmfinder.cache;

public class ReviewKey {
    private int movieId;
    private int userId;

    public ReviewKey(int movieId, int userId) {
        this.movieId = movieId;
        this.userId = userId;
    }

    private int getMovieId() {
        return movieId;
    }

    public int getUserId() {
        return userId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        ReviewKey rev = (ReviewKey) obj;

        return (this.movieId==rev.getMovieId() && this.userId==rev.getUserId());
    }

    @Override
    public int hashCode() {
        return movieId*userId;
    }
}
