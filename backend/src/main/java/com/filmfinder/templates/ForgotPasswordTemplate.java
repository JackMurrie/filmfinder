package com.filmfinder.templates;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ForgotPasswordTemplate {
    private String email;

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
