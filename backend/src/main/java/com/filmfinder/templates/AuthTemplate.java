package com.filmfinder.templates;

import javax.xml.bind.annotation.XmlRootElement; 

@XmlRootElement
public class AuthTemplate {

    private String email;
    private String password;
    private String firstName;
    private String lastName;

    public AuthTemplate() {}

    public AuthTemplate(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public AuthTemplate(String email, String password, String firstName, String lastName) {
        this(email, password);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getPassword() {
        return password;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setPassword(String password) {
        this.password = password;
    }

}