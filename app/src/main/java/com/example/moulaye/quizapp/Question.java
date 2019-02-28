package com.example.moulaye.quizapp;

public class Question {
    private String question;
    private String capitale;
    private String lat;
    private String lon;

    public Question() {
    }
    public Question(String question, String capitale, String lat, String lon) {
        this.question = question;
        this.capitale = capitale;
        this.lat = lat;
        this.lon = lon;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCapitale() {
        return capitale;
    }

    public void setCapitale(String capitale) {
        this.capitale = capitale;
    }


    public String  getLat() { return lat; }

    public void setLat(String lat) { this.lat = lat;}

    public String getLon() {return lon; }

    public void setLon(String lon) { this.lon = lon; }


}
