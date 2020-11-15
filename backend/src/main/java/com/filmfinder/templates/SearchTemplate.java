package com.filmfinder.templates;



import javax.xml.bind.annotation.XmlRootElement; 

@XmlRootElement
public class SearchTemplate {

    private String title;
    private String director;
    private String genre;
    private int limit = 10;

    public int getLimit() {
        return this.limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
    
    public String getGenre() {
        return this.genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirector() {
        return this.director;
    }

    public void setDirector(String director) {
        this.director = director;
    }
}