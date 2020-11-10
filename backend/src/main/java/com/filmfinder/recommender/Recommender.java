package com.filmfinder.recommender;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import com.filmfinder.db.MovieDb;
import com.filmfinder.db.RecommenderDB;
import com.filmfinder.db.ReviewDB;

import com.filmfinder.movie.Movie;
import com.filmfinder.movie.Movies;
import com.filmfinder.recommender.PredictedMoviePair;
import com.filmfinder.recommender.SimilarityPair;
import com.filmfinder.recommender.SortByPredictedRating;
import com.filmfinder.recommender.SortBySimilarity;

import com.filmfinder.db.UtilDB;

import javassist.NotFoundException;

import com.filmfinder.review.Review;
import com.filmfinder.review.ReviewPair;
import com.filmfinder.review.Reviews;
import com.filmfinder.review.SortByRating;

public class Recommender {
        // this is the function that populates the similarity table

    // This and the function to generate predicted movie values should be called
    // periodically to update the user's recommendations of movies based on 
    // other user ratings
    public static void populateSimilarityTable() throws SQLException, NotFoundException {

        ArrayList<Integer> allUsers = UtilDB.getAllUserIds();

        Collections.sort(allUsers);        
        for (int i = 0; i < allUsers.size(); i++) {
            for (int j = i+1; j < allUsers.size(); j++) {

                // this is the code section to check for blacklisting, if the user is blacklisted, simply skip
                // im holding onto a merge with the branch for a bit before i put this in because im currently
                // in the dev branch

                // if other user id in blacklisted and vice versa continue
                
                ArrayList<ReviewPair> pairs = ReviewDB.getReviewPairs(allUsers.get(i), allUsers.get(j));
                
                ArrayList<ReviewPair> differencePairs = new ArrayList<ReviewPair>();
                for(int k = 0; k < pairs.size(); k++) {
                    ReviewPair pair = pairs.get(k);
                    int id = pair.getMovieId();
                    float avg = MovieDb.getRating(id);
                    System.out.println(avg);
                    ReviewPair differencePair = new ReviewPair(avg - pair.getFirstRating(), avg - pair.getSecondRating(), id);
                    differencePairs.add(differencePair);
                }

                float similarity = calculateSimilarity(differencePairs);
                System.out.println("Similarity of user " + i + " to user " + j + " is " + similarity);

                RecommenderDB.putSimilarity(i, j, similarity);

            }
        }
    }

    // use the cosine similarity measure to calculate the similarity of two users

    // CLARIFICATIONS
    // The closer the users are to being the same, the closer the similarity is to 1
    // And they are not similar at all if the similarity is 0

    // If a user has NO ratings, this returns 0, (even if the user is themself)
    private static float calculateSimilarity(ArrayList<ReviewPair> pairs) {
        // scalar product / (magnitude A x magnitude B)

        if (pairs.size() == 0) {
            return 0;
        }

        double scalarProduct = 0;
        double sumPowA = 0, sumPowB = 0;
        for (int i = 0; i < pairs.size(); i++) {
            scalarProduct += pairs.get(i).getFirstRating() * pairs.get(i).getSecondRating();
            sumPowA += Math.pow(pairs.get(i).getFirstRating(), 2);
            sumPowB += Math.pow(pairs.get(i).getSecondRating(), 2);
        }

        double magnitudeA = Math.sqrt(sumPowA);
        double magnitudeB = Math.sqrt(sumPowB);
        double similarity = (scalarProduct / (magnitudeA * magnitudeB));
        return (float)similarity;
    }

    // TODO: Get user ratings on movies, find their most liked genre and suggest movies
    // of that genre and rating
    public static Movies getGenericRecommendations(int limit) throws SQLException, NotFoundException {
        return RecommenderDB.getGenericRecommendations(limit);
    }

    public static Movies getRecommendedMovies(int userId, int count) throws SQLException,
            NotFoundException {


        //ArrayList<SimilarityPair> pairs = RecommenderDB.getUserSimilaritiesTo(userId);

        ArrayList<Integer> movieIds = MovieDb.getMovies();
        ArrayList<PredictedMoviePair> predictedValues = new ArrayList<PredictedMoviePair>();

        for (int i = 0; i < 10; i++) {
            int movieId = movieIds.get(i);
            predictedValues.add(new PredictedMoviePair(movieId, getPredictedValue(movieId, userId)));
        }
        predictedValues.sort(new SortByPredictedRating());
        Movies movies = new Movies();
        for (int i = 0; i < Math.min(predictedValues.size(), count); i++) {
            movies.add(Movie.getMovie(predictedValues.get(i).getMovieId()));
        }
        return movies;
    }

    public static Movies getRecommendedMovies2(int userId, int limit) throws SQLException, NotFoundException {
        return RecommenderDB.getRecommendedMovies2(userId, limit);
    }

    private static float getPredictedValue(int movieId, int userId) throws SQLException,
            NotFoundException {
        
                // having to query twice is absolutely detrimental to the performance of this
                // need to figure out solutions !

                // something like this CAN be precomputed, but its even more expensive to compute than
                // the similarities and it shouldn't be 
        float avg = MovieDb.getRating(movieId);

        float sum = 0;
        float cumulativePredicted = 0;
        ArrayList<RatingSimilarityPair> ratings = ReviewDB.getReviewedWithSimilarities(movieId, userId);
        for (int i = 0; i < ratings.size(); i++) {
                
            float difference = avg - ratings.get(i).getRating();
            cumulativePredicted += (ratings.get(i).getSimilarity() * difference);
            sum += Math.abs(ratings.get(i).getSimilarity());
        }

        if (sum == 0) {
            return 0;
        }
        return cumulativePredicted / sum;
    }
}
