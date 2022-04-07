package com.example.civiladvocacyapp;

import java.io.Serializable;

public class MainRec implements Serializable {

    private String title;
    private String name;
    private String party;
    private String picurl;
    private String address;
    private String phone;
    private String website;

    public MainRec(String title, String name, String party, String picurl, String address, String phone, String website) {
        this.title = title;
        this.name = name;
        this.party = party;
        this.picurl = picurl;
        this.address = address;
        this.phone = phone;
        this.website = website;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public String getPicurl() {
        return picurl;
    }

    public void setPicurl(String picurl) {
        this.picurl = picurl;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
}
