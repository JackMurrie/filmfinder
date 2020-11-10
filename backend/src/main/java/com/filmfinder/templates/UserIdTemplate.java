package com.filmfinder.templates;

import javax.xml.bind.annotation.XmlRootElement; 

@XmlRootElement
public class UserIdTemplate {
    private int userId;

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
