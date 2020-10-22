package com.filmfinder.movie.population;

import java.util.ArrayList;

public class PopMovieData {
    private int id;
    private String original_title;
    
    private ArrayList<Genre> genres;
    private Crew credits;

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
