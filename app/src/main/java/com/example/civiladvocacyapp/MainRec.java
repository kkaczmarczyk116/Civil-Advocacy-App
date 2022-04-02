package com.example.civiladvocacyapp;

import java.io.Serializable;

public class MainRec implements Serializable {

    private  String title;
    private  String name;

    public MainRec(String title, String name) {
        this.title = title;
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public String getName() {
        return name;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public void setName(String name) {
        this.name = name;
    }
}
