package com.shuffl.niched.model;

import java.io.Serializable;

public class User implements Serializable {

    public User() {
    }

    public String name;
    public String profile;
    public String id;
    public double ratings;
    public int raters;
    public String mobileNumber;
    public String university;
    public boolean isAuthenticated;

}
