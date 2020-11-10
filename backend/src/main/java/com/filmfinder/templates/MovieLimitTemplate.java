package com.filmfinder.templates;

import javax.xml.bind.annotation.XmlRootElement; 

@XmlRootElement
public class MovieLimitTemplate {
   
    private int limit = 10;

    public int getLimit() {
        return this.limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
