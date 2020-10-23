package com.filmfinder.movie.population;

import java.util.ArrayList;

import com.filmfinder.db.MovieDb;
import com.filmfinder.util.UrlConnector;
import com.google.gson.Gson;

public class PopMovieData {
    private int id;
    private String original_title;
    
    private ArrayList<Genre> genres;
    private Crew credits;

    public static PopMovieData getPopMovieData(int id) throws Exception {
        String json = UrlConnector.readUrl("https://api.themoviedb.org/3/movie/" + id + "?api_key=70ae629f88a806e8758ac3900483833e&append_to_response=credits");
        PopMovieData data = new Gson().fromJson(json, PopMovieData.class);
        return data;
    }

    public String getTitle() {
        return this.original_title;
    }

    public void updateDBDirector() {
        for (Person p: credits.getDirectors()) {
            try {
                MovieDb.putDirector(p.getName(), this.id);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void updateDBGenres() {
        for (Genre g: genres) {
            try {
                MovieDb.putGenre(g.name, this.id);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public class Genre {
        private String name;

        @Override
        public String toString() {
            return "  Genre: " + name + "\n";
        }
    }
    public class Crew {
        private ArrayList<Person> crew;

        public ArrayList<Person> getDirectors() {
            ArrayList<Person> directors = new ArrayList<Person>();
            for (Person p: crew) {
                if (p.getJob().equals("Director")) {
                    directors.add(p);
                }
            }
            return directors;
        }
    }
    public class Person {
        private String job;
        private String name;

        public String getJob() {
            return this.job;
        }
        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return "  Name: " + name + "\n";
        }
        
    }

    @Override
    public String toString() {
        return "id: " + id +
                "\nTitle: " + original_title +
                "\nGenres:\n" + genres.toString() +
                "\nDirectors:\n" + credits.getDirectors().toString();
    }
}
