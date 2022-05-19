package com.batost.musicPlayer;

import java.io.Serializable;

public class User implements Serializable {
    private String fullName;
    private String email;
    private String telNumber;
    private String password;
    private String confirmPassword;

    public User() {

    }

    public User(String fullName, String email, String telNumber, String password, String confirmPassword) {
        this.fullName = fullName;
        this.email = email;
        this.telNumber = telNumber;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelNumber() {
        return telNumber;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }
}
