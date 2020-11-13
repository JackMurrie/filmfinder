package com.filmfinder.templates;

import javax.xml.bind.annotation.XmlRootElement; 

@XmlRootElement
public class ResetPasswordTemplate {
    
    private String code;
    private String password;

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
