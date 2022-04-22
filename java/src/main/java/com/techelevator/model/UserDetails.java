package com.techelevator.model;

import javax.validation.constraints.Email;

public class UserDetails {
    //@Email(message = "Please enter a valid email address.") add back in when front end validation is better
    private String email;
    Long userId;


    public UserDetails(String email, Long userId) {
        this.email = email;
        this.userId = userId;
    }

    public UserDetails() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "UserDetails{" +
                "email='" + email + '\'' +
                ", userId=" + userId +
                '}';
    }
}
