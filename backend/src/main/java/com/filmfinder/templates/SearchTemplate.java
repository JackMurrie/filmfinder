package com.filmfinder.templates;



import javax.xml.bind.annotation.XmlRootElement; 

@XmlRootElement
public class SearchTemplate {

    private String searchString;
    
    public String getSearchString() {
        return searchString;
    }

    public SearchTemplate() {
    }

    public SearchTemplate(String searchString) {
        this.searchString = searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    // flags and such

}