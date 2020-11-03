package com.filmfinder.templates;



import javax.xml.bind.annotation.XmlRootElement; 

@XmlRootElement
public class SearchTemplate {

    private String searchString;
    

    public String getSearchString() {
        return searchString;
    }
    // flags and such

}