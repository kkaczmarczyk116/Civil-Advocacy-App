package com.example.civiladvocacyapp;

public class Channels {

    private String ch1Type;
    private String ch1Id;
    private String ch2Type;
    private String ch2Id;
    private String ch3Type;
    private String ch3Id;

    public Channels(String ch1Type, String ch1Id, String ch2Type, String ch2Id, String ch3Type, String ch3Id) {
        this.ch1Type = ch1Type;
        this.ch1Id = ch1Id;
        this.ch2Type = ch2Type;
        this.ch2Id = ch2Id;
        this.ch3Type = ch3Type;
        this.ch3Id = ch3Id;
    }

    public String getCh1Type() {
        return ch1Type;
    }

    public void setCh1Type(String ch1Type) {
        this.ch1Type = ch1Type;
    }

    public String getCh1Id() {
        return ch1Id;
    }

    public void setCh1Id(String ch1Id) {
        this.ch1Id = ch1Id;
    }

    public String getCh2Type() {
        return ch2Type;
    }

    public void setCh2Type(String ch2Type) {
        this.ch2Type = ch2Type;
    }

    public String getCh2Id() {
        return ch2Id;
    }

    public void setCh2Id(String ch2Id) {
        this.ch2Id = ch2Id;
    }

    public String getCh3Type() {
        return ch3Type;
    }

    public void setCh3Type(String ch3Type) {
        this.ch3Type = ch3Type;
    }

    public String getCh3Id() {
        return ch3Id;
    }

    public void setCh3Id(String ch3Id) {
        this.ch3Id = ch3Id;
    }
}
