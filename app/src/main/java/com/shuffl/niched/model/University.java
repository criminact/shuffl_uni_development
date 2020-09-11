package com.shuffl.niched.model;

import java.io.Serializable;

public class University implements Serializable {
    public String name;
    public String code;

    public University(String name) {
        this.name = name;
    }

    public University() {
    }
}
