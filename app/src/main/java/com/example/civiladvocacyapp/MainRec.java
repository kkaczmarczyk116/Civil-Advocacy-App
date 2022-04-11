package com.example.civiladvocacyapp;

import com.google.android.gms.common.util.Strings;

import java.io.Serializable;
import java.util.ArrayList;

public class MainRec implements Serializable {

    private String title;
    private String name;
    private String party;
    private String picurl;
    private String address;
    private String phone;
    private String website;
    private Channels channels;
//    private String ch1Type;
//    private String ch1Id;
//    private String ch2Type;
//    private String ch2Id;
//    private String ch3Type;
//    private String ch3Id;


    public MainRec(String title, String name, String party, String picurl, String address, String phone, String website, Channels channels) {
        this.title = title;
        this.name = name;
        this.party = party;
        this.picurl = picurl;
        this.address = address;
        this.phone = phone;
        this.website = website;
        this.channels = channels;
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

    public Channels getChannels() {
        return channels;
    }

    public void setChannels(Channels channels) {
        this.channels = channels;
    }
    public String getCh1Type(){
        return channels.getCh1Type();
    }
    public String getCh1Id(){
        return channels.getCh1Id();
    }
    public String getCh2Type(){
        return channels.getCh2Type();
    }
    public String getCh2Id(){
        return channels.getCh2Id();
    }
    public String getCh3Type(){
        return channels.getCh3Type();
    }
    public String getCh3Id(){
        return channels.getCh3Id();
    }
}
